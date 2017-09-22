package com.yimei.hs.service.ying;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingSettleDownstream;
import com.yimei.hs.entity.YingSettleTraffic;
import com.yimei.hs.entity.YingSettleUpstream;
import com.yimei.hs.entity.dto.ying.PageYingSettleDownstreamDTO;
import com.yimei.hs.entity.dto.ying.PageYingSettleTrafficDTO;
import com.yimei.hs.entity.dto.ying.PageYingSettleUpstreamDTO;
import com.yimei.hs.mapper.YingSettleDownstreamMapMapper;
import com.yimei.hs.mapper.YingSettleDownstreamMapper;
import com.yimei.hs.mapper.YingSettleTrafficMapper;
import com.yimei.hs.mapper.YingSettleUpstreamMapper;
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
}
