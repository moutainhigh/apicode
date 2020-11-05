package com.ycandyz.master.service.base.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.entities.base.BaseBank;
import com.ycandyz.master.domain.query.base.BaseBankQuery;
import com.ycandyz.master.dao.base.BaseBankDao;
import com.ycandyz.master.service.base.IBaseBankService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * @Description base-银行 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 * @version 2.0
 */
@Slf4j
@Service
public class BaseBankServiceImpl extends BaseService<BaseBankDao,BaseBank,BaseBankQuery> implements IBaseBankService {

    @Override
    public Page<BaseBank> page(Page page, BaseBankQuery query) {
        LambdaQueryWrapper<BaseBank> queryWrapper = new LambdaQueryWrapper<BaseBank>()
                .eq(StrUtil.isNotEmpty(query.getCode()),BaseBank::getCode, query.getCode())
                .like(StrUtil.isNotEmpty(query.getName()),BaseBank::getName, query.getName())
                .orderByDesc(BaseBank::getSort);
        return (Page<BaseBank>) baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<BaseBank> list(BaseBankQuery query) {
        LambdaQueryWrapper<BaseBank> queryWrapper = new LambdaQueryWrapper<BaseBank>()
                .eq(StrUtil.isNotEmpty(query.getCode()),BaseBank::getCode, query.getCode())
                .like(StrUtil.isNotEmpty(query.getName()),BaseBank::getName, query.getName())
                .orderByDesc(BaseBank::getSort);
        return baseMapper.selectList(queryWrapper);
    }
    
}
