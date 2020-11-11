package com.ycandyz.master.service.taboo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.domain.query.taboo.BaseTabooWordsQuery;
import com.ycandyz.master.domain.response.risk.BaseTabooWordsRep;
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

    BaseTabooWordsRep selById(Long id);

    void delById(Long id);

    Page<BaseTabooWordsRep> selectList(RequestParams<BaseTabooWordsQuery> query);

    void updateBaseTabooWords(BaseTabooWordsVO baseTabooWordsVO);
}
