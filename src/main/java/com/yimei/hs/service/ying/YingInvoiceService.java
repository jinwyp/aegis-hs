package com.yimei.hs.service.ying;

import com.yimei.hs.mapper.YingInvoiceDetailMapper;
import com.yimei.hs.mapper.YingInvoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingInvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleService.class);


    @Autowired
    private YingInvoiceMapper yingInvoiceMapper;

    @Autowired
    private YingInvoiceDetailMapper yingInvoiceDetailMapper;

    @Autowired
    private YingLogService yingLogService;
}
