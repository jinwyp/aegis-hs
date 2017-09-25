package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangFukuan;

public interface CangFukuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangFukuan record);

    int insertSelective(CangFukuan record);

    CangFukuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangFukuan record);

    int updateByPrimaryKey(CangFukuan record);
}