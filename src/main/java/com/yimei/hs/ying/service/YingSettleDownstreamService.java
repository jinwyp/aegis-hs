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
public class YingSettleDownstreamService {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleDownstreamService.class);




    @Autowired
    YingSettleDownstreamMapper yingSettleDownstreamMapper;





    @Autowired
    private YingLogService yingLogService;

    /**
     * @param pageYingSettleDownstreamDTO
     * @return
     */
    public Page<YingSettleDownstream> getPageDownstream(PageYingSettleDownstreamDTO pageYingSettleDownstreamDTO) {
        return yingSettleDownstreamMapper.getPage(pageYingSettleDownstreamDTO);
    }


    /**
     * 下游结算
     *
     * @param yingSettleDownstream
     * @return
     */
    @Transactional(readOnly = false)
    public int createDownstream(YingSettleDownstream yingSettleDownstream) {
        int rtn = yingSettleDownstreamMapper.insert(yingSettleDownstream);
        if (rtn != 1) {
            return 0;
        }

        return rtn;
    }


    /**
     * @param id
     * @return
     */
    public YingSettleDownstream findDownstream(long id) {
        return yingSettleDownstreamMapper.selectByPrimaryKey(id);
    }


    /**
     * @param yingSettleDownstream
     * @return
     */
    @Transactional(readOnly = false)
    public int updateDownstream(YingSettleDownstream yingSettleDownstream) {
        return yingSettleDownstreamMapper.updateByPrimaryKeySelective(yingSettleDownstream);
    }

    /**
     * 重建所有对应关系
     * @param orderId
     * @return
     */

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteDownstream(long id) {
        return yingSettleDownstreamMapper.delete(id);
    }

}
