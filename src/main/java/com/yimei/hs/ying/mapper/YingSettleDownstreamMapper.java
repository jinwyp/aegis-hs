package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingSettleDownstream;
import com.yimei.hs.ying.dto.PageYingSettleDownstreamDTO;

public interface YingSettleDownstreamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingSettleDownstream record);

    int insertSelective(YingSettleDownstream record);

    YingSettleDownstream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingSettleDownstream record);

    int updateByPrimaryKey(YingSettleDownstream record);


    Page<YingSettleDownstream> getPage(PageYingSettleDownstreamDTO pageYingSettleDownstreamDTO);


}