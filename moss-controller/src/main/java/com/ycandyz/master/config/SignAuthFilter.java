package com.ycandyz.master.config;

import com.alibaba.fastjson.JSONObject;

import com.ycandyz.master.domain.BodyReaderHttpServletRequestWrapper;
import com.ycandyz.master.utils.SignUtil;
import com.ycandyz.master.utils.HttpUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedMap;

/**
 * @author: WangYang
 * @Date: 2020/11/1 10:47
 * @Description:
 */

@Slf4j
//@Component
public class SignAuthFilter implements Filter {

    @Value(value = "${sign.authConfigSecret}")
    private String authConfigSecret;

    static final String FAVICON = "/favicon.ico";

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("初始化 SignAuthFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        //获取图标不需要验证签名
        if (FAVICON.equals(requestWrapper.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            //获取全部参数(包括URL和body上的)
            SortedMap<String, Object> allParams = HttpUtils.getAllParams(requestWrapper);
            //对参数进行签名验证
            boolean isSigned = SignUtil.verifySign(allParams,authConfigSecret);

            if (isSigned) {
                log.info("签名通过");
                filterChain.doFilter(requestWrapper, response);
            } else {
                log.info("参数校验出错");
                //校验失败返回前端
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                JSONObject resParam = new JSONObject();
                resParam.put("msg", "参数校验出错");
                resParam.put("success", "false");
                out.append(resParam.toJSONString());
            }
        }

    }

    @Override
    public void destroy() {
        log.info("销毁 SignAuthFilter");
    }

    }
