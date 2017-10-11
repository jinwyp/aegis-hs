package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.dto.PageYingSettleDownstreamDTO;
import com.yimei.hs.ying.dto.PageYingSettleTrafficDTO;
import com.yimei.hs.ying.dto.PageYingSettleUpstreamDTO;
import com.yimei.hs.ying.entity.YingSettleDownstream;
import com.yimei.hs.ying.entity.YingSettleTraffic;
import com.yimei.hs.ying.entity.YingSettleUpstream;
import com.yimei.hs.ying.mapper.YingFayunMapper;
import com.yimei.hs.ying.mapper.YingSettleDownstreamMapper;
import com.yimei.hs.ying.mapper.YingSettleTrafficMapper;
import com.yimei.hs.ying.mapper.YingSettleUpstreamMapper;
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
public class YingSettleUpsteamService {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleUpsteamService.class);


    @Autowired
    YingSettleUpstreamMapper yingSettleUpstreamMapper;


    /**
     * find settles by orderid
     *
     * @param orderId
     * @return
     */
    public List<YingSettleUpstream> getAllUpStreamSettles(Long orderId) {
        return yingSettleUpstreamMapper.selectByOrderId(orderId);
    }

    @Autowired
    private YingLogService yingLogService;



    /**
     * @param pageYingSettleUpstreamDTO
     * @return
     */
    public Page<YingSettleUpstream> getPageUpstream(PageYingSettleUpstreamDTO pageYingSettleUpstreamDTO) {
        return yingSettleUpstreamMapper.getPage(pageYingSettleUpstreamDTO);
    }





    @Transactional(readOnly = false)
    public int createUpstream(YingSettleUpstream yingSettleUpstream) {
        return yingSettleUpstreamMapper.insert(yingSettleUpstream);
    }


    /**
     * @param id
     * @return
     */
    public YingSettleUpstream findUpstream(long id) {
        return yingSettleUpstreamMapper.selectByPrimaryKey(id);
    }

    /**
     * @param yingSettleUpstream
     * @return
     */
    @Transactional(readOnly = false)
    public int updateUpstream(YingSettleUpstream yingSettleUpstream) {
        return yingSettleUpstreamMapper.updateByPrimaryKeySelective(yingSettleUpstream);
    }







    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteUpstream(long id) {
        return yingSettleUpstreamMapper.delete(id);
    }
}
