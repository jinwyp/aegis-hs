package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.dto.CangAnalysisData;
import org.apache.ibatis.annotations.Param;

public interface CangAnalysisDataMapper {


    CangAnalysisData findOne(@Param("hsId") Long hsId, @Param("orderId") Long orderId);
}