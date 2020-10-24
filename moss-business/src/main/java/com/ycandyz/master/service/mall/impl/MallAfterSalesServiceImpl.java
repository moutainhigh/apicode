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
import com.ycandyz.master.dao.mall.*;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.dto.mall.*;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.entities.mall.MallAfterSalesLog;
import com.ycandyz.master.entities.mall.MallOrder;
import com.ycandyz.master.enums.SalesEnum;
import com.ycandyz.master.model.mall.*;
import com.ycandyz.master.model.user.UserVO;
import com.ycandyz.master.service.mall.MallAfterSalesService;
import com.ycandyz.master.utils.IDGeneratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class MallAfterSalesServiceImpl extends BaseService<MallAfterSalesDao, MallAfterSales, MallafterSalesQuery> implements MallAfterSalesService {

    @Autowired
    private MallAfterSalesDao mallAfterSalesDao;

    @Autowired
    private MallAfterSalesLogDao mallAfterSalesLogDao;

    @Autowired
    private MallShopShippingDao mallShopShippingDao;

    @Autowired
    private MallShopShippingLogDao mallShopShippingLogDao;

    @Autowired
    private MallOrderDetailSpecDao mallItemSpecByMallOrderDetailSpecVO;

    @Autowired
    private MallShopDao mallShopDao;

    @Override
    public ReturnResponse<Page<MallAfterSalesVO>> querySalesListPage(RequestParams<MallafterSalesQuery> requestParams, UserVO userVO) {
        Page<MallAfterSalesVO> mallPage = new Page<>();
        MallafterSalesQuery mallafterSalesQuery = requestParams.getT();
        if (mallafterSalesQuery==null){
            mallafterSalesQuery = new MallafterSalesQuery();
        }
        mallafterSalesQuery.setShopNo(userVO.getShopNo());
        Page pageQuery = new Page(requestParams.getPage(), requestParams.getPage_size());
        List<MallAfterSalesVO> list = new ArrayList<>();
        Page<MallAfterSalesDTO> page = null;
        try {
            page = mallAfterSalesDao.getTrendMallAfterSalesPage(pageQuery, mallafterSalesQuery);
            if (page.getRecords() != null && page.getRecords().size() > 0) {
                MallAfterSalesVO mallAfterSalesVO = null;
                for (MallAfterSalesDTO mallAfterSalesDTO : page.getRecords()) {
                    Integer subStatus = mallAfterSalesDTO.getSubStatus();
                    Integer state = null;
                    if (subStatus!=null){
                        if (subStatus==1010){
                            state = 1;
                        }else if (subStatus==1030){
                            state = 2;
                        }else if (subStatus==1050 || subStatus==2010){
                            state = 3;
                        }else if (subStatus==1080 || subStatus==2020 || subStatus==2030){
                            state = 4;
                        }else if (subStatus==1020 || subStatus==1040 || subStatus==1060 || subStatus==2050){
                            state = 6;
                        }else {
                            state = 5;
                        }
                    }
                    mallAfterSalesDTO.setState(state);
                    mallAfterSalesVO = new MallAfterSalesVO();
                    BeanUtils.copyProperties(mallAfterSalesDTO, mallAfterSalesVO);
                    MallOrderByAfterSalesDTO mallOrderByAfterSalesDTO = mallAfterSalesDTO.getOrder();
                    if (mallOrderByAfterSalesDTO!=null){
                        //关联订单
                        MallOrderByAfterSalesVO mallOrderByAfterSalesVO = new MallOrderByAfterSalesVO();
                        BeanUtils.copyProperties(mallOrderByAfterSalesDTO, mallOrderByAfterSalesVO);
                        mallAfterSalesVO.setOrder(mallOrderByAfterSalesVO);
                    }
                    MallOrderDetailByAfterSalesDTO mallOrderDetailByAfterSalesDTO = mallAfterSalesDTO.getDetails();
                    if (mallOrderDetailByAfterSalesDTO!=null){
                        //关联订单详情
                        MallOrderDetailByAfterSalesVO mallOrderDetailByAfterSalesVO = new MallOrderDetailByAfterSalesVO();
                        BeanUtils.copyProperties(mallOrderDetailByAfterSalesDTO,mallOrderDetailByAfterSalesVO);
                        mallAfterSalesVO.setDetails(mallOrderDetailByAfterSalesVO);
                    }
                    list.add(mallAfterSalesVO);
                }
            }
        }catch (Exception e){
            page = new Page<>(0,10,0);
        }
        mallPage.setTotal(page.getTotal());
        mallPage.setSize(page.getSize());
        mallPage.setRecords(list);
        mallPage.setCurrent(page.getCurrent());
        mallPage.setPages(page.getPages());
        return ReturnResponse.success(mallPage);
    }

    @Override
    public ReturnResponse<MallAfterSalesVO> querySalesDetail(String afterSalesNo, UserVO userVO) {
        MallAfterSalesVO mallAfterSalesVO = null;
        MallAfterSalesDTO mallAfterSalesDTO = mallAfterSalesDao.querySalesDetail(afterSalesNo);
        if (mallAfterSalesDTO!=null){
            mallAfterSalesVO = new MallAfterSalesVO();
            BeanUtils.copyProperties(mallAfterSalesDTO,mallAfterSalesVO);

            MallOrderByAfterSalesDTO mallOrderByAfterSalesDTO = mallAfterSalesDTO.getOrder();
            if (mallOrderByAfterSalesDTO!=null){
                //关联订单
                MallOrderByAfterSalesVO mallOrderByAfterSalesVO = new MallOrderByAfterSalesVO();
                BeanUtils.copyProperties(mallOrderByAfterSalesDTO, mallOrderByAfterSalesVO);
                mallAfterSalesVO.setOrder(mallOrderByAfterSalesVO);
            }
            MallOrderDetailByAfterSalesDTO mallOrderDetailByAfterSalesDTO = mallAfterSalesDTO.getDetails();
            if (mallOrderDetailByAfterSalesDTO!=null){
                //关联订单详情
                MallOrderDetailByAfterSalesVO mallOrderDetailByAfterSalesVO = new MallOrderDetailByAfterSalesVO();
                BeanUtils.copyProperties(mallOrderDetailByAfterSalesDTO,mallOrderDetailByAfterSalesVO);
                mallAfterSalesVO.setDetails(mallOrderDetailByAfterSalesVO);

                //MallOrderDetail中的item信息拼接
                MallItemByMallOrderDetailVO mallItemByMallOrderDetailVO = new MallItemByMallOrderDetailVO();
                mallItemByMallOrderDetailVO.setItemNo(mallOrderDetailByAfterSalesVO.getItemNo());
                mallItemByMallOrderDetailVO.setItemName(mallOrderDetailByAfterSalesVO.getItemName());
                mallItemByMallOrderDetailVO.setGoodsNo(mallOrderDetailByAfterSalesVO.getGoodsNo());
                mallItemByMallOrderDetailVO.setItemCover(mallOrderDetailByAfterSalesVO.getItemCover());
                mallItemByMallOrderDetailVO.setPrice(mallOrderDetailByAfterSalesVO.getPrice());
                mallItemByMallOrderDetailVO.setQuantity(mallOrderDetailByAfterSalesVO.getQuantity());

                //MallOrderDetailSpec中的specValue商品规格拼接
                List<MallOrderDetailSpecDTO> mallOrderDetailSpecDTOs = mallItemSpecByMallOrderDetailSpecVO.queryListByOrderDetailNo(mallOrderDetailByAfterSalesVO.getOrderDetailNo());
                if (mallOrderDetailSpecDTOs!=null && mallOrderDetailSpecDTOs.size()>0){
                    List<MallOrderDetailSpecVO> mallOrderDetailSpecVOS = new ArrayList<>();
                    List<MallItemSpecByMallOrderDetailSpecVO> mallItemSpecByMallOrderDetailSpecVOS = new ArrayList<>();
                    mallOrderDetailSpecDTOs.forEach(dto->{
                        //拼接数据到item中
                        MallItemSpecByMallOrderDetailSpecVO mallItemSpecByMallOrderDetailSpecVO = new MallItemSpecByMallOrderDetailSpecVO();
                        mallItemSpecByMallOrderDetailSpecVO.setSpecValue(dto.getSpecValue());
                        mallItemSpecByMallOrderDetailSpecVOS.add(mallItemSpecByMallOrderDetailSpecVO);

                        //拼接数据到details中
                        MallOrderDetailSpecVO mallOrderDetailSpecVO = new MallOrderDetailSpecVO();
                        BeanUtils.copyProperties(dto,mallOrderDetailSpecVO);
                        mallOrderDetailSpecVOS.add(mallOrderDetailSpecVO);
                    });
                    mallItemByMallOrderDetailVO.setSpec(mallItemSpecByMallOrderDetailSpecVOS);
                    mallAfterSalesVO.getDetails().setSpecs(mallOrderDetailSpecVOS);
                }
                mallAfterSalesVO.setItemInfo(mallItemByMallOrderDetailVO);
            }

            //MallAfterSalesLog表
            List<MallAfterSalesLogDTO> mallAfterSalesLogDTOs = mallAfterSalesLogDao.querySalesLogByShopNoAndSalesNo(mallAfterSalesDTO.getAfterSalesNo(),userVO.getShopNo());
            if (mallAfterSalesLogDTOs!=null && mallAfterSalesLogDTOs.size()>0){
                List<MallAfterSalesLogVO> mallAfterSalesLogVOS = new ArrayList<>();
                mallAfterSalesLogDTOs.forEach(dto->{
                    MallAfterSalesLogVO mallAfterSalesLogVO = new MallAfterSalesLogVO();
                    BeanUtils.copyProperties(dto,mallAfterSalesLogVO);
                    mallAfterSalesLogVOS.add(mallAfterSalesLogVO);
                });
                mallAfterSalesVO.setAsLog(mallAfterSalesLogVOS);
            }

            //MallShopShpping表
            MallShopShippingDTO mallShopShippingDTO = mallShopShippingDao.queryByOrderNo(mallAfterSalesDTO.getOrderNo());
            if (mallShopShippingDTO!=null){
                MallShopShippingVO mallShopShippingVO = new MallShopShippingVO();
                BeanUtils.copyProperties(mallShopShippingDTO,mallShopShippingVO);
                mallAfterSalesVO.setShippingInfo(mallShopShippingVO);

                //MallShopShippingLog表
                List<MallShopShippingLogDTO> mallShopShippingLogDTOS = mallShopShippingLogDao.selectListByShopShippingNo(mallShopShippingVO.getShopShippingNo());
                if (mallShopShippingLogDTOS!=null && mallShopShippingLogDTOS.size()>0){
                    List<MallShopShippingLogVO> mallShopShippingLogVOS = new ArrayList<>();
                    mallShopShippingLogDTOS.forEach(dto->{
                        MallShopShippingLogVO mallShopShippingLogVO = new MallShopShippingLogVO();
                        BeanUtils.copyProperties(dto,mallShopShippingLogVO);
                        mallShopShippingLogVOS.add(mallShopShippingLogVO);
                    });
                    mallAfterSalesVO.setShippinglog(mallShopShippingLogVOS);
                }
            }

            //MallShop信息拼接
            MallShopDTO mallShopDTO = mallShopDao.queryByShopNo(userVO.getShopNo());
            if (mallShopDTO!=null){
                MallShopVO mallShopVO = new MallShopVO();
                BeanUtils.copyProperties(mallShopDTO,mallShopVO);
                mallAfterSalesVO.getOrder().setShop(mallShopVO);
            }

            //转换退款原因reason为reasonStr（文字描述）
            if (mallAfterSalesVO.getReason()!=null){
                if (mallAfterSalesVO.getReason()==10402010){
                    mallAfterSalesVO.setReasonStr("多拍/拍错/不想要");
                }else if (mallAfterSalesVO.getReason()==10402020){
                    mallAfterSalesVO.setReasonStr("不喜欢/效果不好");
                }else if (mallAfterSalesVO.getReason()==10402030){
                    mallAfterSalesVO.setReasonStr("做工/质量问题");
                }else if (mallAfterSalesVO.getReason()==10402040){
                    mallAfterSalesVO.setReasonStr("商家发错货");
                }else if (mallAfterSalesVO.getReason()==10402050){
                    mallAfterSalesVO.setReasonStr("未按约定时间发货");
                }else if (mallAfterSalesVO.getReason()==10402060){
                    mallAfterSalesVO.setReasonStr("与商品描述严重不符");
                }else if (mallAfterSalesVO.getReason()==10402070){
                    mallAfterSalesVO.setReasonStr("收到商品少件/破损/污渍");
                }else if (mallAfterSalesVO.getReason()==10402099){
                    mallAfterSalesVO.setReasonStr("其他 仅退款原因 未收到货");
                }else if (mallAfterSalesVO.getReason()==20401010){
                    mallAfterSalesVO.setReasonStr("空包裹");
                }else if (mallAfterSalesVO.getReason()==20401020){
                    mallAfterSalesVO.setReasonStr("快递/物流一直未到");
                }else if (mallAfterSalesVO.getReason()==20401030){
                    mallAfterSalesVO.setReasonStr("快递/物流无跟踪记录");
                }else if (mallAfterSalesVO.getReason()==20401040){
                    mallAfterSalesVO.setReasonStr("货物破损/变质 仅退款原因 已收到货");
                }else if (mallAfterSalesVO.getReason()==20402010){
                    mallAfterSalesVO.setReasonStr("不喜欢/效果不好");
                }else if (mallAfterSalesVO.getReason()==20402020){
                    mallAfterSalesVO.setReasonStr("做工/质量问题");
                }else if (mallAfterSalesVO.getReason()==20402030){
                    mallAfterSalesVO.setReasonStr("商家发错货未按约定时间发货");
                }else if (mallAfterSalesVO.getReason()==20402040){
                    mallAfterSalesVO.setReasonStr("与商品描述严重不符");
                }else if (mallAfterSalesVO.getReason()==20402050){
                    mallAfterSalesVO.setReasonStr("收到商品少件/破损/污渍");
                }else if (mallAfterSalesVO.getReason()==20402099){
                    mallAfterSalesVO.setReasonStr("其他");
                }
            }

            //转换photos，jsonarray为List数组
            if (StringUtils.isNotEmpty(mallAfterSalesVO.getPhotos())){
                String demosub = mallAfterSalesVO.getPhotos().substring(1,mallAfterSalesVO.getPhotos().length()-1).replaceAll("\"","");
                List<String> photosArray = Arrays.asList(demosub.split(","));
                mallAfterSalesVO.setPhotosArray(photosArray);
            }
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
