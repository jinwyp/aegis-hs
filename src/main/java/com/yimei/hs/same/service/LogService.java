package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageLogDTO;
import com.yimei.hs.same.entity.Log;
import com.yimei.hs.same.mapper.LogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hary on 2017/9/21.
 */
@Service
@Transactional(readOnly = true)
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private LogMapper logMapper;

    public void log(Long orderId, Long hsId, String entityType, Long entityId, String memo) {
    }

    public Page<Log> getPage(PageLogDTO pageLogDTO) {
        return logMapper.getPage(pageLogDTO);
    }

    @Transactional(readOnly = false)
    public int create(Log log) {
        return logMapper.insertSelective(log);
    }

    @Transactional(readOnly = false)
    public Log findOne(Long id) {
        return logMapper.selectByPrimaryKey(id);
    }
}
