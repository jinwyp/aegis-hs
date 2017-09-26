package com.yimei.hs.ying.mapper;

import com.yimei.hs.ying.entity.YingHuankuanMap;

import java.util.List;

public interface YingHuankuanMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingHuankuanMap record);

    int insertSelective(YingHuankuanMap record);

    YingHuankuanMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingHuankuanMap record);

    int updateByPrimaryKey(YingHuankuanMap record);

    List<YingHuankuanMap> loadAll(Long orderId);
}