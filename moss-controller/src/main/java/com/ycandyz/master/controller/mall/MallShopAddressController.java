package com.ycandyz.master.controller.mall;

import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.auth.CurrentUser;
import com.ycandyz.master.controller.base.BaseController;
import com.ycandyz.master.domain.query.mall.MallShopAddressQuery;
import com.ycandyz.master.entities.mall.MallShopAddress;
import com.ycandyz.master.model.mall.MallShopAddressVO;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.MallShopAddressService;
import com.ycandyz.master.service.mall.impl.MallShopAddressServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mall")
@Api(tags="mall-订单商家快递地址表")
public class MallShopAddressController extends BaseController<MallShopAddressServiceImpl, MallShopAddress, MallShopAddressQuery> {

    @Autowired
    private MallShopAddressService mallShopAddressService;

    /**
     * 获取商家地址列表
     * @param userVO
     * @return
     */
    @ApiOperation(value = "商家地址列表",notes = "商家地址列表",httpMethod = "GET")
    @GetMapping("/order/shop/address/list")
    public CommonResult<List<MallShopAddressVO>> queryShopAddressList(@CurrentUser UserVO userVO){
        return mallShopAddressService.queryShopAddressList(userVO.getShopNo());
    }
}
