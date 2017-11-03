package com.yimei.hs.same.mapper;

import com.yimei.hs.same.entity.HuikuanMap;

import java.util.List;

public interface HuikuanMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HuikuanMap record);

    /**
     *  删除业务线orderId
     * @param orderId
     * @return
     */
    int deleteByOrderId(long orderId);

    int insertSelective(HuikuanMap record);

    HuikuanMap selectByPrimaryKey(Long id);

    HuikuanMap selectByPrimaryKeyDeleted(Long id);

    int updateByPrimaryKeySelective(HuikuanMap record);

    int updateByPrimaryKey(HuikuanMap record);

    List<HuikuanMap> loadAll(Long orderId);

    List<HuikuanMap> getListByFukuanId(Long fukuanId);

    List<HuikuanMap> getListByHuikuanId(Long huikuanId);
}