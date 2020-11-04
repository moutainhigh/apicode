package com.ycandyz.master.controller.risk;

import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.risk.TabooCheckParams;
import com.ycandyz.master.service.risk.TaboowordsCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TaboowordsCheck")
@Api(value="敏感词检测",tags={"敏感词检测-动态接口"})
public class TaboowordsCheckController {

    @Autowired
    private TaboowordsCheckService taboowordsCheckService;

    @ApiOperation(value = "敏感词检测",notes = "敏感词检测",httpMethod = "POST")
    @PostMapping("/check")
    public ReturnResponse check(@RequestBody RequestParams<TabooCheckParams> requestParams) {
        return taboowordsCheckService.check(requestParams);
    }

}
