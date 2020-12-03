package com.ycandyz.master.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.constant.SecurityConstant;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.user.UserNodeQuery;
import com.ycandyz.master.domain.response.user.UserNodeResp;
import com.ycandyz.master.entities.user.UserRole;
import com.ycandyz.master.enums.PlatformEnum;
import com.ycandyz.master.service.user.INodeService;
import com.ycandyz.master.service.user.IUserRoleService;
import com.ycandyz.master.service.user.IUserService;
import com.ycandyz.master.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 授权拦截器
 * SanGang
 */

@Slf4j
@Component
@Order(3)
public class InterceptorAuth implements HandlerInterceptor {

    @Resource
    private IUserService userService;
    @Resource
    private IUserRoleService UserRoleService;
    @Autowired
    private INodeService nodeService;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String path = httpServletRequest.getServletPath();
        String method = httpServletRequest.getMethod();
        String httpPath = method+"|"+path;
        log.info(path);
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String[] excludeUrls = ArrayUtils.addAll(SecurityConstant.PATTERN_URLS, ignoreUrlsConfig.getUrls());
        boolean flow = Arrays.stream(excludeUrls).anyMatch(p -> antPathMatcher.match(p,path));
        if (flow) {
            log.info("requestUrl: {}", path);
            return true;
        }
        if (method.equals("OPTIONS")){
            return true;
        }
        UserVO user = (UserVO)httpServletRequest.getSession().getAttribute(SecurityConstant.USER_TOKEN_HEADER);
        String menuIdStr = httpServletRequest.getHeader(SecurityConstant.MENU_ID);
        if(StrUtil.isEmpty(menuIdStr)){
            AssertUtils.notNull(null, "menu_id不能为空");
        }
        Long menuId = Long.parseLong(menuIdStr);
        user.setMenuId(menuId);
        PlatformEnum platformEnum = PlatformEnum.parseCode(user.getPlatform());
        AssertUtils.notNull(platformEnum, "platform不正确");
        //目前只对U客管理后台、U客APP做权限
        if(platformEnum.getCode() < PlatformEnum.TYPE_4.getCode()){
            return true;
        }
        //需要做权限的接口
        boolean authFlow = false;

        List<String> nodes = nodeService.getAllNode();
        if(CollectionUtil.isNotEmpty(nodes)){
            authFlow = nodes.stream().anyMatch(p -> antPathMatcher.match(p,httpPath));
        }
        if(authFlow){
            PrintWriter out = null ;
            CommonResult result = null;
            AssertUtils.notNull(user, "用户信息未获取到");
            AssertUtils.notNull(user.getPlatform(), "platform未获取到");
            AssertUtils.notNull(user.getOrganizeId(), "organizeId未获取到");
            //先判断是否超管
            LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>()
                    .eq(UserRole::getUserId, user.getId())
                    .eq(UserRole::getOrganizeId,user.getOrganizeId())
                    .eq(UserRole::getPlatform,user.getPlatform())
                    .eq(UserRole::getStatus,0)
                    .eq(UserRole::getRoleId,0);
            UserRole userRole = UserRoleService.getOne(queryWrapper);
            if(null != userRole){
                return true;
            }
            //校验接口权限
            UserNodeQuery query = new UserNodeQuery();
            query.setMenuId(user.getMenuId());
            query.setUserId(user.getId());
            query.setOrganizeId(user.getOrganizeId());
            query.setPlatform(user.getPlatform());
            List<UserNodeResp> list = userService.selectUserNode(query);
            if(CollectionUtil.isNotEmpty(list)){
                //匹配路径
                List<String> uriList = list.stream().map(UserNodeResp::getUri).collect(Collectors.toList());
                boolean isTrue = uriList.stream().anyMatch(p -> antPathMatcher.match(p,httpPath));
                if (isTrue) {
                    log.info("httpPath: {}", httpPath);
                    return true;
                }else {
                    //提示无权限
                    out(httpServletResponse, CommonResult.forbidden(null));
                    return false;
                }
            }else {
                //提示无权限
                out(httpServletResponse, CommonResult.forbidden(null));
                return false;
            }
        }else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // System.out.println("处理请求完成后视图渲染之前的处理操作");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // System.out.println("视图渲染之后的操作");
    }

    public static <T> void out(ServletResponse response, T result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSONUtil.parse(result).toJSONString(0));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
