package com.yimei.hs.ying.service;

import com.yimei.hs.ying.mapper.YingTransferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingTransferService {

    private static final Logger logger = LoggerFactory.getLogger(YingTransferService.class);

    @Autowired
    private YingTransferMapper yingTransferMapper;


    @Autowired
    private YingLogService yingLogService;

}
