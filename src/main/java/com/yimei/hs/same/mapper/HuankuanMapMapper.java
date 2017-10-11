package com.yimei.hs.same.mapper;

import com.yimei.hs.same.entity.HuankuanMap;

import java.util.List;

public interface HuankuanMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HuankuanMap record);

    int deleteByOrderId(long orderId);

    int insertSelective(HuankuanMap record);

    HuankuanMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HuankuanMap record);

    int updateByPrimaryKey(HuankuanMap record);

    List<HuankuanMap> loadAll(Long orderId);

    int deleteByHuankuanId(long huankuanId);

    List<HuankuanMap> getListByFukuanId(Long fukuanId);
}