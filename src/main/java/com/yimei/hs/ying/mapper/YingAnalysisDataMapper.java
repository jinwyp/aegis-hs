package com.yimei.hs.ying.mapper;

import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.ying.entity.YingAnalysisData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YingAnalysisDataMapper {
    int insert(YingAnalysisData record);

    int insertSelective(YingAnalysisData record);

    YingAnalysisData findOne(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    List<Jiekuan> findJiekuanByhsId( @Param("orderId") Long orderId, @Param("hsId") Long hsId);

    List<Jiekuan> findJiekuanMathchedHuankuan( @Param("orderId") Long orderId,  @Param("hsId")Long hsId);
}