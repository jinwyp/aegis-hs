package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingFayun;

public interface YingFayunMapper {
    int deleteByPrimaryKey(Long id);

    long insert(YingFayun record);

    int insertSelective(YingFayun record);

    YingFayun selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingFayun record);

    int updateByPrimaryKey(YingFayun record);
}