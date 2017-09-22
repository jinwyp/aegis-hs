package com.yimei.hs.service.ying;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingHuankuan;
import com.yimei.hs.entity.dto.ying.PageYingHuankuanDTO;
import com.yimei.hs.mapper.YingHuankuanMapper;
import com.yimei.hs.mapper.YingHuikuanMapMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingHuankuanService {

    private static final Logger logger = LoggerFactory.getLogger(YingHuankuanService.class);


    @Autowired
    private YingHuankuanMapper yingHuankuanMapper;

    @Autowired
    private YingLogService yingLogService;

    public Page<YingHuankuan> getPage(PageYingHuankuanDTO pageYingHuankuanDTO) {
        return yingHuankuanMapper.getPage(pageYingHuankuanDTO);
    }
}
