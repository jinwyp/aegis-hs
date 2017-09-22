package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingHuankuanMap;

public interface YingHuankuanMapMapper {
    int deleteByPrimaryKey(Long id);

    long insert(YingHuankuanMap record);

    int insertSelective(YingHuankuanMap record);

    YingHuankuanMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingHuankuanMap record);

    int updateByPrimaryKey(YingHuankuanMap record);
}