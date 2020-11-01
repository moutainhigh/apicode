package com.ycandyz.master.config;

import com.ycandyz.master.constant.SecurityConstant;
import com.ycandyz.master.utils.ConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("深圳有传科技有限公司-接口文档")
                .description("有传管理后台接口文档")
                .version("9.0")
                .contact(new Contact("有传科技","https://ycandyz.com/","wangyang@ycandyz.com"))
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

    @Bean
    public Docket swaggerApi() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder token = new ParameterBuilder();
        token.name(SecurityConstant.JWT_TOKEN).defaultValue(ConfigUtils.getValue(Config.DEFAULT_TOKEN)).description("header中的登录token").modelRef(new ModelRef("string")).parameterType(SecurityConstant.JWT_TOKEN_HEADER).required(false).build();
        ParameterBuilder tokenMenuId = new ParameterBuilder();
        tokenMenuId.name("menu_id").defaultValue("").description("header中menu_id").modelRef(new ModelRef("string")).parameterType(SecurityConstant.JWT_TOKEN_HEADER).required(false).build();
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

}
