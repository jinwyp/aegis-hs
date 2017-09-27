package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.dto.PageYingLogDTO;
import com.yimei.hs.ying.entity.YingLog;
import com.yimei.hs.ying.mapper.YingLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/21.
 */
@Service
public class YingLogService {
    private static final Logger logger = LoggerFactory.getLogger(YingLogService.class);

    @Autowired
    private YingLogMapper yingLogMapper;

    public void log(Long orderId, Long hsId, String entityType, Long entityId, String memo) {
    }

    public Page<YingLog> getPage(PageYingLogDTO pageYingLogDTO) {
        return yingLogMapper.getPage(pageYingLogDTO);
    }

    public int create(YingLog yingLog) {
        return yingLogMapper.insert(yingLog);
    }

    public YingLog findOne(Long id) {
        return yingLogMapper.selectByPrimaryKey(id);
    }
}
