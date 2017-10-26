package com.yimei.hs.ying.service;

import com.yimei.hs.ying.entity.YingAnalysisData;
import com.yimei.hs.ying.entity.YingBail;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class YingDataAnalysisService {

    @Autowired
    YingAnalysisDataMapper yingAnalysisDataMapper;
    public YingAnalysisData findOne(Long morderId, long hsId) {
        return yingAnalysisDataMapper.findOne(morderId,hsId);
    }
}
