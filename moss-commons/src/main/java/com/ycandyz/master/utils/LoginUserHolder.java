package com.ycandyz.master.utils;

import com.ycandyz.master.constant.SecurityConstant;
import com.ycandyz.master.domain.UserVO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sangang
 */
@Component
public class LoginUserHolder {

    public UserVO getLoginUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserVO user = (UserVO) request.getSession().getAttribute(SecurityConstant.USER_TOKEN_HEADER);
        return user;
    }

    public String getUsername() {
        if (getLoginUser() == null) return null;
        return getLoginUser().getName();
    }

    public Long getUserId() {
        if (getLoginUser() == null) return null;
        return getLoginUser().getId();
    }

}
