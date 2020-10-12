package com.ycandyz.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MasterControllerMain {

    @RequestMapping("/")
    public String index() {
        return "200";
    }

    public static void main(String[] args) {
        SpringApplication.run(MasterControllerMain.class,args);
    }
}
