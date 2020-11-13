package com.ycandyz.master.dao.organize;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.dto.organize.OrganizeDTO;
import com.ycandyz.master.entities.organize.Organize;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrganizeDao extends BaseMapper<Organize> {
    OrganizeDTO queryName(Long organizeId);

    /**
     * 判断企业到期时间等于传入时间
     * @param serviceEndDayBegin    传入的开始时间
     * @param serviceEndDayEnd  传入的结束时间
     * @return
     */
    List<OrganizeDTO> queryByServiceTime(@Param("Long serviceEndDayBegin") Long serviceEndDayBegin, @Param("Long serviceEndDayEnd") Long serviceEndDayEnd);
}
