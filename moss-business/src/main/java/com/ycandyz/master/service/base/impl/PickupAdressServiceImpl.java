package com.ycandyz.master.service.base.impl;

import com.ycandyz.master.dao.base.PickupAdressDao;
import com.ycandyz.master.entities.PickupAddress;
import com.ycandyz.master.service.base.PickupAdressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@Service
public class PickupAdressServiceImpl implements PickupAdressService {

    @Resource
    private PickupAdressDao pickupAdressDao;

    @Override
    public List<PickupAddress>  selPickupAddress(List<Integer> ids) {
        log.info("查询自提地址入参:{}",ids);
        if (ids == null){
            return null;
        }
        List<PickupAddress> pickupAddress = pickupAdressDao.selPickupAddress(ids);
        return pickupAddress;
    }
}
