package com.ycandyz.master.controller.mall.goodsManage;


import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dto.mall.goodsManage.MallItemDTO;
import com.ycandyz.master.service.mall.goodsManage.MallItemService;
import com.ycandyz.master.vo.MallItemOprVO;
import com.ycandyz.master.vo.MallItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value="商品分类",tags={"商品管理-商品管理动态接口"})
@RestController
@RequestMapping("/mallItem")
@Slf4j
public class MallItemController {

    @Resource
    private MallItemService mallItemService;


    @ApiOperation(value = "添加商品",notes = "添加",httpMethod = "POST")
    @PostMapping("/item")
    public ReturnResponse addMallItem(@RequestBody MallItemVO MallItemVO){
        mallItemService.addMallItem(MallItemVO);
        return ReturnResponse.success("添加商品成功");
    }


    @ApiOperation(value = "获取商品详情",notes = "查询",httpMethod = "GET")
    @GetMapping("/item/{itemNo}")
    public ReturnResponse<MallItemDTO> selMallItemByitemNo(@PathVariable(value = "itemNo") String itemNo){
        MallItemDTO mallItemDTO = mallItemService.selMallItemByitemNo(itemNo);
        log.info("itemNo:{}；获取商品详情:{}",itemNo,mallItemDTO);
        return ReturnResponse.success(mallItemDTO);
    }


    @ApiOperation(value = "上下架商品",notes = "修改",httpMethod = "PUT")
    @PutMapping("/item/opr")
    public ReturnResponse<Integer> oprbyItemNo(@RequestBody MallItemOprVO mallItemOprVO){
        int count = mallItemService.oprbyItemNo(mallItemOprVO);
        log.info("上下架商品:{}",mallItemOprVO);
        if (count > 0){
            return ReturnResponse.success(count);
        }
        return ReturnResponse.failed("商品上下架失败");
    }

    @ApiOperation(value = "删除商品",notes = "删除",httpMethod = "DELETE")
    @DeleteMapping("/item/{itemNo}")
    public ReturnResponse delMallItemByItemNo(@PathVariable(value = "itemNo") String itemNo){
        int count = mallItemService.delMallItemByItemNo(itemNo);
        if (count > 0) {
            return ReturnResponse.success("删除商品成功");
        }
        return ReturnResponse.failed("删除商品失败");
    }

    @ApiOperation(value = "编辑商品分类",notes = "put",httpMethod = "PUT")
    @PutMapping("/item")
    public ReturnResponse updateMallItem(@RequestBody MallItemVO mallItemVO){
        log.info("修改运费模版请求参数:{}",mallItemVO);
        mallItemService.updateMallItem(mallItemVO);
        return ReturnResponse.success(null);
    }

}
