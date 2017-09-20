package com.yimei.hs.service.ying;

import com.yimei.hs.entity.YingSettleUpstream;
import com.yimei.hs.mapper.YingSettleUpstreamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingSettleService {
    @Autowired
    YingSettleUpstreamMapper yingSettleUpstream;

    /**
     *  find settles by orderid
     * @param orderId
     * @return
     */
    public List<YingSettleUpstream> getAllUpStreamSettles(Long orderId) {
        return yingSettleUpstream.selectByOrderId(orderId);
    }
}
