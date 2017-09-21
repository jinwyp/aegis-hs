package com.yimei.hs.service.ying;

import com.yimei.hs.mapper.YingFukuanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingFukuanService {

    private static final Logger logger = LoggerFactory.getLogger(YingFukuanService.class);

    @Autowired
    private YingFukuanMapper yingFukuanMapper;

    @Autowired
    private YingLogService yingLogService;
}
