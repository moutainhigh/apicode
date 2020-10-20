package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.mall.MallOrderDao;
import com.ycandyz.master.domain.query.mall.MallOrderQuery;
import com.ycandyz.master.dto.mall.MallOrderDTO;
import com.ycandyz.master.dto.mall.MallOrderDetailDTO;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.model.mall.MallOrderDetailVO;
import com.ycandyz.master.model.mall.MallOrderVO;
import com.ycandyz.master.service.mall.MallOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaillOrderServiceImpl extends BaseService<MallOrderDao, MallOrder, MallOrderQuery> implements MallOrderService {

    @Autowired
    private MallOrderDao mallOrderDao;

    @Override
    public CommonResult<Page<MallOrderVO>> queryOrderList(PageModel model, MallOrderQuery mallOrderQuery) {
        Page<MallOrderDTO> page = mallOrderDao.getTrendMallOrderPage(model,mallOrderQuery);
        Page<MallOrderVO> page1 = new Page<>();
        List<MallOrderVO> list = new ArrayList<>();
        MallOrderVO mallOrderVo = null;
        if (list!=null && list.size()>0) {
            for (MallOrderDTO mallOrderDTO : page.getRecords()) {
                mallOrderVo = new MallOrderVO();
                BeanUtils.copyProperties(mallOrderDTO, mallOrderVo);
                list.add(mallOrderVo);
            }
        }
        page1.setPages(page.getPages());
        page1.setCurrent(page.getCurrent());
        page1.setOrders(page.getOrders());
        page1.setRecords(list);
        page1.setSize(page.getSize());
        page1.setTotal(page.getTotal());
        return CommonResult.success(page1);
    }

    public void exportEXT(MallOrderQuery mallOrderQuery, HttpServletResponse response){
        List<MallOrderDTO> list = mallOrderDao.getTrendMallOrder(mallOrderQuery);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //自定义标题别名
        writer.addHeaderAlias("cartOrderSn", "母订单编号");
        writer.addHeaderAlias("orderNo", "订单编号");
        writer.addHeaderAlias("itemName", "商品名称");
        writer.addHeaderAlias("allMoney", "总计金额");
        writer.addHeaderAlias("payType", "支付方式");
        writer.addHeaderAlias("status", "状态");
        writer.addHeaderAlias("afterSalesStatus", "售后");
        writer.addHeaderAlias("payuser", "购买用户");
        writer.addHeaderAlias("shipuser", "收货人");
        writer.addHeaderAlias("receiverAddress", "收货地址");
        writer.addHeaderAlias("orderAt", "下单时间");
        writer.addHeaderAlias("paymentTime", "支付时间");
        writer.addHeaderAlias("receiveAt", "收货时间");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        String name = "Order-list";
        response.setHeader("Content-Disposition","attachment;filename="+name+".xls");
        ServletOutputStream out= null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        // 关闭writer，释放内存ß
            writer.close();
        }
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    @Override
    public CommonResult<MallOrderDetailVO> queryOrderDetail(String orderNo) {
        MallOrderDetailVO mallOrderDetailVO = null;
        MallOrderDetailDTO mallOrderDetailDTO = mallOrderDao.queryOrderDetail(orderNo);
        if (mallOrderDetailDTO!=null){
            mallOrderDetailVO = new MallOrderDetailVO();
            BeanUtils.copyProperties(mallOrderDetailDTO,mallOrderDetailVO);
        }
        return CommonResult.success(mallOrderDetailVO);
    }

    @Override
    public boolean shipment(String orderNo) {
        return false;
    }
}
