package com.yimei.hs.ying.mapper;

import com.yimei.hs.ying.entity.YingOrderConfig;

public interface YingOrderConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingOrderConfig record);

    int insertSelective(YingOrderConfig record);

    YingOrderConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingOrderConfig record);

    int updateByPrimaryKey(YingOrderConfig record);
}