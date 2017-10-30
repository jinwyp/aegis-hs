package com.yimei.hs.ying.mapper;

import com.yimei.hs.same.entity.InvoiceAnalysis;
import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.ying.entity.YingAnalysisData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YingAnalysisDataMapper {


    YingAnalysisData findOne(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    List<Jiekuan> findJiekuanByhsId( @Param("orderId") Long orderId, @Param("hsId") Long hsId);

    List<Jiekuan> findJiekuanMathchedHuankuan( @Param("orderId") Long orderId,  @Param("hsId")Long hsId);

    List<InvoiceAnalysis> findByOrderIdOpenCompanyId(@Param("openCompanyId") Long openCompanyId, @Param("orderId") Long orderId);

}