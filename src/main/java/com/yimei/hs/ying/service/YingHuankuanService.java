package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.ying.entity.YingHuankuan;
import com.yimei.hs.ying.dto.PageYingHuankuanDTO;
import com.yimei.hs.ying.entity.YingHuankuanMap;
import com.yimei.hs.ying.mapper.YingFukuanMapper;
import com.yimei.hs.ying.mapper.YingHuankuanMapMapper;
import com.yimei.hs.ying.mapper.YingHuankuanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingHuankuanService {

    private static final Logger logger = LoggerFactory.getLogger(YingHuankuanService.class);

    @Autowired YingFukuanService yingFukuanService;

    @Autowired
    private YingHuankuanMapper yingHuankuanMapper;

    @Autowired
    private YingHuankuanMapMapper yingHuankuanMapMapper;

    @Autowired
    private YingFukuanMapper yingFukuanMapper;

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

        // 1. 插入还款记录
        int rtn = yingHuankuanMapper.insert(yingHuankuan);
        if (rtn != 1) {
            return 0;
        }

        for (YingHuankuanMap map : yingHuankuan.getHuankuanMapList()) {
            map.setHuankuanId(yingHuankuan.getId());
            map.setOrderId(yingHuankuan.getOrderId());
            yingHuankuanMapMapper.insert(map);
        }
        return rtn;
    }

    /**
     * todo 陆彪
     *  重建 还款-付款-映射
     * @param orderId
     * @return
     */
    public int createMap(long orderId) {
        // 1. 删除所有的orderId的 hs_ying_huankuan_map记录
        // 2. 重建所有的orderId的 hs_ying_huankuan_map记录
        return 1;
    }

    /**
     *  更新
     * @param yingHuankuan
     * @return
     */
    public int update(YingHuankuan yingHuankuan) {
        return yingHuankuanMapper.updateByPrimaryKeySelective(yingHuankuan);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    public int delete( long id) {
        // 还款是手动对应的， 删除还款记录，
        // 需要同时删除其map
        yingHuankuanMapMapper.deleteByHuankuanId(id);
        return yingHuankuanMapper.delete(id);
    }
}
