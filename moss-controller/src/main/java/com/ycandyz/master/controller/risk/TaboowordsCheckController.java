package com.ycandyz.master.controller.risk;

import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.risk.TabooCheckNotInsertParams;
import com.ycandyz.master.domain.query.risk.TabooCheckParams;
import com.ycandyz.master.service.risk.TaboowordsCheckService;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taboowordsCheck")
@Api(value="敏感词检测",tags={"敏感词检测-动态接口"})
public class TaboowordsCheckController {

    @Autowired
    private TaboowordsCheckService taboowordsCheckService;

    @ApiOperation(value = "敏感词检测",notes = "敏感词检测",httpMethod = "POST")
    @PostMapping("/check")
    public ReturnResponse check(@Validated(ValidatorContract.OnCreate.class)  @RequestBody TabooCheckParams tabooCheckParams) {
        return taboowordsCheckService.check(tabooCheckParams);
    }

    @ApiOperation(value = "敏感词检测-不插入审核表",notes = "敏感词检测-不插入审核表",httpMethod = "POST")
    @PostMapping("/checkNotInsert")
    public ReturnResponse checkNotInsert(@Validated(ValidatorContract.OnCreate.class)  @RequestBody TabooCheckNotInsertParams tabooCheckParams) {
        return taboowordsCheckService.checkNotInsert(tabooCheckParams);
    }

}
