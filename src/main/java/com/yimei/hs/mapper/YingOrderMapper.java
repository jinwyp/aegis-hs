package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingOrder;

import java.util.List;

public interface YingOrderMapper {
    int deleteByPrimaryKey(Long id);

    long insert(YingOrder record);

    int insertSelective(YingOrder record);

    YingOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingOrder record);

    int updateByPrimaryKey(YingOrder record);

    Page<YingOrder> selectAllOrder();
}