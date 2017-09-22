package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangFukuan;

public interface CangFukuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangFukuan record);

    int insertSelective(CangFukuan record);

    CangFukuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangFukuan record);

    int updateByPrimaryKey(CangFukuan record);
}