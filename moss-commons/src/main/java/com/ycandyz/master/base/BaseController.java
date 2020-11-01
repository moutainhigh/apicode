package com.ycandyz.master.base;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ycandyz.master.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * @Description 接口父类
 * </p>
 *
 * @author sangang
 * @since 2019-07-14
 * @version 2.0
 */

@Slf4j
public abstract class BaseController<S extends BaseService,T extends Model,Q > {

    @Autowired
    protected S service;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    protected <T> CommonResult<T> result(boolean flow, T data, String msg) {
        if (flow){
            return CommonResult.success(data);
        }
        return CommonResult.failed(msg);
    }
    
}
