package com.ycandyz.master.controller.msg;


import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.entities.msg.TransferAccountEntry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("transferAccount")
@Api(tags="转移管理员账号信息")
public class TransferAccountController {

    @ApiOperation(value = "转移管理员账号信息")
    @PostMapping
    public ReturnResponse transferAccount(@RequestBody TransferAccountEntry transferAccountEntry) {
        log.info("{}",transferAccountEntry);
        return ReturnResponse.success("已安排我司同事联系您，请保持手机畅通");
    }
}
