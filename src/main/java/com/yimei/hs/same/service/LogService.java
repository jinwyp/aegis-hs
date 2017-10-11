package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageLogDTO;
import com.yimei.hs.same.entity.Log;
import com.yimei.hs.same.mapper.LogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/21.
 */
@Service
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private LogMapper yingLogMapper;

    public void log(Long orderId, Long hsId, String entityType, Long entityId, String memo) {
    }

    public Page<Log> getPage(PageLogDTO pageLogDTO) {
        return yingLogMapper.getPage(pageLogDTO);
    }

    public int create(Log yingLog) {
        return yingLogMapper.insert(yingLog);
    }

    public Log findOne(Long id) {
        return yingLogMapper.selectByPrimaryKey(id);
    }
}
