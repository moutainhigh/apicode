package com.ycandyz.master.controller.risk;

import com.alibaba.fastjson.JSON;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.risk.TabooCheckNotInsertParams;
import com.ycandyz.master.domain.query.risk.TabooCheckParams;
import com.ycandyz.master.service.risk.TaboowordsCheckService;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/audit/content/sensitive-word-check")
@Api(value="敏感词检测",tags={"敏感词检测-动态接口"})
@Slf4j
public class TaboowordsCheckController {

    @Autowired
    private TaboowordsCheckService taboowordsCheckService;

//    @ApiOperation(value = "敏感词检测",notes = "敏感词检测",httpMethod = "POST")
//    @PostMapping("/check")
//    public ReturnResponse check(@Validated(ValidatorContract.OnCreate.class)  @RequestBody TabooCheckParams tabooCheckParams) {
//        log.info("敏感词检测请求入参:{}", JSON.toJSONString(tabooCheckParams));
//        ReturnResponse check = taboowordsCheckService.check(tabooCheckParams);
//        log.info("敏感词检测请求响应:{}", JSON.toJSONString(check));
//        return check;
//    }

    @ApiOperation(value = "敏感词检测",notes = "敏感词检测",httpMethod = "POST")
    @PostMapping
    public ReturnResponse checkNotInsert(@Validated(ValidatorContract.OnCreate.class)  @RequestBody TabooCheckNotInsertParams tabooCheckParams) {
        log.info("敏感词检测-不插入审核表请求入参:{}", JSON.toJSONString(tabooCheckParams));
        ReturnResponse check = taboowordsCheckService.checkNotInsert(tabooCheckParams);
        log.info("敏感词检测-不插入审核表请求响应:{}", JSON.toJSONString(check));
        return check;
    }

}
