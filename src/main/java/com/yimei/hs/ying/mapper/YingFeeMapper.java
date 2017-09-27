package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFee;
import com.yimei.hs.ying.dto.PageYingFeeDTO;

public interface YingFeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingFee record);

    int delete(long id);

    int insertSelective(YingFee record);

    YingFee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingFee record);

    int updateByPrimaryKey(YingFee record);

    Page<YingFee> getPage(PageYingFeeDTO pageYingFeeDTO);
}