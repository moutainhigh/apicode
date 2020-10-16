package com.ycandyz.master.controller.mall.goodsManage;


import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.dto.mall.goodsManage.MallCategoryDTO;
import com.ycandyz.master.entities.CommonResult;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.goodsManage.MallItemService;
import com.ycandyz.master.vo.MallItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/category")
    public CommonResult<List<MallCategoryDTO>> addMallItem(@RequestBody MallItemVO MallItemVO, @CurrentUser UserVO user){
        mallItemService.addMallItem(MallItemVO,user);
        return CommonResult.success(null);
    }


}
