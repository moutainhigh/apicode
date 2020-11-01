package com.ycandyz.master.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangang
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ignored")
public class IgnoreUrlsConfig {

    private String[] urls;

    private String[] authUrls;
    
    private List<String> ips = new ArrayList<>();
    
}
