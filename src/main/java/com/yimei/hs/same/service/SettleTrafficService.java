package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageSettleTrafficDTO;
import com.yimei.hs.same.entity.SettleTraffic;
import com.yimei.hs.same.mapper.SettleTrafficMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class SettleTrafficService {

    private static final Logger logger = LoggerFactory.getLogger(SettleTrafficService.class);


    @Autowired
    SettleTrafficMapper settleTrafficMapper;

    /**
     * @param pageSettleTrafficDTO
     * @return
     */
    public Page<SettleTraffic> getPageTraffic(PageSettleTrafficDTO pageSettleTrafficDTO) {
        return settleTrafficMapper.getPage(pageSettleTrafficDTO);
    }

    public SettleTraffic findTraffic(long id) {
        return settleTrafficMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public int create(SettleTraffic yingSettleTraffic) {
        return settleTrafficMapper.insert(yingSettleTraffic);
    }

    /**
     * @param yingSettleTraffic
     * @return
     */
    @Transactional(readOnly = false)
    public int udpateTraffic(SettleTraffic yingSettleTraffic) {
        return settleTrafficMapper.updateByPrimaryKeySelective(yingSettleTraffic);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long id) {
        return settleTrafficMapper.delete(id);
    }

}
