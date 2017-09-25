package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingHuikuan;
import com.yimei.hs.ying.dto.PageYingHuikuanDTO;
import com.yimei.hs.ying.mapper.YingHuikuanMapMapper;
import com.yimei.hs.ying.mapper.YingHuikuanMapper;
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

    public int update(YingHuikuan yingHuikuan) {
        return yingHuikuanMapper.updateByPrimaryKeySelective(yingHuikuan);
    }

}
