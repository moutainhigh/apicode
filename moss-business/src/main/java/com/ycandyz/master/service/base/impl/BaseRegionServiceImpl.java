package com.ycandyz.master.service.base.impl;

import com.ycandyz.master.dao.base.BaseRegionDao;
import com.ycandyz.master.entities.BaseRegion;
import com.ycandyz.master.service.base.BaseRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class BaseRegionServiceImpl implements BaseRegionService {

    @Resource
    private BaseRegionDao baseRegionDao;

    @Override
    public List<BaseRegion> selBaseRegion() {
        return baseRegionDao.selBaseRegion();
    }
}
