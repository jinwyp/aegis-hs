package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingHuankuan;

public interface YingHuankuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingHuankuan record);

    int insertSelective(YingHuankuan record);

    YingHuankuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingHuankuan record);

    int updateByPrimaryKey(YingHuankuan record);
}