package com.ycandyz.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TimeZone;

@RestController
@SpringBootApplication
@EnableTransactionManagement
public class MasterControllerMain {

    @RequestMapping("/")
    public String index() {
        return "200";
    }


    public static void main(String[] args) {
        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(MasterControllerMain.class,args);
    }
}
