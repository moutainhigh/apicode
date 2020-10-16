package com.ycandyz.master.controller.mall.goodsManage;


import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.dto.mall.goodsManage.MallCategoryDTO;
import com.ycandyz.master.entities.CommonResult;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.goodsManage.MallItemService;
import com.ycandyz.master.vo.MallItemVO;
import com.ycandyz.master.vo.MallSpecSingleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value="商品分类",tags={"商品管理-商品动态接口"})
@RestController
@RequestMapping("/mallItem")
@Slf4j
public class MallItemController {

    @Resource
    private MallItemService mallItemService;


    @ApiOperation(value = "添加商品",notes = "添加",httpMethod = "POST")
    @PostMapping("/item")
    public CommonResult<List<MallCategoryDTO>> addMallItem(@RequestBody MallItemVO MallItemVO, @CurrentUser UserVO user){
        mallItemService.addMallItem(MallItemVO,user);
        return CommonResult.success(null);
    }


    @ApiOperation(value = "获取商品详情",notes = "查询",httpMethod = "GET")
    @GetMapping("/item/{itemNo}")
    public CommonResult<MallItemVO> selMallItemByitemNo(@PathVariable(value = "itemNo") String itemNo, @CurrentUser UserVO userVO){
        MallItemVO mallItemVO = mallItemService.selMallItemByitemNo(userVO,itemNo);
        log.info("itemNo:{}；获取商品详情:{}",itemNo,mallItemVO);
        return CommonResult.success(mallItemVO);
    }

}
