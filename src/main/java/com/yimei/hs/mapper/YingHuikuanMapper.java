package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingHuikuan;

public interface YingHuikuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingHuikuan record);

    int insertSelective(YingHuikuan record);

    YingHuikuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingHuikuan record);

    int updateByPrimaryKey(YingHuikuan record);
}