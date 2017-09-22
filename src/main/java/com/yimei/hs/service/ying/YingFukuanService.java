package com.yimei.hs.service.ying;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingFukuan;
import com.yimei.hs.entity.dto.ying.PageYingFukuanDTO;
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
        return yingFukuanMapper.insert(yingFukuan);
    }

    public YingFukuan findOne(long id) {
        return yingFukuanMapper.selectByPrimaryKey(id);
    }
}
