package com.ycandyz.master.service.mall.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.controller.base.BaseService;
import com.ycandyz.master.dao.mall.MallAfterSalesDao;
import com.ycandyz.master.domain.query.mall.MallafterSalesQuery;
import com.ycandyz.master.dto.mall.MallAfterSalesDTO;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.model.mall.MallAfterSalesVO;
import com.ycandyz.master.service.mall.MallAfterSalesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MallAfterSalesServiceImpl extends BaseService<MallAfterSalesDao, MallAfterSales, MallafterSalesQuery> implements MallAfterSalesService {

    @Autowired
    private MallAfterSalesDao mallAfterSalesDao;

    @Override
    public CommonResult<Page<MallAfterSalesVO>> querySalesList(PageModel model, MallafterSalesQuery mallafterSalesQuery) {
        Page<MallAfterSalesVO> mallPage = new Page<>();
        Page<MallAfterSalesDTO> page = mallAfterSalesDao.getTrendMallAfterSalesPage(model,mallafterSalesQuery);
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
        return CommonResult.success(mallPage);
    }

    @Override
    public CommonResult<MallAfterSalesVO> querySalesDetail(Long id) {
        MallAfterSalesVO mallAfterSalesVO = null;
        MallAfterSalesDTO mallAfterSalesDTO = mallAfterSalesDao.querySalesDetail(id);
        if (mallAfterSalesDTO!=null){
            mallAfterSalesVO = new MallAfterSalesVO();
            BeanUtils.copyProperties(mallAfterSalesDTO,mallAfterSalesVO);
        }
        return CommonResult.success(mallAfterSalesVO);
    }

    @Transactional
    @Override
    public boolean refundAuditFirst(Long id, Integer subStatus) {
        boolean flag = false;
        MallAfterSales mallAfterSales = mallAfterSalesDao.selectById(id);
        if (mallAfterSales!=null){
            if (mallAfterSales.getStatus()==10 && Objects.equals(mallAfterSales.getSubStatus(),1010)){
                mallAfterSales.setSubStatus(subStatus);
                mallAfterSales.setAuditFirstAt(new Date().getTime());
                mallAfterSalesDao.updateById(mallAfterSales);
                flag = true;
            }
        }
        return flag;
    }
}
