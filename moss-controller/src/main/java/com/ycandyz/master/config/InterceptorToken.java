package com.ycandyz.master.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ycandyz.master.entities.CommonResult;
import com.ycandyz.master.entities.user.User;
import com.ycandyz.master.enums.ResultEnum;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.user.IUserService;
import com.ycandyz.master.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author: WangYang
 * @Date: 2020/9/23 16:41
 * @Description:
 */

@Slf4j
@Component
@Order(1)
public class InterceptorToken implements HandlerInterceptor {

    @Resource
    private IUserService userService;

    @Value(value = "${token.authConfigSecret}")
    private String authConfigSecret;

    @Value("${exclude.path}")
    private String excludePath;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String path = httpServletRequest.getServletPath();
        log.info(path);

        String[] excludeUrl = {"/instances",
                "/actuator/**",
                "/druid/**",
                "/assets/**",
                "/webjars/**",
                "/configuration/ui",
                "/swagger-resources",
                "/configuration/security",
                "/swagger-ui.html",
                "/docs.html",
                "/doc.html",
                "/swagger-resources/**",
                "/v2/api-docs"};
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean flow = Arrays.stream(excludeUrl).anyMatch(p -> antPathMatcher.match(p,path));
        if (excludePath.contains(path) || flow) {
            log.info("requestUrl: {}", path);
            // 请求放行
            return true;
        }
        String url = httpServletRequest.getRequestURI();
        String token = httpServletRequest.getHeader("x-auth-token");
        String method = httpServletRequest.getMethod();
        if (!method.equals("OPTIONS")){
            log.info(token);
            log.info(url);

            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;

            CommonResult result = null;
            Integer userId = 0;
            String shopNo = "";
            if(StrUtil.isNotEmpty(token)){
                try {
//                    userId = TokenUtil.verifyToken(token, authConfigSecret);
                    JSONObject jsonObject = TokenUtil.verifyToken(token, authConfigSecret);
                    userId = jsonObject.getInt("user_id");
                    shopNo = jsonObject.getStr("shop_no");
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
                    }else {
                        //获取到用户信息
                        //查看shop_no
                        UserVO userVO = userToUserVO(ukeUser);
                        userVO.setShopNo(shopNo);
                        httpServletRequest.getSession().setAttribute("currentUser", userVO);
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

    private UserVO userToUserVO(User user){
        UserVO userVO = new UserVO();
        userVO.setAppstoreId(user.getAppstoreId());
        userVO.setDisableReason(user.getDisableReason());
        userVO.setEmail(user.getEmail());
        userVO.setHeadimg(user.getHeadimg());
        userVO.setId(user.getId());
        userVO.setInviteCode(user.getInviteCode());
        userVO.setInviteStatus(user.getInviteStatus());
        userVO.setInviteUserId(user.getInviteUserId());
        userVO.setIsAuth(user.getIsAuth());
        userVO.setIsDel(user.getIsDel());
        userVO.setIsDisable(user.getIsDisable());
        userVO.setName(user.getName());
        userVO.setNickname(user.getNickname());
        userVO.setPhone(user.getPhone());
        userVO.setRegistDevice(user.getRegistDevice());
        userVO.setRegistPlatfrom(user.getRegistPlatfrom());
        userVO.setSex(user.getSex());
        userVO.setWxGzhOpenId(user.getWxGzhOpenId());
        userVO.setWxId(user.getWxId());
        userVO.setWxMiniOpenId(user.getWxMiniOpenId());
        userVO.setWxOpenId(user.getWxOpenId());
        userVO.setWxUnionId(user.getWxUnionId());
        userVO.setBlockChainId(user.getBlockChainId());
        return userVO;
    }
}
