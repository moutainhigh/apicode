package com.ycandyz.master.dao.organize;

import com.ycandyz.master.dto.organize.OrganizeDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrganizeDao {
    OrganizeDTO queryName(Long organizeId);
}
