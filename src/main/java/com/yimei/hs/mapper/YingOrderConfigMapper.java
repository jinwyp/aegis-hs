package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingOrderConfig;

public interface YingOrderConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingOrderConfig record);

    int insertSelective(YingOrderConfig record);

    YingOrderConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingOrderConfig record);

    int updateByPrimaryKey(YingOrderConfig record);
}