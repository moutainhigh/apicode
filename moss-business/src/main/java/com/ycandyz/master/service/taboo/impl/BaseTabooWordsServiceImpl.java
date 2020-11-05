package com.ycandyz.master.service.taboo.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.taboo.BaseTabooWordsQuery;
import com.ycandyz.master.entities.taboo.BaseTabooWords;
import com.ycandyz.master.model.taboo.BaseTabooWordsVO;
import com.ycandyz.master.request.UserRequest;
import com.ycandyz.master.service.taboo.IBaseTabooWordsService;
import com.ycandyz.master.dao.taboo.BaseTabooWordsDao;
import com.ycandyz.master.utils.MyCollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * @Description 基础商品表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Slf4j
@Service
public class BaseTabooWordsServiceImpl extends BaseService<BaseTabooWordsDao, BaseTabooWords, BaseTabooWordsQuery> implements IBaseTabooWordsService {

    @Resource
    private BaseTabooWordsDao baseTabooWordsDao;

    /**
     * @Description: 新增敏感字
     * @Author li Zhuo
     * @Date 2020/10/26
     * @Version: V1.0
    */
    @Override
    public void addBaseTabooWords(BaseTabooWordsVO baseTabooWordsVO) {
        UserVO currentUser = UserRequest.getCurrentUser();
        BaseTabooWords baseTabooWords = new BaseTabooWords();
        String tabooWords = MyCollectionUtils.PraseArraytoString(baseTabooWordsVO.getTabooWords());
        BeanUtils.copyProperties(baseTabooWordsVO,baseTabooWords);
        baseTabooWords.setTabooWords(tabooWords);
        baseTabooWords.setOperator(currentUser.getPhone());
        log.info("添加敏感字baseTabooWords:{}到数据库",baseTabooWords);
        baseTabooWordsDao.addBaseTabooWords(baseTabooWords);
    }

    /**
     * @Description: 根据id查询敏感字
     * @Author li Zhuo
     * @Date 2020/10/26
     * @Version: V1.0
    */
    @Override
    public BaseTabooWords selById(Long id) {
        BaseTabooWords baseTabooWords = baseTabooWordsDao.selById(id);
        return baseTabooWords;
    }

    @Override
    public int delById(Long id) {
        int i =baseTabooWordsDao.delById(id);
        return i;
    }

    @Override
    public Page<BaseTabooWords> selectList(RequestParams<BaseTabooWordsQuery> requestParams) {
        BaseTabooWordsQuery baseTabooWordsQuery = requestParams.getT();
        Page pageQuery = new Page(requestParams.getPage(),requestParams.getPage_size());
        Page<BaseTabooWords> page = null;
        try {
            page = baseTabooWordsDao.selectList(pageQuery, baseTabooWordsQuery);
        }catch (Exception e){
            log.error("error:{}",e.getMessage());
            page = new Page<>(0,10,0);
        }
        return page;
    }

    @Override
    public int updateBaseTabooWords(BaseTabooWordsVO baseTabooWordsVO) {
        UserVO currentUser = UserRequest.getCurrentUser();
        BaseTabooWords baseTabooWords = new BaseTabooWords();
        String tabooWords = MyCollectionUtils.PraseArraytoString(baseTabooWordsVO.getTabooWords());
        BeanUtils.copyProperties(baseTabooWordsVO,baseTabooWords);
        baseTabooWords.setTabooWords(tabooWords);
        baseTabooWords.setOperator(currentUser.getPhone());
        int i = baseTabooWordsDao.updateBaseTabooWords(baseTabooWords);
        return i;
    }

}
