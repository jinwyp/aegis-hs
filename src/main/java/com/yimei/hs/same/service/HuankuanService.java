package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageHuankuanDTO;
import com.yimei.hs.same.entity.Huankuan;
import com.yimei.hs.same.entity.HuankuanMap;
import com.yimei.hs.same.mapper.FukuanMapper;
import com.yimei.hs.same.mapper.HuankuanMapMapper;
import com.yimei.hs.same.mapper.HuankuanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class HuankuanService {

    private static final Logger logger = LoggerFactory.getLogger(HuankuanService.class);

    @Autowired FukuanService yingFukuanService;

    @Autowired
    private HuankuanMapper huankuanMapper;

    @Autowired
    private HuankuanMapMapper huankuanMapMapper;

    @Autowired
    private FukuanMapper yingFukuanMapper;

    @Autowired
    private LogService yingLogService;

    public Page<Huankuan> getPage(PageHuankuanDTO pageHuankuanDTO) {
        return huankuanMapper.getPage(pageHuankuanDTO);
    }

    public Huankuan findOne(long id) {
        return huankuanMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建还款时， 需要将还款与借款款对应关系设置好
     * @param huankuan
     * @return
     */
    @Transactional(readOnly = false)
    public int create(Huankuan huankuan) {

        // 1. 插入还款记录
        int rtn = huankuanMapper.insert(huankuan);
        if (rtn != 1) {
            return 0;
        }

        for (HuankuanMap map : huankuan.getHuankuanMapList()) {
            map.setHuankuanId(huankuan.getId());
            map.setOrderId(huankuan.getOrderId());
            huankuanMapMapper.insert(map);
        }
        return rtn;
    }

    /**
     * todo 陆彪
     *  重建 还款-付款-映射
     * @param orderId
     * @return
     */
    @Transactional(readOnly = false)
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
    @Transactional(readOnly = false)
    public int update(Huankuan yingHuankuan) {
        return huankuanMapper.updateByPrimaryKeySelective(yingHuankuan);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete( long id) {
        // 还款是手动对应的， 删除还款记录，
        // 需要同时删除其map
        huankuanMapMapper.deleteByHuankuanId(id);
        return huankuanMapper.delete(id);
    }
}
