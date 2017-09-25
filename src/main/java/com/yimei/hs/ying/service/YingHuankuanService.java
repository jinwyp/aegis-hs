package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingHuankuan;
import com.yimei.hs.ying.dto.PageYingHuankuanDTO;
import com.yimei.hs.ying.mapper.YingHuankuanMapper;
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

    public YingHuankuan findOne(long id) {
        return yingHuankuanMapper.selectByPrimaryKey(id);
    }

    public int create(YingHuankuan yingHuankuan) {
        return yingHuankuanMapper.insert(yingHuankuan);
    }
}
