package com.ycandyz.master.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author: WangYang
 * @Date: 2020/9/22 10:22
 * @Description:
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private InterceptorToken tokenInterceptor;
    @Autowired
    private InterceptorLog logInterceptor;
    @Autowired
    private InterceptorAuth authInterceptor;
    @Autowired
    private com.ycandyz.master.auth.CurrentUserHandlerMethodArgReslover CurrentUserHandlerMethodArgReslover;

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        /* 是否允许请求带有验证信息 */
        corsConfiguration.setAllowCredentials(true);
        /* 允许访问的客户端域名 */
        corsConfiguration.addAllowedOrigin("*");
        /* 允许服务端访问的客户端请求头 */
        corsConfiguration.addAllowedHeader("*");
        /* 允许访问的方法名,GET POST等 */
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 注册 token 验证拦截器
         registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns("/META-INF/**/**","/swagger-ui.html/**","/swagger-resources");
        registry.addInterceptor(logInterceptor);
        registry.addInterceptor(authInterceptor);
    }

    /**
     * 参数解析器
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(CurrentUserHandlerMethodArgReslover);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/favicon.ico")
//                .addResourceLocations("classpath:/static/images/favicon.ico");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/webjars/");
//    }

}