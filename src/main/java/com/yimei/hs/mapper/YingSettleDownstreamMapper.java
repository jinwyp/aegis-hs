package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingSettleDownstream;
import com.yimei.hs.entity.YingSettleUpstream;
import com.yimei.hs.entity.dto.ying.PageYingSettleDownstreamDTO;
import com.yimei.hs.entity.dto.ying.PageYingSettleUpstreamDTO;

public interface YingSettleDownstreamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingSettleDownstream record);

    int insertSelective(YingSettleDownstream record);

    YingSettleDownstream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingSettleDownstream record);

    int updateByPrimaryKey(YingSettleDownstream record);


    Page<YingSettleDownstream> getPage(PageYingSettleDownstreamDTO pageYingSettleDownstreamDTO);


}