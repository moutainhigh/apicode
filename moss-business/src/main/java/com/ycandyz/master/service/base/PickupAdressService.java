package com.ycandyz.master.service.base;


import com.ycandyz.master.entities.PickupAddress;

import java.util.List;

/**
 * @Description: 省市
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/

public interface PickupAdressService {

    List<PickupAddress>  selPickupAddress(List<Integer> ids);
}
