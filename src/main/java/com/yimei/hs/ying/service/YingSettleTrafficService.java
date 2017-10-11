package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.*;
import com.yimei.hs.ying.dto.PageYingSettleDownstreamDTO;
import com.yimei.hs.ying.dto.PageYingSettleTrafficDTO;
import com.yimei.hs.ying.dto.PageYingSettleUpstreamDTO;
import com.yimei.hs.ying.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class YingSettleTrafficService {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleTrafficService.class);



    @Autowired
    YingSettleTrafficMapper yingSettleTrafficMapper;


    @Autowired
    private YingLogService yingLogService;

    /**
     * @param pageYingSettleTrafficDTO
     * @return
     */
    public Page<YingSettleTraffic> getPageTraffic(PageYingSettleTrafficDTO pageYingSettleTrafficDTO) {
        return yingSettleTrafficMapper.getPage(pageYingSettleTrafficDTO);
    }


    public YingSettleTraffic findTraffic(long id) {
        return yingSettleTrafficMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public int createTraffic(YingSettleTraffic yingSettleTraffic) {
        return yingSettleTrafficMapper.insert(yingSettleTraffic);
    }


    /**
     * @param yingSettleTraffic
     * @return
     */
    @Transactional(readOnly = false)
    public int udpateTraffic(YingSettleTraffic yingSettleTraffic) {
        return yingSettleTrafficMapper.updateByPrimaryKeySelective(yingSettleTraffic);
    }


    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteTraffic(long id) {
        return yingSettleTrafficMapper.delete(id);
    }

}
