package com.yimei.hs.service.ying;

import com.yimei.hs.mapper.YingLogMapper;
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
}
