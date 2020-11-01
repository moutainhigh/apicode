package com.ycandyz.master.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.api.ReturnResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 处理返回json数据的类
 */
@Slf4j
@ControllerAdvice
@Order(0)
public class ResponseReturnConfiguration implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body!=null){
            log.info("begin----" + body.toString());
            if (body instanceof ReturnResponse) {
                ReturnResponse returnResponse = (ReturnResponse) body;
                Object data = returnResponse.getData();
                if (data instanceof Page) {
                    Page page = (Page) data;
                    PageResult pageResult = new PageResult();
                    pageResult.setPage(page.getCurrent());
                    pageResult.setPage_size(page.getSize());
                    pageResult.setResult(page.getRecords());
                    pageResult.setTotal(page.getTotal());
                    returnResponse.setData(pageResult);
                }
                log.info("after----" + returnResponse.toString());
                return returnResponse;
            }
        }
        return body;
    }
}
