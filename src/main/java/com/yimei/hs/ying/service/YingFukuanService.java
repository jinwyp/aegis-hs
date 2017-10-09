package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.*;
import com.yimei.hs.ying.dto.PageYingFukuanDTO;
import com.yimei.hs.ying.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingFukuanService {

    private static final Logger logger = LoggerFactory.getLogger(YingFukuanService.class);

    @Autowired
    private YingFukuanMapper yingFukuanMapper;

    @Autowired
    private YingHuikuanMapMapper yingHuikuanMapMapper;

    @Autowired
    private YingHuikuanMapper yingHuikuanMapper;

    @Autowired
    private YingHuankuanMapper yingHuankuanMapper;

    @Autowired
    private YingHuankuanMapMapper yingHuankuanMapMapper;

    @Autowired
    private YingHuikuanService yingHuikuanService;

    @Autowired
    private YingHuankuanService yingHuankuanService;

    @Autowired
    private YingLogService yingLogService;

    /**
     *  获取一页付款记录
     * @param pageYingFukuanDTO
     * @return
     */
    public Page<YingFukuan> getPage(PageYingFukuanDTO pageYingFukuanDTO) {
        Page<YingFukuan> page = yingFukuanMapper.getPage(pageYingFukuanDTO);
        for (YingFukuan yingFukuan : page.getResults()) {

            List<YingHuikuan> huikuanList = yingHuikuanMapper.getListByFukuanID(yingFukuan.getId());
            List<YingHuikuanMap> huikuanMap = yingHuikuanMapMapper.getListByFukuanId(yingFukuan.getId());
            yingFukuan.setHuikuanList(huikuanList);
            yingFukuan.setHuikuanMap(huikuanMap);

            List<YingHuankuan> huankuanList = yingHuankuanMapper.getListByFukuanId(yingFukuan.getId());
            List<YingHuankuanMap> huankuanMap = yingHuankuanMapMapper.getListByFukuanId(yingFukuan.getId());
            yingFukuan.setHuankuanList(huankuanList);
            yingFukuan.setHuankuanMap(huankuanMap);
        }
        return page;
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

    public int update(YingFukuan yingFukuan) {
        return yingFukuanMapper.updateByPrimaryKeySelective(yingFukuan);
    }

    /**
     * 删除指定订单的付款记录， 这时候， 需要重建所有 回款-付款-map,  还款-付款-map
     * @param orderId
     * @param id
     * @return
     */
    public int delete(Long orderId, long id) {
        yingHuikuanService.createMap(orderId);
        yingHuankuanService.createMap(orderId);
        return yingFukuanMapper.delete(id);
    }
}
