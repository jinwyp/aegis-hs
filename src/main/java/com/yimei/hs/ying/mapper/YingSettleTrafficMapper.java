package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingSettleTraffic;
import com.yimei.hs.ying.dto.PageYingSettleTrafficDTO;

public interface YingSettleTrafficMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingSettleTraffic record);

    int insertSelective(YingSettleTraffic record);

    YingSettleTraffic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingSettleTraffic record);

    int updateByPrimaryKey(YingSettleTraffic record);

    Page<YingSettleTraffic> getPage(PageYingSettleTrafficDTO pageYingSettleTrafficDTO);
}