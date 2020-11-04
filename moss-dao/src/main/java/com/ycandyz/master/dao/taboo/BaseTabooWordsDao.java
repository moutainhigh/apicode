package com.ycandyz.master.dao.taboo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.risk.TabooWordsForReview;
import com.ycandyz.master.domain.query.taboo.BaseTabooWordsQuery;
import com.ycandyz.master.entities.taboo.BaseTabooWords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基础商品表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 */
@Mapper
public interface BaseTabooWordsDao extends BaseMapper<BaseTabooWords> {
    void addBaseTabooWords(BaseTabooWords baseTabooWords);

    BaseTabooWords selById(Long id);

    int delById(Long id);

    Page<BaseTabooWords>  selectList(Page pageQuery, @Param("bt") BaseTabooWordsQuery baseTabooWordsQuery);

    List<BaseTabooWords> select();

    List<TabooWordsForReview> selectWords();

    int updateBaseTabooWords(BaseTabooWords baseTabooWords);
}
