package com.yimei.hs.ying.service;

import com.yimei.hs.same.entity.Fukuan;
import com.yimei.hs.same.entity.HuankuanMap;
import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.same.mapper.HuankuanMapMapper;
import com.yimei.hs.ying.entity.YingAnalysisData;
import com.yimei.hs.ying.entity.YingBail;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

@Service
@Transactional(readOnly = true)
public class YingDataAnalysisService {

    @Autowired
    YingAnalysisDataMapper yingAnalysisDataMapper;

    @Autowired
    HuankuanMapMapper huankuanMapMapper;


    BigDecimal matchedUnloadEstimateCost = new BigDecimal("0");

    public YingAnalysisData findOne(Long morderId, long hsId) {
        List<Jiekuan> unMathchjiekuans = removeMatchList(morderId, hsId);
        BigDecimal unLoadEstimateCosts = new BigDecimal("0");
        for (Jiekuan jiekuan : unMathchjiekuans) {
            unLoadEstimateCosts= unLoadEstimateCosts.add(jiekuan.getLoadEstimateCost());
        }
        unLoadEstimateCosts=  unLoadEstimateCosts.add(matchedUnloadEstimateCost);
        YingAnalysisData yingAnalysisData = yingAnalysisDataMapper.findOne(morderId, hsId);
        if (yingAnalysisData != null) {
            yingAnalysisData.setTotalUnrepaymentEstimateCost(unLoadEstimateCosts);
        }
        return yingAnalysisData;
    }

    /**
     * 获取核算月所有借款
     *
     * @param orderId
     * @param hsId
     * @return
     */
    public List<Jiekuan> findJiekuanByhsId(Long orderId, Long hsId) {

        return yingAnalysisDataMapper.findJiekuanByhsId(orderId, hsId);
    }

    public List<Jiekuan> removeMatchList(Long morderId, long hsId) {
        List<Jiekuan> unMathchjiekuans = new ArrayList<Jiekuan>();
        List<Jiekuan> allJiekuans = findJiekuanByhsId(morderId, hsId);
        List<Jiekuan> mathchedjiekuans = new ArrayList<Jiekuan>();

        if (allJiekuans == null || allJiekuans.size() == 0) {
            return null;
        }
        for (Jiekuan jiekuan : allJiekuans) {
            List<HuankuanMap> huankuanMaps = huankuanMapMapper.getListByJiekuanId(jiekuan.getId());
            BigDecimal totalhuankuan = huankuanMaps.stream().map(m -> m.getPrincipal()).reduce(BigDecimal.ZERO, BigDecimal::add);
            jiekuan.setHuankuanTotal(totalhuankuan);
            //还款总额大于1
            if (totalhuankuan.compareTo(BigDecimal.ZERO) == 1) {
                mathchedjiekuans.add(jiekuan);
                matchedUnloadEstimateCost = jiekuan.getAmount().subtract(jiekuan.getHuankuanTotal()).multiply(jiekuan.getUseInterest()).multiply(new BigDecimal(jiekuan.getUseDays())).divide(new BigDecimal(360),2,ROUND_HALF_DOWN);;
            } else {
                unMathchjiekuans.add(jiekuan);
            }
        }

        return unMathchjiekuans;
    }
}
