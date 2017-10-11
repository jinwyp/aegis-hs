package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageSettleTrafficDTO;
import com.yimei.hs.same.entity.SettleTraffic;

public interface SettleTrafficMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SettleTraffic record);

    int insertSelective(SettleTraffic record);

    SettleTraffic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SettleTraffic record);

    int updateByPrimaryKey(SettleTraffic record);

    Page<SettleTraffic> getPage(PageSettleTrafficDTO pageSettleTrafficDTO);

    int delete(long id);
}