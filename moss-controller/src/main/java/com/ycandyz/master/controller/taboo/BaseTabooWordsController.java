package com.ycandyz.master.controller.taboo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.taboo.BaseTabooWordsQuery;
import com.ycandyz.master.domain.response.risk.BaseTabooWordsRep;
import com.ycandyz.master.entities.taboo.BaseTabooWords;
import com.ycandyz.master.model.taboo.BaseTabooWordsVO;
import com.ycandyz.master.service.risk.impl.TabooCheckServiceImpl;
import com.ycandyz.master.service.taboo.BaseTabooWordsService;
import com.ycandyz.master.service.taboo.impl.BaseTabooWordsServiceImpl;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * @Description 敏感词管理 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("/sys/audit/content/sensitive-word-group")
@Api(tags="敏感词管理")
public class BaseTabooWordsController extends BaseController<BaseTabooWordsServiceImpl, BaseTabooWords, BaseTabooWordsQuery> {

    @Autowired
    private BaseTabooWordsService baseTabooWordsService;

    @Autowired
    private TabooCheckServiceImpl tabooCheckService;

    @ApiOperation(value="新增敏感字")
    @PostMapping
    public ReturnResponse<Object> addBaseTabooWords(@Validated(ValidatorContract.OnCreate.class) @RequestBody BaseTabooWordsVO baseTabooWordsVO) {
        if (baseTabooWordsVO == null){
            log.error("当前更新的入参数据为空");
            return ReturnResponse.failed("当前更新的入参数据为空");
        }
        log.info("新增敏感字请求入参:{}", JSON.toJSONString(baseTabooWordsVO));
        String phraseName = baseTabooWordsVO.getPhraseName();
        String[] tabooWords = baseTabooWordsVO.getTabooWords();
        ReturnResponse returnResponse = baseTabooWordsService.selTabooWords(phraseName,tabooWords);
        if (returnResponse != null && returnResponse.getCode() == 500){
            return returnResponse;
        }
        baseTabooWordsService.addBaseTabooWords(baseTabooWordsVO);
        log.info("新增敏感字请求响应:{}", ReturnResponse.success("保存成功!"));
        return ReturnResponse.success("保存成功!");
    }

    @ApiOperation(value = "查询根据ID")
    @GetMapping(value = "/{id}")
    public ReturnResponse<BaseTabooWordsRep> getById(@PathVariable Long id) {
        log.info("敏感词管理查询根据ID请求入参:{}", id);
        BaseTabooWordsRep baseTabooWords = baseTabooWordsService.selById(id);
        log.info("敏感词管理查询根据ID请求响应:{}", ReturnResponse.success(baseTabooWords));
        return ReturnResponse.success(baseTabooWords);
    }

    @ApiOperation(value="编辑敏感字")
    @PutMapping("/{id}")
    public ReturnResponse<Object> updateBaseTabooWords(@PathVariable Long id,@Validated(ValidatorContract.OnCreate.class) @RequestBody BaseTabooWordsVO baseTabooWordsVO) {
        log.info("编辑敏感字请求入参:id:{};baseTabooWordsVO:{}", id,JSON.toJSONString(baseTabooWordsVO));
        ReturnResponse returnResponse = baseTabooWordsService.selTabooWord(id,baseTabooWordsVO);
        if (returnResponse != null && returnResponse.getCode() == 500){
            return returnResponse;
        }
        ReturnResponse returnResponse2 = baseTabooWordsService.updateBaseTabooWords(id,baseTabooWordsVO);
        log.info("编辑敏感字请求响应:{}", JSON.toJSONString(returnResponse2));
        return returnResponse2;
    }

    @ApiOperation(value = "查询全部敏感词")
    @ApiImplicitParams({
            @ApiImplicitParam(name="phraseName",value="敏感词词组名称",dataType="String"),
            @ApiImplicitParam(name="tabooWord",value="敏感词",dataType="String"),
            @ApiImplicitParam(name="createdTimeStart",value="敏感词词组开始",dataType="String"),
            @ApiImplicitParam(name="createdTimeEnd",value="创建时间截止",dataType="Long"),
            @ApiImplicitParam(name="page_size",value="每页显示条数",dataType="Long"),
            @ApiImplicitParam(name="page",value="当前页",dataType="Long")
    })
    @GetMapping
    public ReturnResponse<Page<BaseTabooWordsRep>> selectList(@RequestParam(value = "phraseName",required = false) String phraseName,
                                                              @RequestParam(value = "tabooWord",required = false) String tabooWord,
                                                              @RequestParam(value = "createdTimeStart",required = false) Long createdTimeStart,
                                                              @RequestParam(value = "createdTimeEnd",required = false) Long createdTimeEnd,
                                                              @RequestParam(value = "page_size",defaultValue = "10",required = false) Long page_size,
                                                              @RequestParam(value = "page",defaultValue = "1",required = false) Long page) {
        BaseTabooWordsQuery baseTabooWordsQuery = new BaseTabooWordsQuery();
        baseTabooWordsQuery.setPhraseName(phraseName);
        baseTabooWordsQuery.setTabooWord(tabooWord);
        baseTabooWordsQuery.setCreatedTimeStart(createdTimeStart);
        baseTabooWordsQuery.setCreatedTimeEnd(createdTimeEnd);
        RequestParams<BaseTabooWordsQuery> requestParams = new RequestParams<>();
        requestParams.setPage(page);
        requestParams.setPage_size(page_size);
        requestParams.setT(baseTabooWordsQuery);
        log.info("敏感词管理查询全部请求入参:{}", JSON.toJSONString(requestParams));
        Page<BaseTabooWordsRep> pages = baseTabooWordsService.selectList(requestParams);
        log.info("敏感词管理查询全部请求响应:{}", JSON.toJSONString(ReturnResponse.success(pages)));
        return ReturnResponse.success(pages);
    }

    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "/{id}")
    public ReturnResponse<Object> delById(@PathVariable Long id) {
        log.info("敏感词管理通过ID删除请求入参:{}", id);
        ReturnResponse returnResponse = baseTabooWordsService.delById(id);
        log.info("敏感词管理查询全部请求响应:{}", JSON.toJSONString(ReturnResponse.success(returnResponse)));
        return ReturnResponse.success(returnResponse);
    }

//    public ReturnResponse<String> getAllToRedis(){
//        tabooCheckService.
//    }
}
