package com.ycandyz.master.service.mall.goodsManage.impl;

import com.github.pagehelper.PageInfo;
import com.ycandyz.master.service.mall.goodsManage.MallSpecService;
import com.ycandyz.master.vo.MallSpecKeyWordVO;
import com.ycandyz.master.vo.MallSpecVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MallSpecServiceImplTest {

    @Resource
    private MallSpecService mallSpecService;
    @Test
    public void selMallSpecByKeyWord() {
    }
}