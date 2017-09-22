package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingFukuan;

public interface YingFukuanMapper {
    int deleteByPrimaryKey(Long id);

    long insert(YingFukuan record);

    int insertSelective(YingFukuan record);

    YingFukuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingFukuan record);

    int updateByPrimaryKey(YingFukuan record);
}