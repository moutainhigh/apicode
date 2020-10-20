package com.ycandyz.master.controller.mall.goodsManage;


import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.dto.mall.goodsManage.MallCategoryDTO;
import com.ycandyz.master.dto.mall.goodsManage.MallItemDTO;
import com.ycandyz.master.entities.CommonResult;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.goodsManage.MallItemService;
import com.ycandyz.master.vo.MallCategoryVO;
import com.ycandyz.master.vo.MallItemOprVO;
import com.ycandyz.master.vo.MallItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value="商品分类",tags={"商品管理-商品动态接口"})
@RestController
@RequestMapping("/mallItem")
@Slf4j
public class MallItemController {

    @Resource
    private MallItemService mallItemService;


    @ApiOperation(value = "添加商品",notes = "添加",httpMethod = "POST")
    @PostMapping("/item")
    public CommonResult addMallItem(@RequestBody MallItemVO MallItemVO, @CurrentUser UserVO user){
        mallItemService.addMallItem(MallItemVO,user);
        return CommonResult.success(null);
    }


    @ApiOperation(value = "获取商品详情",notes = "查询",httpMethod = "GET")
    @GetMapping("/item/{itemNo}")
    public CommonResult<MallItemDTO> selMallItemByitemNo(@PathVariable(value = "itemNo") String itemNo, @CurrentUser UserVO userVO){
        MallItemDTO mallItemDTO = mallItemService.selMallItemByitemNo(userVO,itemNo);
        log.info("itemNo:{}；获取商品详情:{}",itemNo,mallItemDTO);
        return CommonResult.success(mallItemDTO);
    }


    @ApiOperation(value = "上下架商品",notes = "修改",httpMethod = "PUT")
    @PutMapping("/item/opr")
    public CommonResult<Integer> oprbyItemNo(@RequestBody MallItemOprVO mallItemOprVO, @CurrentUser UserVO userVO){
        int count = mallItemService.oprbyItemNo(userVO,mallItemOprVO);
        log.info("userVO:{}；上下架商品:{}",userVO,mallItemOprVO);
        if (count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed(count);
    }

    @ApiOperation(value = "删除商品",notes = "删除",httpMethod = "DELETE")
    @DeleteMapping("/item/{itemNo}")
    public CommonResult delMallItemByItemNo(@PathVariable(value = "itemNo") String itemNo, @CurrentUser UserVO userVO){
        int count = mallItemService.delMallItemByItemNo(itemNo,userVO);
        if (count > 0) {
            return CommonResult.success(null);
        }
        return CommonResult.failed(null);
    }

    @ApiOperation(value = "编辑商品分类",notes = "put",httpMethod = "PUT")
    @PutMapping("/item")
    public CommonResult updateMallItem(@RequestBody MallItemVO mallItemVO, @CurrentUser UserVO userVO){
        log.info("修改运费模版请求参数:{}",mallItemVO);
        mallItemService.updateMallItem(mallItemVO,userVO);
        return CommonResult.success(null);
    }

}
