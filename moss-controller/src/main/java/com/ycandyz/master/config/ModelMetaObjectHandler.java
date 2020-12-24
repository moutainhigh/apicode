package com.ycandyz.master.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ycandyz.master.constant.FieldConstant;
import com.ycandyz.master.utils.LoginUserHolder;
import com.ycandyz.master.utils.SpringUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author sangang
 */
//@Component
public class ModelMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        /*Object createDate = this.getFieldValByName(FieldConstant.CREATE_DATE, metaObject);
        if (null == createDate) {
            this.setFieldValByName(FieldConstant.CREATE_DATE, new Date(), metaObject);
        }*/
        Object createBy = this.getFieldValByName(FieldConstant.CREATE_BY, metaObject);
        if (null == createBy) {
            this.setFieldValByName(FieldConstant.CREATE_BY, getLoginUserId(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        /*Object updateDate = this.getFieldValByName(FieldConstant.UPDATE_DATE, metaObject);
        if (updateDate == null) {
        	this.setFieldValByName(FieldConstant.UPDATE_DATE, new Date(), metaObject);
		}*/
        Object updateBy = this.getFieldValByName(FieldConstant.UPDATE_BY, metaObject);
        if (null == updateBy) {
            this.setFieldValByName(FieldConstant.UPDATE_BY, getLoginUserId(), metaObject);
        }
    }

    private Long getLoginUserId() {
        return SpringUtil.getBean(LoginUserHolder.class).getUserId();
    }

}
