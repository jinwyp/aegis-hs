package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingHuikuanMap;

public interface YingHuikuanMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingHuikuanMap record);

    int insertSelective(YingHuikuanMap record);

    YingHuikuanMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingHuikuanMap record);

    int updateByPrimaryKey(YingHuikuanMap record);
}