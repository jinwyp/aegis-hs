package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.ying.entity.YingHuankuan;
import com.yimei.hs.ying.entity.YingHuikuan;
import com.yimei.hs.ying.dto.PageYingHuikuanDTO;
import com.yimei.hs.ying.entity.YingHuikuanMap;
import com.yimei.hs.ying.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingHuikuanService {

    private static final Logger logger = LoggerFactory.getLogger(YingHuikuanService.class);

    @Autowired
    YingFukuanService yingFukuanService;

    @Autowired
    private YingHuikuanMapMapper yingHuikuanMapMapper;

    @Autowired
    private YingFukuanMapper yingFukuanMapper;

    @Autowired
    private YingHuikuanMapper yingHuikuanMapper;

    @Autowired
    private YingLogService yingLogService;

    public Page<YingHuikuan> getPage(PageYingHuikuanDTO pageYingHuikuanDTO) {

        Page<YingHuikuan> yingHuikuanPage=yingHuikuanMapper.getPage(pageYingHuikuanDTO);

        for (YingHuikuan yingHuikuan:yingHuikuanPage.getResults()) {
            yingHuikuan.setFukuanList(yingHuikuanMapMapper.getList(yingHuikuan.getId()));
        }
        return yingHuikuanPage;
    }

    public YingHuikuan findOne(long id) {
        return yingHuikuanMapper.selectByPrimaryKey(id);
    }

    public int update(YingHuikuan yingHuikuan) {
        return yingHuikuanMapper.updateByPrimaryKeySelective(yingHuikuan);
    }

    public int create(YingHuikuan yingHuikuan) {

        // 1. 插入回款记录
        int rtn = yingHuikuanMapper.insert(yingHuikuan);

        this.createMapping(yingHuikuan.getOrderId());

        return rtn;
    }

    /**
     * todo 陆彪 check
     * 逻辑删除
     *
     * @param id
     * @return
     */
    public int delete(long orderId, long id) {
        // 由于回款时自动对应到付款的, 删除回款记录, 需要重建整个业务线的 回款-付款-map记录
        yingHuikuanMapMapper.deleteByOrderId(orderId);
        this.createMapping(orderId);
        return yingHuikuanMapper.delete(id);
    }

    public void createMapping(Long orderId) {
        // 2. 找出付款尚未完成回款对应的付款记录

        List<YingFukuan> unfinishedFukuan = yingFukuanService.huikuanUnfinished(orderId);
        if (unfinishedFukuan.size() == 0) {
            return;
        }

        Iterator<YingFukuan> it = unfinishedFukuan.iterator();


        List<YingHuikuanMap> toAdd = new ArrayList<>();

        // 1.  找出订单的还款记录 -  尚有未对应完的余额
        List<YingHuikuan> unfinished = yingHuikuanMapper.getUnfinshedByOrderId(orderId);

        for (YingHuikuan huikuan : unfinished) {

            // 尚未对应完的余额
            BigDecimal total = huikuan.getHuikuanAmount().subtract(huikuan.getFukuanTotal());

            while(it.hasNext()) {

                YingFukuan cur = it.next();

                YingHuikuanMap record = new YingHuikuanMap();
                record.setOrderId(huikuan.getOrderId());
                record.setHuikuanId(huikuan.getId());
                record.setFukuanId(cur.getId());

                BigDecimal toFinished = cur.getPayAmount().subtract(cur.getHuikuanTotal());

                if(total.compareTo(toFinished) != -1) {
                    total = total.subtract(toFinished);
                    record.setAmount(toFinished);
                    toAdd.add(record);
                } else {
                    record.setAmount(total);
                    toAdd.add(record);
                    break;
                }
            }
        }
    }
}



