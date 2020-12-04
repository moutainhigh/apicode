package com.ycandyz.master.controller.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ycandyz.master.constant.SecurityConstant;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.enums.OperateEnum;
import com.ycandyz.master.utils.AssertUtil;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.PreventUtil;
import com.ycandyz.master.utils.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * @Description 业务基础类
 * </p>
 *
 * @author sangang
 * @since 2019-07-14
 * @version 2.0
 */
public abstract class BaseService<M extends BaseMapper<T>, T extends Model, Q> extends ServiceImpl<M, T> {

    @Autowired
    protected HttpServletRequest request;

    protected UserVO getUser() {
        return (UserVO)request.getSession().getAttribute(SecurityConstant.USER_TOKEN_HEADER);
    }

    protected Long getUserId() {
        if(getUser() == null){
            return null;
        }
        return getUser().getId();
    }

    protected String getUsername() {
        return getUser().getName();
    }

    /**
     * 获取当前用户登录的店铺编号
     */
    protected String getShopNo() {
        if(getUser() == null){
            return null;
        }
        return getUser().getShopNo();
    }

    public boolean remove(Wrapper wrapper) {
        return SqlHelper.retBool(baseMapper.delete(wrapper));
    }

    public boolean insertBatch(Collection<T> entityList) {
        for (T entity : entityList) {
            AssertUtil.checkout(entity, OperateEnum.INSERT);
        }
        return super.saveBatch(entityList, entityList.size());
    }

    public boolean updateById(T entity) {
        PreventUtil.filtrate(entity, OperateEnum.UPDATE);
        return super.updateById(entity);
    }

    @SuppressWarnings("unchecked")
    public boolean deleteByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @SuppressWarnings("unchecked")
    public boolean deleteByIds(Long[] ids) {
        return super.removeByIds(CollUtil.toList(ids));
    }

    public T getById(Long id) {
        return baseMapper.selectById(id);
    }

    @SuppressWarnings("unchecked")
    public List<T> list(Q query) {
        return baseMapper.selectList(QueryUtil.buildWrapper(query, false));
    }

    @SuppressWarnings("unchecked")
    public Page<T> page(Page<T> page, Q query) {
        return (Page<T>) baseMapper.selectPage(page, QueryUtil.buildWrapper(query, false));
    }

}
