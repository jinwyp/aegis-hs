package com.yimei.hs.service.ying;

import com.yimei.hs.entity.YingSettleUpstream;
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

    @Autowired
    YingSettleDownstreamMapMapper yingSettleDownstreamMapMapper;

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
}
