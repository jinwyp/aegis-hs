package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageSettleBuyerDTO;
import com.yimei.hs.same.entity.SettleBuyer;
import com.yimei.hs.same.mapper.SettleBuyerMapper;
import com.yimei.hs.ying.service.YingLogService;
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
public class SettleBuyerService {

    private static final Logger logger = LoggerFactory.getLogger(SettleBuyerService.class);

    @Autowired
    SettleBuyerMapper settleBuyerMapper;

    /**
     * @param pageSettleBuyerDTO
     * @return
     */
    public Page<SettleBuyer> getPage(PageSettleBuyerDTO pageSettleBuyerDTO) {
        return settleBuyerMapper.getPage(pageSettleBuyerDTO);
    }

    /**
     * 应收-下游结算 || 苍押-上游结算
     *
     * @return
     */
    @Transactional(readOnly = false)
    public int create(SettleBuyer settleBuyer) {
        int rtn = settleBuyerMapper.insert(settleBuyer);
        if (rtn != 1) {
            return 0;
        }
        return rtn;
    }

    /**
     * @param id
     * @return
     */
    public SettleBuyer findOne(long id) {
        return settleBuyerMapper.selectByPrimaryKey(id);
    }


    /**
     * @param settleBuyer
     * @return
     */
    @Transactional(readOnly = false)
    public int update(SettleBuyer settleBuyer) {
        return settleBuyerMapper.updateByPrimaryKeySelective(settleBuyer);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long id) {
        return settleBuyerMapper.delete(id);
    }
}
