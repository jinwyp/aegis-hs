package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageHuankuanDTO;
import com.yimei.hs.same.entity.Huankuan;
import com.yimei.hs.same.entity.HuankuanMap;
import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.same.mapper.FukuanMapper;
import com.yimei.hs.same.mapper.HuankuanMapMapper;
import com.yimei.hs.same.mapper.HuankuanMapper;
import com.yimei.hs.same.mapper.JiekuanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class HuankuanService {

    private static final Logger logger = LoggerFactory.getLogger(HuankuanService.class);

    @Autowired
    private HuankuanMapper huankuanMapper;

    @Autowired
    private HuankuanMapMapper huankuanMapMapper;

    @Autowired
    private FukuanMapper yingFukuanMapper;

    @Autowired
    private JiekuanMapper jiekuanMapper;

    public Page<Huankuan> getPage(PageHuankuanDTO pageHuankuanDTO) {
        Page<Huankuan> pagehuankuans = huankuanMapper.getPage(pageHuankuanDTO);

        for (Huankuan huankuan :pagehuankuans.getResults()){
            // 设置还款所对应的借款
            huankuan.setJiekuanList(jiekuanMapper.getJiekuanListByHuankuanId(huankuan.getId()));
            // 设置还款所对应的还款明细
            huankuan.setHuankuanMapList(huankuanMapMapper.getListByHuankuanId(huankuan.getId()));
        }
        return pagehuankuans;
    }

    /**
     * 
     * @param id
     * @return
     */
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

        // 2. 插入还款明细
        for (HuankuanMap map : huankuan.getHuankuanMapList()) {
            map.setHuankuanId(huankuan.getId());
            map.setOrderId(huankuan.getOrderId());
            huankuanMapMapper.insert(map);
        }
        return rtn;
    }

    /**
     *  更新
     * @param hk
     * @return
     */
    @Transactional(readOnly = false)
    public int update(Huankuan hk) {
        
        // 1. 找出还款
        Huankuan huankuan = huankuanMapper.selectByPrimaryKey(hk.getId());

        // 2. 删除还款对应的还款明细
        huankuanMapMapper.deleteByHuankuanId(hk.getId());

        // 3. 插入还款明细
        for (HuankuanMap map : hk.getHuankuanMapList()) {
            map.setHuankuanId(huankuan.getId());
            map.setOrderId(huankuan.getOrderId());
            huankuanMapMapper.insert(map);
        }

        // 4. 更新还款记录(可能是日期)
        return huankuanMapper.updateByPrimaryKeySelective(hk);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete( long id) {

        // 1. 找出还款记录
        Huankuan huankuan = huankuanMapper.selectByPrimaryKey(id);

        // 3. 删除还款对应的还款明细
        huankuanMapMapper.deleteByHuankuanId(id);

        // 删除还款
        return huankuanMapper.delete(id);
    }
}
