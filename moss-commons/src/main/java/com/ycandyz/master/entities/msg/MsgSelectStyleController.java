package com.ycandyz.master.entities.msg;


import com.ycandyz.master.api.ReturnResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("selectMoudle")
@Api(tags="选择并修改样式")
public class MsgSelectStyleController {

    @ApiOperation(value = "选择样式")
    @GetMapping
    public ReturnResponse selectMoudle(@PathVariable Long moudleId) {
        log.info("{}",moudleId);
        return ReturnResponse.success("选择样式id:"+moudleId);
    }

    @ApiOperation(value = "编辑样式")
    @PutMapping
    public ReturnResponse modifyMoudle(@PathVariable Long moudleId,Long baseId,String moudleName) {
        log.info("{},{},{}",moudleId,baseId,moudleName);
        return ReturnResponse.success("修改样式:"+moudleId+"-"+baseId+"-"+moudleName);
    }

    @ApiOperation(value = "删除样式")
    @PutMapping
    public ReturnResponse deleteMoudle(@PathVariable Long moudleId,Long baseId) {
        log.info("{},{}",moudleId,baseId);
        return ReturnResponse.success("删除样式:"+moudleId+"-"+baseId);
    }

    @ApiOperation(value = "拖动样式")
    @PutMapping
    public ReturnResponse deleteMoudle(@PathVariable Long moudleId,Long baseId,Integer sortModuleNum) {
        log.info("{},{},{}",moudleId,baseId,sortModuleNum);
        return ReturnResponse.success("拖动样式:"+moudleId+"-"+baseId+"-"+sortModuleNum);
    }
}
