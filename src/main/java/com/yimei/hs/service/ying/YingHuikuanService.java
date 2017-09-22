package com.yimei.hs.service.ying;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingHuikuan;
import com.yimei.hs.entity.dto.ying.PageYingHuikuanDTO;
import com.yimei.hs.mapper.YingHuikuanMapMapper;
import com.yimei.hs.mapper.YingHuikuanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingHuikuanService {

    private static final Logger logger = LoggerFactory.getLogger(YingHuikuanService.class);


    @Autowired
    private YingHuikuanMapMapper yingHuikuanMapMapper;

    @Autowired
    private YingHuikuanMapper yingHuikuanMapper;

    @Autowired
    private YingLogService yingLogService;

    public Page<YingHuikuan> getPage(PageYingHuikuanDTO pageYingHuikuanDTO) {
        return yingHuikuanMapper.getPage(pageYingHuikuanDTO);
    }

    public YingHuikuan findOne(long id) {
        return yingHuikuanMapper.selectByPrimaryKey(id);
    }
}
