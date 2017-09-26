package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingHuankuan;
import com.yimei.hs.ying.dto.PageYingHuankuanDTO;
import com.yimei.hs.ying.entity.YingHuankuanMap;
import com.yimei.hs.ying.mapper.YingHuankuanMapMapper;
import com.yimei.hs.ying.mapper.YingHuankuanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingHuankuanService {

    private static final Logger logger = LoggerFactory.getLogger(YingHuankuanService.class);


    @Autowired
    private YingHuankuanMapper yingHuankuanMapper;

    @Autowired
    private YingHuankuanMapMapper yingHuankuanMapMapper;

    @Autowired
    private YingLogService yingLogService;

    public Page<YingHuankuan> getPage(PageYingHuankuanDTO pageYingHuankuanDTO) {
        return yingHuankuanMapper.getPage(pageYingHuankuanDTO);
    }

    public YingHuankuan findOne(long id) {
        return yingHuankuanMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建还款时， 需要将还款与付款对应关系设置好
     * @param yingHuankuan
     * @return
     */
    public int create(YingHuankuan yingHuankuan) {

        // 插入还款记录
        int rtn = yingHuankuanMapper.insert(yingHuankuan);
        if (rtn != 1) {
            return 0;
        }

        List<YingHuankuanMap> huankuanMap = yingHuankuanMapMapper.loadAll(yingHuankuan.getOrderId());

        return rtn;
    }

    public int update(YingHuankuan yingHuankuan) {
        return yingHuankuanMapper.updateByPrimaryKeySelective(yingHuankuan);
    }
}
