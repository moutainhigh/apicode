package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.util.StringUtil;
import com.ycandyz.master.api.PageResult;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.mall.MallAfterSalesDao;
import com.ycandyz.master.dao.mall.MallAfterSalesLogDao;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.dto.mall.MallAfterSalesDTO;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.entities.mall.MallAfterSalesLog;
import com.ycandyz.master.enums.SalesEnum;
import com.ycandyz.master.model.mall.MallAfterSalesVO;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.MallAfterSalesService;
import com.ycandyz.master.utils.IDGeneratorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MallAfterSalesServiceImpl extends BaseService<MallAfterSalesDao, MallAfterSales, MallafterSalesQuery> implements MallAfterSalesService {

    @Autowired
    private MallAfterSalesDao mallAfterSalesDao;

    @Autowired
    private MallAfterSalesLogDao mallAfterSalesLogDao;

    @Override
    public ReturnResponse<Page<MallAfterSalesVO>> querySalesListPage(RequestParams<MallafterSalesQuery> requestParams, UserVO userVO) {
        Page<MallAfterSalesVO> mallPage = new Page<>();
        MallafterSalesQuery mallafterSalesQuery = requestParams.getT();
        if (mallafterSalesQuery==null){
            mallafterSalesQuery = new MallafterSalesQuery();
        }
        mallafterSalesQuery.setShopNo(userVO.getShopNo());
        Page pageQuery = new Page(requestParams.getPage(),requestParams.getPage_size());
        Page<MallAfterSalesDTO> page = mallAfterSalesDao.getTrendMallAfterSalesPage(pageQuery,mallafterSalesQuery);
        if (page.getRecords()!=null && page.getRecords().size()>0){
            List<MallAfterSalesVO> list = new ArrayList<>();
            MallAfterSalesVO mallAfterSalesVO = null;
            for (MallAfterSalesDTO mallAfterSalesDTO : page.getRecords()){
                mallAfterSalesVO = new MallAfterSalesVO();
                BeanUtils.copyProperties(mallAfterSalesDTO,mallAfterSalesVO);
                list.add(mallAfterSalesVO);
            }
            mallPage.setTotal(page.getTotal());
            mallPage.setSize(page.getSize());
            mallPage.setRecords(list);
            mallPage.setCurrent(page.getCurrent());
            mallPage.setPages(page.getPages());
        }
        return ReturnResponse.success(mallPage);
    }

    @Override
    public ReturnResponse<MallAfterSalesVO> querySalesDetail(Long id) {
        MallAfterSalesVO mallAfterSalesVO = null;
        MallAfterSalesDTO mallAfterSalesDTO = mallAfterSalesDao.querySalesDetail(id);
        if (mallAfterSalesDTO!=null){
            mallAfterSalesVO = new MallAfterSalesVO();
            BeanUtils.copyProperties(mallAfterSalesDTO,mallAfterSalesVO);
        }
        return ReturnResponse.success(mallAfterSalesVO);
    }

    @Transactional
    @Override
    public boolean refundAuditFirst(MallafterSalesQuery mallafterSalesQuery, UserVO userVO) {
        boolean flag = false;
        MallAfterSales mallAfterSales = mallAfterSalesDao.selectById(mallafterSalesQuery.getId());
        if (mallAfterSales!=null){
            if (StringUtil.isEmpty(mallAfterSales.getShopNo()) || userVO.getShopNo().equals(mallAfterSales.getShopNo())){  //鉴权用户
                if (mallAfterSales.getStatus()==10 && Objects.equals(mallAfterSales.getSubStatus(),1010)){
                    mallAfterSales.setSubStatus(mallafterSalesQuery.getSubStatus());
                    mallAfterSales.setAuditFirstAt(new Date().getTime());
                    mallAfterSalesDao.updateById(mallAfterSales);
                    if (Objects.equals(mallafterSalesQuery.getSubStatus(),1030)){
                        //审批通过记录日志
                        MallAfterSalesLog mallAfterSalesLog = new MallAfterSalesLog();
                        mallAfterSalesLog.setAfterSalesNo(mallAfterSales.getAfterSalesNo());
                        mallAfterSalesLog.setShopNo(userVO.getShopNo());
                        mallAfterSalesLog.setUserId(mallAfterSales.getUserId());
                        mallAfterSalesLog.setLogNo(IDGeneratorUtils.getLongId()+"");
                        mallAfterSalesLog.setContextShop(SalesEnum.ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_AUDIT_FIRST_PASS.description().replace("ADDRESS",mallafterSalesQuery.getReceiver()+
                                ","+mallafterSalesQuery.getReceiverPhone()+","+mallafterSalesQuery.getReceiverAddress()).replace("MONEY",mallAfterSales.getMoney().toString()));
                        mallAfterSalesLog.setContextBuyer(SalesEnum.ORDER_AFTER_SALES_LOG_THTK_BUYER_ANGLE_AUDIT_FIRST_PASS.description().replace("ADDRESS",mallafterSalesQuery.getReceiver()+
                                ","+mallafterSalesQuery.getReceiverPhone()+","+mallafterSalesQuery.getReceiverAddress()).replace("MONEY",mallAfterSales.getMoney().toString()));
                        mallAfterSalesLogDao.insert(mallAfterSalesLog);
                    }
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public void exportEXT(MallafterSalesQuery mallafterSalesQuery, UserVO userVO, HttpServletResponse response) {
        mallafterSalesQuery.setShopNo(userVO.getShopNo());
        List<MallAfterSalesDTO> list = mallAfterSalesDao.getTrendMallAfterSalesList(mallafterSalesQuery);
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
        writer.addHeaderAlias("receiverName", "收货人");
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
}
