package com.ycandyz.master.service.taboo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.domain.query.taboo.BaseTabooWordsQuery;
import com.ycandyz.master.entities.taboo.BaseTabooWords;
import com.ycandyz.master.model.taboo.BaseTabooWordsVO;
/**
 * <p>
 * @Description 基础商品表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
public interface IBaseTabooWordsService extends IService<BaseTabooWords>{


    void addBaseTabooWords(BaseTabooWordsVO baseTabooWordsVO);

    BaseTabooWords selById(Long id);

    int delById(Long id);

    Page<BaseTabooWords> selectList(RequestParams<BaseTabooWordsQuery> query);

    int updateBaseTabooWords(BaseTabooWordsVO baseTabooWordsVO);
}
