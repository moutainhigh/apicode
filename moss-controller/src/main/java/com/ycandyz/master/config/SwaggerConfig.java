package com.ycandyz.master.config;

import com.ycandyz.master.constant.SecurityConstant;
import com.ycandyz.master.utils.ConfigUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Profile({"dev","local","test"})
public class SwaggerConfig implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("深圳有传科技有限公司-接口文档")
                .version("2.0")
                .contact(new Contact("有传科技","https://ycandyz.com/","wangyang@ycandyz.com"))
                .build();
    }

    @Bean
    public Docket swaggerApi() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder token = new ParameterBuilder();
        token.name(SecurityConstant.JWT_TOKEN).defaultValue(ConfigUtils.getValue(Config.DEFAULT_TOKEN)).description(SecurityConstant.JWT_TOKEN).modelRef(new ModelRef("string")).parameterType(SecurityConstant.JWT_TOKEN_HEADER).required(false).build();
        ParameterBuilder tokenMenuId = new ParameterBuilder();
        tokenMenuId.name(SecurityConstant.MENU_ID).defaultValue("").description("菜单ID").modelRef(new ModelRef("int")).parameterType(SecurityConstant.JWT_TOKEN_HEADER).required(false).build();
        pars.add(token.build());
        pars.add(tokenMenuId.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ycandyz.master.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(pars);
    }

    private List<ResponseMessage> customerResponseMessage() {
        List<ResponseMessage> list = new ArrayList<>();
        list.add(new ResponseMessageBuilder().code(200).message("请求成功").build());
        list.add(new ResponseMessageBuilder().code(401).message("请求失败,未经过身份认证").build());
        list.add(new ResponseMessageBuilder().code(403).message("请求失败,身份认证已过期").build());
        list.add(new ResponseMessageBuilder().code(500).message("服务端异常").build());
        return list;
    }

    private Docket buildDocketWithGroupName(String groupName) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(input -> {
                    if (input.getHandlerMethod().hasMethodAnnotation(ApiVersion.class)) {
                        ApiVersion apiVersion = input.getHandlerMethod().getMethodAnnotation(ApiVersion.class);
                        if (apiVersion.group() != null && apiVersion.group().length != 0) {
                            if (Arrays.asList(apiVersion.group()).contains(groupName)) {
                                return true;
                            }
                        }
                        if (apiVersion.value() != null && apiVersion.value().length != 0) {
                            if (Arrays.asList(apiVersion.value()).contains(groupName)) {
                                return true;
                            }
                        }

                    }
                    ApiVersion clzzApiVersion = input.getHandlerMethod().getBeanType().getAnnotation(ApiVersion.class);
                    if (clzzApiVersion != null) {
                        if (clzzApiVersion.group() != null && clzzApiVersion.group().length != 0) {
                            if (Arrays.asList(clzzApiVersion.group()).contains(groupName)) {
                                return true;
                            }
                        }
                        if (clzzApiVersion.value() != null && clzzApiVersion.value().length != 0) {
                            if (Arrays.asList(clzzApiVersion.value()).contains(groupName)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })//controller路径
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 根据ApiConstantVersion构造 docket
        Class<ApiVersionConstant> clzz = ApiVersionConstant.class;
        Field[] declaredFields = clzz.getDeclaredFields();
        // 动态注入bean
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if(autowireCapableBeanFactory instanceof DefaultListableBeanFactory){
            DefaultListableBeanFactory capableBeanFactory = (DefaultListableBeanFactory) autowireCapableBeanFactory;
            for (Field declaredField : declaredFields) {
                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().setFactoryMethodOnBean("buildDocketWithGroupName", "swaggerConfig")
                        .addConstructorArgValue(declaredField.get(ApiVersionConstant.class)).getBeanDefinition();
                capableBeanFactory.registerBeanDefinition(declaredField.getName(), beanDefinition);
            }
        }
    }

}
