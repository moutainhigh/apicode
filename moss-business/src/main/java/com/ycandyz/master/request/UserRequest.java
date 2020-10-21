package com.ycandyz.master.request;

import com.ycandyz.master.model.user.UserVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class UserRequest{
   public static UserVO getCurrentUser(){
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      UserVO user = (UserVO) request.getSession().getAttribute("currentUser");
      return user;
   }

}