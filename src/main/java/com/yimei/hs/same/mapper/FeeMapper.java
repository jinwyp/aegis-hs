package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageFeeDTO;
import com.yimei.hs.same.entity.Fee;

public interface FeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Fee record);

    int delete(long id);

    int insertSelective(Fee record);

    Fee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Fee record);

    int updateByPrimaryKey(Fee record);

    Page<Fee> getPage(PageFeeDTO pageFeeDTO);
}