package com.yimei.hs.ying.mapper;

import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.ying.entity.YingHuikuan;
import com.yimei.hs.ying.entity.YingHuikuanMap;

import java.util.List;

public interface YingHuikuanMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingHuikuanMap record);

    /**
     *  删除业务线orderId
     * @param orderId
     * @return
     */
    int deleteByOrderId(long orderId);

    int insertSelective(YingHuikuanMap record);

    YingHuikuanMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingHuikuanMap record);

    int updateByPrimaryKey(YingHuikuanMap record);

    List<YingHuikuanMap> loadAll(Long orderId);

    List<YingFukuan> getList(Long huikuanid);
}