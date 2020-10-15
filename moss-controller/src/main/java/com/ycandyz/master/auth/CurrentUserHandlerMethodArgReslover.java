package com.ycandyz.master.auth;

import com.ycandyz.master.model.user.UserVO;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @CurrentUser注解实现类
 * @author: gongxp
 */
@Component
public class CurrentUserHandlerMethodArgReslover implements HandlerMethodArgumentResolver {

    /**
     * 判断注解@CurrentUser是否可以支持
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果该参数注解有@CurrentUser且参数类型是User
        return parameter.getParameterAnnotation(com.ycandyz.master.auth.CurrentUser.class) != null &&parameter.getParameterType() == UserVO.class;
    }

    /**
     * 注入参数值
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取得HttpServletRequest
        HttpServletRequest request= (HttpServletRequest) webRequest.getNativeRequest();
        //取出session中的User
        return (UserVO)request.getSession().getAttribute("currentUser");
    }
}
