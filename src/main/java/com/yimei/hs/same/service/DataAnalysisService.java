package com.yimei.hs.same.service;

import com.yimei.hs.cang.dto.CangAnalysisData;
import com.yimei.hs.cang.mapper.CangAnalysisDataMapper;
import com.yimei.hs.ying.entity.YingAnalysisData;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class DataAnalysisService {

    @Autowired
    YingAnalysisDataMapper yingAnalysisDataMapper;

    @Autowired
    CangAnalysisDataMapper cangAnalysisDataMapper;



    public YingAnalysisData findOneYing(Long morderId, long hsId) {
        YingAnalysisData yingAnalysisData = yingAnalysisDataMapper.findOne(morderId, hsId);
        return yingAnalysisData;
    }
    public List<YingAnalysisData> findYingList(Long morderId) {
        List<YingAnalysisData> yingAnalysisDataList = yingAnalysisDataMapper.findList(morderId);
        return yingAnalysisDataList;
    }


    public CangAnalysisData findOneCang(Long hsId, Long orderId) {

        CangAnalysisData cangAnalysisData = cangAnalysisDataMapper.findOne(hsId,orderId);

        return cangAnalysisData;
    }

    public List<CangAnalysisData> findCangList(Long orderId) {

        List<CangAnalysisData> cangAnalysisDataList = cangAnalysisDataMapper.findList(orderId);

        return cangAnalysisDataList;
    }
}
