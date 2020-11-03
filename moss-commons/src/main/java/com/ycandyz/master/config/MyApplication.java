package com.ycandyz.master.config;

import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @program: ycandy-uke-manager
 * @description:
 * @author: lz
 * @create: 2020-11-01 18:23
 **/

@Getter
@Slf4j
@SpringBootApplication
public class MyApplication implements ApplicationListener<WebServerInitializedEvent> {

    private String url = "";

    @SneakyThrows
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        WebServer server = event.getWebServer();
        WebServerApplicationContext context = event.getApplicationContext();
        Environment env = context.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String hostName = InetAddress.getLocalHost().getHostName();
        int port = server.getPort();
        String contextPath = env.getProperty("server.servlet.context-path");
        if (contextPath == null) {
            contextPath = "";
        }
        log.info("\n---------------------------------------------------------\n" +
                "\tApplication is running! Access address:\n" +
                "\tLocal:\t\thttp://localhost:{}" +
                "\n\tExternal:\thttp://{}:{}{}" +
                "\n---------------------------------------------------------\n", port, ip, port, contextPath);
        this.url = "http://"+hostName+":"+port+contextPath;
    }
}
