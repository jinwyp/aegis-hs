package com.yimei.hs.ying.mapper;

import com.yimei.hs.same.entity.CapitalPressure;
import com.yimei.hs.same.entity.InvoiceAnalysis;
import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.ying.entity.AnalysisData;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface YingAnalysisDataMapper {


    AnalysisData findOne(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    List<Jiekuan> findJiekuanByhsId( @Param("orderId") Long orderId, @Param("hsId") Long hsId);

    List<Jiekuan> findJiekuanMathchedHuankuan( @Param("orderId") Long orderId,  @Param("hsId")Long hsId);

    List<InvoiceAnalysis> findByOrderIdOpenCompanyId(@Param("openCompanyId") Long openCompanyId, @Param("orderId") Long orderId);

    List<AnalysisData> findList(Long orderId);

    List<AnalysisData> findYingPartsOneList(Long morderId);

    List<AnalysisData> findYingPartsTwoList(Long morderId);
    OrderConfig findOneVBase(@Param("orderId") Long orderId, @Param("hsId")long hsId);
    AnalysisData findOneV1001(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1004(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1006(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1007(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1009(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1012(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1013(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1014(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1015(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1016(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1017(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1018(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1019(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1020(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1021(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1023(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1024(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1027(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1035(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1037(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1039(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV2001(@Param("orderId") Long orderId, @Param("hsId")long hsId);


    AnalysisData findOneV2004(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV2008(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV3001(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV3003(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV3005(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1040ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1040cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1044cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1045(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1046ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1046cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1047(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1048ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1048cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1049ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1049cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1050ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1050cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1051ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1051cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1052ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1052cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1054ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1054cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1055ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1056ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1056cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1057cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1058ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1058cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1059ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1059cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1060cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1060ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1061cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1061ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1063cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1063ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1064cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1064ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1066ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV1066cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV2010cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneV2010ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);


    AnalysisData findOneV3006(@Param("orderId") Long orderId, @Param("hsId")long hsId);

    AnalysisData findOneShowDos1ying(@Param("orderId") Long orderId, @Param("hsId")long hsId);
    AnalysisData findOneShowDos1cang(@Param("orderId") Long orderId, @Param("hsId")long hsId);
    AnalysisData findOneShowDos2(@Param("orderId") Long orderId, @Param("hsId")long hsId);
    AnalysisData findOneShowDos3(@Param("orderId") Long orderId);


    BigDecimal findHuikuanExceptBail(@Param("orderId") Long orderId, @Param("hsId") long hsId);





    CapitalPressure findOneV1075(@Param("id") long id,@Param("orderId") Long orderId, @Param("hsId") long hsId);

    List<Long> selectPartyId(@Param("orderId") Long orderId, @Param("hsId") long hsId);

    CapitalPressure findOneV1076(@Param("id") long id,@Param("orderId") Long orderId, @Param("hsId") long hsId);

    AnalysisData findOneV3010(@Param("orderId") Long orderId, @Param("hsId") long hsId);

    AnalysisData findOneV3013(@Param("orderId") Long orderId, @Param("hsId") long hsId);

    BigDecimal findOneV1075Fee(@Param("id") long id,@Param("orderId") Long orderId, @Param("hsId") long hsId);

    BigDecimal findOneV1075Fukuan(@Param("id") long id,@Param("orderId") Long orderId, @Param("hsId") long hsId);

    BigDecimal findOneV1076Uninvoice(@Param("id") long id,@Param("orderId") Long orderId, @Param("hsId") long hsId);
}