package com.yimei.hs.ying.mapper;

import com.yimei.hs.ying.entity.YingAnalysisData;
import org.apache.ibatis.annotations.Param;

public interface YingAnalysisDataMapper {
    int insert(YingAnalysisData record);

    int insertSelective(YingAnalysisData record);

    YingAnalysisData findOne(@Param("orderId") Long orderId, @Param("hsId")long hsId);
}