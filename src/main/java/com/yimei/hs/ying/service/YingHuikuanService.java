package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.ying.entity.YingHuankuanMap;
import com.yimei.hs.ying.entity.YingHuikuan;
import com.yimei.hs.ying.dto.PageYingHuikuanDTO;
import com.yimei.hs.ying.entity.YingHuikuanMap;
import com.yimei.hs.ying.mapper.YingFukuanMapper;
import com.yimei.hs.ying.mapper.YingHuikuanMapMapper;
import com.yimei.hs.ying.mapper.YingHuikuanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingHuikuanService {

    private static final Logger logger = LoggerFactory.getLogger(YingHuikuanService.class);


    @Autowired
    private YingHuikuanMapMapper yingHuikuanMapMapper;

    @Autowired
    private YingFukuanMapper yingFukuanMapper;

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

    public int create(YingHuikuan yingHuikuan) {
        // 插入还款记录
        int rtn = yingHuikuanMapper.insert(yingHuikuan);
        if (rtn != 1) {
            return 0;
        }
//
//
//        // 查询出当前订单的所有与付款的对应记录
//        List<YingHuikuanMap> huankuanMap = yingHuikuanMapMapper.loadAll(yingHuikuan.getOrderId());
//
//        // 当前订单的所有付款记录
//        List<YingFukuan> hukuanList = yingFukuanMapper.getList(yingHuikuan.getOrderId());
//
//        // 待添加的记录 todo
//        List<YingHuikuanMap> toAdd = new ArrayList<>();
//
//        for ( YingHuikuanMap item: toAdd) {
//            yingHuikuanMapMapper.insert(item);
//        }

        return rtn;
    }


    /**
     * 重建 回款-付款-映射
     * @param orderId
     * @return
     */
    public int createMap(long orderId) {
        // todo 陆彪
        // 1. 删除orderId的所有 hs_ying_huikuan_map记录
        // 2. 重建orderId的所有 hs_ying_huikuan_map记录
        return 1;
    }

    /**
     * todo 陆彪 check
     * 逻辑删除
     * @param id
     * @return
     */
    public int delete(long orderId, long id) {
        // 由于回款时自动对应到付款的, 删除回款记录, 需要重建整个业务线的 回款-付款-map记录
        yingHuikuanMapMapper.deleteByOrderId(orderId);
        this.createMap(orderId);
        return yingHuikuanMapper.delete(id);
    }
}
