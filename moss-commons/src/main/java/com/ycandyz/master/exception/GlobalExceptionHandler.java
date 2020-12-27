package com.ycandyz.master.exception;

import com.ycandyz.master.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常拦截处理
 * @author SanGang
 * @create 2020-10-14
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(HttpServletRequest request, ApiException e) {
        log.error("请求异常,地址:" + request.getRequestURL(), e);
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleValidException(HttpServletRequest request,MethodArgumentNotValidException e) {
        log.error("Method异常,地址:" + request.getRequestURL(), e);
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
            return CommonResult.validateFailed(message);
        }
        return CommonResult.validateFailed("服务端异常");
    }

    @ExceptionHandler(value = BindException.class)
    public CommonResult handleValidException(HttpServletRequest request, BindException e) {
        log.error("请求处理异常,地址:" + request.getRequestURL(), e);
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    /**
     * 自定义业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public CommonResult<String> businessExceptionHandler(HttpServletRequest request, BusinessException e) {
        log.error("业务处理异常,地址:" + request.getRequestURL(), e);
        return CommonResult.validateFailed(e.getMessage());
    }

    /**
     * Exception异常拦截处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult<String> businessExceptionHandler(HttpServletRequest request,Exception e) {
        log.error("Exception异常,地址:" + request.getRequestURL(), e);
        return CommonResult.validateFailed("服务端发生异常");
    }
}
