package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingSettleDownstream;
import com.yimei.hs.ying.entity.YingSettleTraffic;
import com.yimei.hs.ying.entity.YingSettleUpstream;
import com.yimei.hs.ying.dto.PageYingSettleDownstreamDTO;
import com.yimei.hs.ying.dto.PageYingSettleTrafficDTO;
import com.yimei.hs.ying.dto.PageYingSettleUpstreamDTO;
import com.yimei.hs.ying.mapper.YingSettleDownstreamMapper;
import com.yimei.hs.ying.mapper.YingSettleTrafficMapper;
import com.yimei.hs.ying.mapper.YingSettleUpstreamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingSettleService {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleService.class);


    @Autowired
    YingSettleUpstreamMapper yingSettleUpstreamMapper;

    @Autowired
    YingSettleDownstreamMapper yingSettleDownstreamMapper;

    @Autowired
    YingSettleTrafficMapper yingSettleTrafficMapper;

    /**
     *  find settles by orderid
     * @param orderId
     * @return
     */
    public List<YingSettleUpstream> getAllUpStreamSettles(Long orderId) {
        return yingSettleUpstreamMapper.selectByOrderId(orderId);
    }

    @Autowired
    private YingLogService yingLogService;

    /**
     *
     * @param pageYingSettleTrafficDTO
     * @return
     */
    public Page<YingSettleTraffic> getPageTraffic(PageYingSettleTrafficDTO pageYingSettleTrafficDTO) {
        return yingSettleTrafficMapper.getPage(pageYingSettleTrafficDTO);
    }

    /**
     *
     * @param pageYingSettleUpstreamDTO
     * @return
     */
    public Page<YingSettleUpstream> getPageUpstream(PageYingSettleUpstreamDTO pageYingSettleUpstreamDTO) {
        return yingSettleUpstreamMapper.getPage(pageYingSettleUpstreamDTO);
    }

    /**
     *
     * @param pageYingSettleDownstreamDTO
     * @return
     */
    public Page<YingSettleDownstream> getPageDownstream(PageYingSettleDownstreamDTO pageYingSettleDownstreamDTO) {
        return yingSettleDownstreamMapper.getPage(pageYingSettleDownstreamDTO);
    }

    public YingSettleTraffic findTraffic(long id) {
        return yingSettleTrafficMapper.selectByPrimaryKey(id);
    }

    public int createTraffic(YingSettleTraffic yingSettleTraffic) {
        return yingSettleTrafficMapper.insert(yingSettleTraffic);
    }

    public int createUpstream(YingSettleUpstream yingSettleUpstream) {
        return yingSettleUpstreamMapper.insert(yingSettleUpstream);
    }

    public int createDownstream(YingSettleDownstream yingSettleDownstream) {
        return yingSettleDownstreamMapper.insert(yingSettleDownstream);
    }

    public YingSettleDownstream findDownstream(long id) {
        return yingSettleDownstreamMapper.selectByPrimaryKey(id);
    }

    public YingSettleUpstream findUpstream(long id) {
        return yingSettleUpstreamMapper.selectByPrimaryKey(id);
    }
}
