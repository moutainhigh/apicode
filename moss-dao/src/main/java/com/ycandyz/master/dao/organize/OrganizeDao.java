package com.ycandyz.master.dao.organize;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.organize.OrganizeDTO;
import com.ycandyz.master.entities.organize.Organize;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrganizeDao extends BaseMapper<Organize> {
    OrganizeDTO queryName(Long organizeId);
}
