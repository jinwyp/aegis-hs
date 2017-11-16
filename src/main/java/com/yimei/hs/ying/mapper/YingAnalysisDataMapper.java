package com.yimei.hs.ying.mapper;

import com.yimei.hs.same.entity.InvoiceAnalysis;
import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.ying.entity.YingAnalysisData;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface YingAnalysisDataMapper {


    YingAnalysisData findOne(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    List<Jiekuan> findJiekuanByhsId( @Param("orderId") Long orderId, @Param("hsId") Long hsId);

    List<Jiekuan> findJiekuanMathchedHuankuan( @Param("orderId") Long orderId,  @Param("hsId")Long hsId);

    List<InvoiceAnalysis> findByOrderIdOpenCompanyId(@Param("openCompanyId") Long openCompanyId, @Param("orderId") Long orderId);

    List<YingAnalysisData> findList(Long orderId);

    List<YingAnalysisData> findYingPartsOneList(Long morderId);

    List<YingAnalysisData> findYingPartsTwoList(Long morderId);

    YingAnalysisData findOneV1001(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1004(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1006(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1007(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1009(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1012(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1013(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1014(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1015(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1016(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1017(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1018(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1019(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1020(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1021(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1023(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1024(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1027(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1035(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1037(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1039(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV2001(@Param("orderId") Long orderId, @Param("hsId")long hsId);


    YingAnalysisData findOneV2004(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV2008(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV3001(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV3003(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV3005(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1040ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1040cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1044cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1045(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1046ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1046cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1047(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1048ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1048cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1049ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1049cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1050ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1050cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1051ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1051cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1052ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1052cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1054ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1054cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1055ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1056ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1056cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1057cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1058ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1058cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1059ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1059cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1060cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1060ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1061cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1061ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1063cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1063ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1064cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1064ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1066ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV1066cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV2010cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    YingAnalysisData findOneV2010ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);


    YingAnalysisData findOneV3006(Long morderId, Long hsId);


}