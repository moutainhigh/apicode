package com.ycandyz.master.service.risk.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.api.TabooReturnResponse;
import com.ycandyz.master.dao.risk.ContentreviewDao;
import com.ycandyz.master.dao.risk.ContentreviewLogDao;
import com.ycandyz.master.dao.user.UserDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.enums.ad.AdvertisingEnum;
import com.ycandyz.master.domain.query.risk.*;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.entities.ad.Advertising;
import com.ycandyz.master.entities.mall.MallItemVideo;
import com.ycandyz.master.entities.user.User;
import com.ycandyz.master.handler.HandlerContext;
import com.ycandyz.master.request.UserRequest;
import com.ycandyz.master.service.mall.impl.MallItemVideoServiceImpl;
import com.ycandyz.master.service.risk.ContentReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ContentReviewServiceImpl implements ContentReviewService {

    @Autowired
    private ContentreviewDao contentreviewDao;

    @Autowired
    private MallItemVideoServiceImpl mallItemVideoService;

    @Autowired
    private UserDao userDao;

    @Override
    public ReturnResponse<Page<ContentReviewRep>> list(RequestParams<ContentReviewQuery> requestParams) {
        log.info("内容审核查询入参:{}", JSON.toJSONString(requestParams));
        ContentReviewQuery contentReviewQuery = requestParams.getT();
        Page<ContentReviewRep> page1 = new Page<>();
        List<ContentReviewRep> newlist = new ArrayList<>();
        try{
            //获取总条数
            Integer count = contentreviewDao.getReviewCount(contentReviewQuery);
            page1.setTotal(count);
            if (count!=null && count>0) {
                //分页
                List<ContentReviewDTO> list = contentreviewDao.list((requestParams.getPage() - 1) * requestParams.getPage_size(),
                        requestParams.getPage_size(), contentReviewQuery);
                List<Long> auditorList = list.stream().map(ContentReviewDTO::getAuditor).collect(Collectors.toList());
                List<User> userList = userDao.selectBatchIds(auditorList);
                Map<Long, String> map = userList.stream().collect(Collectors.toMap(User::getId,User::getName));
                list.stream().forEach(s -> {
                    ContentReviewRep contentReviewRep = new ContentReviewRep();
                    AbstractHandler handler = HandlerContext.getHandler(s.getType());
                    handler.handleContentreview(s,contentReviewRep);
                    //add video
                    if(s.getType() == 0){
                        String conId = contentreviewDao.selectById(s.getId());
                        LambdaQueryWrapper<MallItemVideo> videoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                                .select(MallItemVideo::getId,MallItemVideo::getUrl,MallItemVideo::getImg)
                                .eq(MallItemVideo::getItemNo,conId);
                        List<MallItemVideo> videoList = mallItemVideoService.list(videoWrapper);
                        contentReviewRep.setVideo(videoList);
                    }
                    //拼接审核人姓名
                    contentReviewRep.setAuditorName(map.get(s.getAuditor()));
                    newlist.add(contentReviewRep);
                });
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        newlist.stream().forEach(i -> {
            LambdaQueryWrapper<MallItemVideo> videoWrapper = new LambdaQueryWrapper<MallItemVideo>()
                    .eq(MallItemVideo::getItemNo, i.getOndContent());
            mallItemVideoService.list();
        });
        page1.setPages(requestParams.getPage());
        page1.setCurrent(requestParams.getPage());
        page1.setRecords(newlist);
        page1.setSize(requestParams.getPage_size());
        return ReturnResponse.success(page1);
    }

    //批量通过/屏蔽
    @Override
    public ReturnResponse batchExamine(ReviewBatchExamineParam reviewParams) {
        if (reviewParams == null){
            return ReturnResponse.success(null,"无通过或屏蔽的数据");
        }
        Integer oper = reviewParams.getOper();
        Multimap<Integer,Long> myMultimap = ArrayListMultimap.create();
        List<ExamineParam> examineParams = reviewParams.getExamineParams();
        if (examineParams != null){
            examineParams.stream().forEach(s-> {
                myMultimap.put(s.getType(),s.getId());
            });
        }
        Map<Integer,Map<Integer,List<Long>>> allMaps = new HashMap<>();
        myMultimap.forEach((mk,mv)->{
            List<Long> ids = (List<Long>) myMultimap.get(mk);
            Map<Integer,List<Long>> maps = new HashMap<>();
            maps.put(oper,ids);
            allMaps.put(mk,maps);
        });
        TabooReturnResponse tabooReturnResponse = new TabooReturnResponse();
        List<ReturnResponse> list = new ArrayList<>();
        allMaps.forEach((k,v) -> {
            AbstractHandler handler = HandlerContext.getHandler(k);
            ReturnResponse returnResponse = handler.handleExamine(v);
            list.add(returnResponse);
        });
        tabooReturnResponse.setData(list);
        return ReturnResponse.success(tabooReturnResponse);
    }


    //通过/屏蔽
    @Override
    public ReturnResponse examine(ReviewParam reviewParam) {
        if (reviewParam == null){
            return null;
        }
        AbstractHandler handler = HandlerContext.getHandler(reviewParam.getType());
        ReturnResponse returnResponse = handler.examine(reviewParam);
        return returnResponse;
    }

}
