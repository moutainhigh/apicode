package com.ycandyz.master.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ycandyz.master.constant.SecurityConstant;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.user.UserNodeQuery;
import com.ycandyz.master.domain.response.user.UserNodeResp;
import com.ycandyz.master.entities.CommonResult;
import com.ycandyz.master.entities.user.User;
import com.ycandyz.master.entities.user.UserRole;
import com.ycandyz.master.enums.ResultEnum;
import com.ycandyz.master.service.user.IUserRoleService;
import com.ycandyz.master.service.user.IUserService;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.RedisUtil;
import com.ycandyz.master.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private IUserRoleService UserRoleService;

    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private RedisUtil redisUtil;

    @Value(value = "${token.authConfigSecret}")
    private String authConfigSecret;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String path = httpServletRequest.getServletPath();
        log.info(path);
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String[] excludeUrls = ArrayUtils.addAll(SecurityConstant.PATTERN_URLS, ignoreUrlsConfig.getUrls());
        boolean flow = Arrays.stream(excludeUrls).anyMatch(p -> antPathMatcher.match(p,path));
        if (flow) {
            log.info("requestUrl: {}", path);
            // 请求放行
            return true;
        }
        String url = httpServletRequest.getRequestURI();
        String token = httpServletRequest.getHeader(SecurityConstant.JWT_TOKEN);
        String menuIdStr = httpServletRequest.getHeader(SecurityConstant.MENU_ID);
        AssertUtils.notNull(menuIdStr, "menuId不能为空");
        Long menuId = Long.parseLong(menuIdStr);
        String method = httpServletRequest.getMethod();
        if (!method.equals("OPTIONS")){
            log.info(token);
            log.info(url);

            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;

            CommonResult result = null;
            Long userId = null;
            String shopNo = "";
            Long organizeId = null;
            if(StrUtil.isNotEmpty(token)){

//                if (!redisUtil.hasKey(token)){
//                    //如果redis中不存在当前key，则返回key获取，重新登录
//                    return false;
//                }

                try {
//                    userId = TokenUtil.verifyToken(token, authConfigSecret);
                    JSONObject jsonObject = TokenUtil.verifyToken(token, authConfigSecret);
                    userId = jsonObject.getLong("user_id");
                    shopNo = jsonObject.getStr("shop_no");
                    organizeId = jsonObject.getLong("organize_id");
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
                        AssertUtils.notNull(organizeId, "organizeId未获取到");
                        //先判断是否超管
                        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>()
                                .eq(UserRole::getUserId, userId)
                                .eq(UserRole::getOrganizeId,organizeId)
                                .eq(UserRole::getPlatform,4)
                                .eq(UserRole::getStatus,0)
                                .eq(UserRole::getIsBoss,0);
                        UserRole userRole = UserRoleService.getOne(queryWrapper);
                        if(null != userRole){
                            UserVO userVO = userToUserVO(ukeUser);
                            userVO.setShopNo(shopNo);
                            httpServletRequest.getSession().setAttribute(SecurityConstant.USER_TOKEN_HEADER, userVO);
                            return true;
                        }
                        //校验接口权限
                        UserNodeQuery query = new UserNodeQuery();
                        query.setMenuId(menuId);
                        query.setUserId(userId);
                        query.setOrganizeId(organizeId);
                        List<UserNodeResp> list = userService.selectUserNode(query);
                        if(CollectionUtil.isNotEmpty(list)){
                            //匹配路径
                            String httpPath = method+"|"+path;
                            List<String> uriList = list.stream().map(UserNodeResp::getUri).collect(Collectors.toList());
                            boolean isTrue = uriList.stream().anyMatch(p -> antPathMatcher.match(p,httpPath));
                            if (isTrue) {
                                log.info("httpPath: {}", httpPath);
                                // 请求放行
                                UserVO userVO = userToUserVO(ukeUser);
                                userVO.setShopNo(shopNo);
                                httpServletRequest.getSession().setAttribute(SecurityConstant.USER_TOKEN_HEADER, userVO);
                                return true;
                            }else {
                                //提示无权限
                                result = new CommonResult(ResultEnum.FORBIDDEN.getValue(),ResultEnum.FORBIDDEN.getDesc(),null);
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
                        }else {
                            //提示无权限
                            result = new CommonResult(ResultEnum.FORBIDDEN.getValue(),ResultEnum.FORBIDDEN.getDesc(),null);
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
