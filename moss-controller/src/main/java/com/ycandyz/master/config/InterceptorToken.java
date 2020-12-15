package com.ycandyz.master.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.constant.SecurityConstant;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.service.mall.impl.MallShopServiceImpl;
import com.ycandyz.master.utils.ConfigUtils;
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
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    private MallShopServiceImpl mallShopService;

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
        String url = httpServletRequest.getRequestURI();
        String token = httpServletRequest.getHeader(SecurityConstant.JWT_TOKEN);
        String method = httpServletRequest.getMethod();
        if (!method.equals("OPTIONS")){
            log.info(token);
            log.info(url);

            AntPathMatcher antPathMatcher = new AntPathMatcher();
            String[] excludeUrls = ArrayUtils.addAll(SecurityConstant.PATTERN_URLS, ignoreUrlsConfig.getUrls());
            boolean flow = Arrays.stream(excludeUrls).anyMatch(p -> antPathMatcher.match(p,path));

            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;

            CommonResult result = null;
            Long userId = null;
            String shopNo = "";
            Long organizeId = null;
            Integer platform = null;
            if(StrUtil.isNotEmpty(token)){

                if(!ConfigUtils.getBoolean(Config.ENABLED)){
                    if (!redisUtil.hasKey(token)){
                        log.info("redis不存在该token-----------");
                        returnJson(httpServletResponse);
                        return false;
                    }
                    //更新redis中token的过期时间
                    redisUtil.set(token,"1",60*60); //token过期时间为一小时
                }

                try {
//                    userId = TokenUtil.verifyToken(token, authConfigSecret);
                    JSONObject jsonObject = TokenUtil.verifyToken(token, authConfigSecret);
                    userId = jsonObject.getLong("user_id");
                    shopNo = jsonObject.getStr("shop_no");
                    //获取企业ID
                    organizeId = jsonObject.getLong("organize_id");
                    platform = jsonObject.getInt("platform");
                }catch (JSONException e){
                    out(httpServletResponse,CommonResult.validateFailed("token解析失败"));
                    return false;
                }
                if(userId == null || userId == 0){
                    out(httpServletResponse,CommonResult.validateFailed("用户ID不正确"));
                    return false;
                }else{
                    //校验用户信息
                    //User ukeUser = userService.getById(userId);
                    //查看shop_no
                    UserVO userVO = new UserVO();
                    userVO.setId(userId);
                    userVO.setOrganizeId(organizeId);
                    userVO.setPlatform(platform);
                    if((StrUtil.isEmpty(shopNo) || SecurityConstant.DEFAULT_SHOP_NO.equals(shopNo)) && organizeId != null){
                        LambdaQueryWrapper<MallShop> queryWrapper = new LambdaQueryWrapper<MallShop>()
                                .eq(MallShop::getOrganizeId, organizeId);
                        MallShop mallShop = mallShopService.getOne(queryWrapper);
                        if(null != mallShop){
                            userVO.setShopNo(mallShop.getShopNo());
                        }
                    }
                    httpServletRequest.getSession().setAttribute(SecurityConstant.USER_TOKEN_HEADER, userVO);
                }
            }else{
                if (flow) {
                    return true;
                }
                out(httpServletResponse,CommonResult.unauthorized(null));
                return false;
            }
            return true;
        }
        return true;
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
            out.println(JSONUtil.toJsonStr(result));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 返回给前端指定错误码
     * @param response
     */
    private void returnJson(HttpServletResponse response){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            Map<String, Object> result = new HashMap<>();
            result.put("code",1100);
            result.put("msg","登录令牌过期");
            JSONObject jsonObject = JSONUtil.parseObj(result);
            writer.print(jsonObject);
        } catch (IOException e){
            log.error("拦截器输出流异常"+e);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
