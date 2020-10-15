package com.ycandyz.master.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONUtil;
import com.ycandyz.master.constants.RedisConstants;
import com.ycandyz.master.enmus.ResultEnum;
import com.ycandyz.master.entities.CommonResult;
import com.ycandyz.master.entities.user.TokenEntity;
import com.ycandyz.master.entities.user.User;
import com.ycandyz.master.service.user.IUserService;
import com.ycandyz.master.utils.RedisUtil;
import com.ycandyz.master.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: WangYang
 * @Date: 2020/9/23 16:41
 * @Description:
 */

@Slf4j
@Component
public class InterceptorToken implements HandlerInterceptor {

    @Resource
    private IUserService userService;

    @Resource
    private RedisUtil redisUtil;

    @Value(value = "${token.authConfigSecret}")
    private String authConfigSecret;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURI();
        String token = httpServletRequest.getHeader("token");
        String method = httpServletRequest.getMethod();
        if (!method.equals("OPTIONS")){
            log.info(token);
            log.info(url);

            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;

            CommonResult result = null;
            TokenEntity tokenEntity = null;
            Integer userId = 0;
            if(StrUtil.isNotEmpty(token)){
                try {
                    tokenEntity = TokenUtil.verifyToken(token, authConfigSecret);
                    userId = Integer.parseInt(tokenEntity.getUserId());
                }catch (JSONException e){
                    log.error("token 解析失败");
                    result = new CommonResult(ResultEnum.TOKEN_ILLEGAL.getValue(),ResultEnum.TOKEN_ILLEGAL.getDesc(),null);
                    String json = JSONUtil.toJsonStr(result);
                    httpServletResponse.setContentType("application/json");
                    out = httpServletResponse.getWriter();
                    out.append(json);
                    out.flush();
                    return false;
                }
                if(userId == 0){
                    result = new CommonResult(ResultEnum.TOKEN_INVALID.getValue(),ResultEnum.TOKEN_INVALID.getDesc(),null);
                    try{
                        String json = JSONUtil.toJsonStr(result);
                        httpServletResponse.setContentType("application/json");
                        out = httpServletResponse.getWriter();
                        out.append(json);
                        out.flush();
                        return false;
                    } catch (Exception e){
                        httpServletResponse.sendError(500);
                        return false;
                    }
                }else{
                    //校验用户信息
                    User ukeUser = userService.getById(userId);
                    if(ukeUser == null){
                        result = new CommonResult(ResultEnum.USER_NOT_EXIST.getValue(),ResultEnum.USER_NOT_EXIST.getDesc(),null);
                        try{
                            String json = JSONUtil.toJsonStr(result);
                            httpServletResponse.setContentType("application/json");
                            out = httpServletResponse.getWriter();
                            out.append(json);
                            out.flush();
                            return false;
                        } catch (Exception e){
                            httpServletResponse.sendError(500);
                            return false;
                        }
                    }
                }
            }else{
                result = new CommonResult(ResultEnum.TOKEN_IS_NULL.getValue(),ResultEnum.TOKEN_IS_NULL.getDesc(),null);
                try{
                    String json = JSONUtil.toJsonStr(result);
                    httpServletResponse.setContentType("application/json");
                    out = httpServletResponse.getWriter();
                    out.append(json);
                    out.flush();
                    return false;
                } catch (Exception e){
                    httpServletResponse.sendError(500);
                    return false;
                }
            }
            redisUtil.set(RedisConstants.SHOPNO,tokenEntity.getShopNo());
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // System.out.println("处理请求完成后视图渲染之前的处理操作");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // System.out.println("视图渲染之后的操作");
    }
}
