package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.ying.dto.PageYingFukuanDTO;
import com.yimei.hs.ying.mapper.YingFukuanMapper;
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

    /**
     *  获取一页付款记录
     * @param pageYingFukuanDTO
     * @return
     */
    public Page<YingFukuan> getPage(PageYingFukuanDTO pageYingFukuanDTO) {
        return yingFukuanMapper.getPage(pageYingFukuanDTO);
    }

    /**
     *
     * @param yingFukuan
     * @return
     */
    public int create(YingFukuan yingFukuan) {
        logger.info("createfukuan {}",yingFukuan);
        return yingFukuanMapper.insert(yingFukuan);
    }

    public YingFukuan findOne(long id) {
        return yingFukuanMapper.selectByPrimaryKey(id);
    }

    public int update(YingFukuan yingFukuan) {
        return yingFukuanMapper.updateByPrimaryKeySelective(yingFukuan);
    }
}
