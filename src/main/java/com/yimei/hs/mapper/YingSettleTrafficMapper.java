package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingSettleDownstream;
import com.yimei.hs.entity.YingSettleTraffic;
import com.yimei.hs.entity.YingSettleUpstream;
import com.yimei.hs.entity.dto.ying.PageYingSettleDownstreamDTO;
import com.yimei.hs.entity.dto.ying.PageYingSettleTrafficDTO;
import com.yimei.hs.entity.dto.ying.PageYingSettleUpstreamDTO;

public interface YingSettleTrafficMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingSettleTraffic record);

    int insertSelective(YingSettleTraffic record);

    YingSettleTraffic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingSettleTraffic record);

    int updateByPrimaryKey(YingSettleTraffic record);

    Page<YingSettleTraffic> getPage(PageYingSettleTrafficDTO pageYingSettleTrafficDTO);
}