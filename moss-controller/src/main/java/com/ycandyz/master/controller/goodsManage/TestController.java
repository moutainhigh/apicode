package com.ycandyz.master.controller.goodsManage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class TestController {

    @GetMapping("/test")
    public void test(){
        System.out.println("test");
    }
}
