package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFukuan;
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

import java.math.BigDecimal;
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
        // 插入还款记录
        int rtn = yingHuikuanMapper.insert(yingHuikuan);
        if (rtn != 1) {
            return 0;
        }
        // 待添加的记录 todo
        List<YingHuikuanMap> toAdd = new ArrayList<>();// createMapById(yingHuikuan, huikuanList);

        for (YingHuikuanMap item : toAdd) {
            yingHuikuanMapMapper.insert(item);
        }

        return rtn;
    }

    /**
     * 为指定的回款记录， 创建map记录
     * @param yingHuikuan
     * @return
     */
    private List<YingHuikuanMap> createMapById(YingHuikuan yingHuikuan) {

        List<YingHuikuanMap> toAdd = new ArrayList<>();

        //
        List<YingHuikuan> huikuanList = yingHuikuanMapper.loadAll(yingHuikuan.getOrderId());

        // 查询出当前订单的所有与付款的对应记录
        List<YingHuikuanMap> huikuanMap = yingHuikuanMapMapper.loadAll(yingHuikuan.getOrderId());

        // 当前订单的所有付款记录
        List<YingFukuan> fukuanList = yingFukuanMapper.getList(yingHuikuan.getOrderId());


        //从未添加过
        if (huikuanMap == null || huikuanMap.size() == 0) {

            toAdd.addAll(doHelp(yingHuikuan, (ArrayList<YingFukuan>) fukuanList));

        } else {
            //匹配数据中最后一组数据id 是否与 回款  最后一组数据一致
            if (huikuanMap.get(0).getHuikuanId() == huikuanList.get(huikuanList.size() - 1).getId()) {

                //已匹配总额
                BigDecimal mapAmount = new BigDecimal("0");
                for (YingHuikuanMap huimap : huikuanMap) {
                    mapAmount.add(huimap.getAmount());
                }
                //付款总额
                BigDecimal fukuanAmount = new BigDecimal("0");
                for (YingFukuan fukuan : fukuanList) {
                    fukuanAmount.add(fukuan.getPayAmount());
                }

                if (fukuanAmount.subtract(mapAmount).doubleValue()!=0) {
                    //匹配数据中最后一组数据id 是否与 付款最后一组数据一致
                    // fukuanList.get(fukuanList.size() - 1).setHuikuanTotal(fukuanAmount.subtract(mapAmount));
                    //todo fukuanList 移除已经匹配过的数据
                    toAdd.addAll(doHelp(yingHuikuan, (ArrayList<YingFukuan>) fukuanList));
                }

                //
            } else {
                //历史数据需要匹配
                //找关键点
                long lastFukuanMapId = huikuanMap.get(0).getFukuanId();
                long lastHuikuanMapId = huikuanMap.get(0).getHuikuanId();
                BigDecimal mapHuikuanSuplus = new BigDecimal("0");
                BigDecimal mapHuikuan = new BigDecimal("0");
                BigDecimal mapFukuanSuplus = new BigDecimal("0");
                BigDecimal mapFukuan = new BigDecimal("0");
                for (YingHuikuanMap huiMap:huikuanMap) {
                    if (lastFukuanMapId==huiMap.getFukuanId()) {
                        mapFukuan.add(huiMap.getAmount());
                    }
                }

                for (YingHuikuanMap huiMap:huikuanMap) {
                    if (lastHuikuanMapId==huiMap.getHuikuanId()) {
                        mapHuikuan.add(huiMap.getAmount());
                    }
                }
                mapHuikuanSuplus = yingHuikuanMapper.selectByPrimaryKey(lastHuikuanMapId).getHuikuanAmount().subtract(mapHuikuan);
                mapFukuanSuplus = yingFukuanMapper.selectByPrimaryKey(lastFukuanMapId).getPayAmount().subtract(mapFukuan);
//                if (mapHuikuanSuplus <= 0) {
//                    fukuanList.remove(1);
//                    for (YingHuikuan huikuan : huikuanList) {
//                        if (mapFukuanSuplus <= 0) {
//                            //从当前位置的下个开始匹配
//                            doHelp(huikuan, (ArrayList<YingFukuan>) fukuanList);
//                        } else {
//                            //从当前位置开始匹配 huikuanTotal=mapFukuanSuplus
//                            doHelp(huikuan, (ArrayList<YingFukuan>) fukuanList);
//                        }
//                    }
//                } else {
//
//                }
            }


        }

        return toAdd;
    }


    /**
     * 重建 回款-付款-映射
     *
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
     *
     * @param id
     * @return
     */
    public int delete(long orderId, long id) {
        // 由于回款时自动对应到付款的, 删除回款记录, 需要重建整个业务线的 回款-付款-map记录
        yingHuikuanMapMapper.deleteByOrderId(orderId);
        this.createMap(orderId);
        return yingHuikuanMapper.delete(id);
    }


    /**
     * @param yingHuikuan
     * @param fukuanList
     * @return
     */
    public ArrayList<YingHuikuanMap> doHelp(YingHuikuan yingHuikuan, ArrayList<YingFukuan> fukuanList) {
        ArrayList<YingHuikuanMap> toAdd = new ArrayList<YingHuikuanMap>();
        BigDecimal templateAmount = yingHuikuan.getHuikuanAmount();

        for (YingFukuan fukuan : fukuanList) {
            // BigDecimal currentFukuan = fukuan.getHuikuanTotal();
            //付款款额度大于回款额度
            if (fukuan.getPayAmount().subtract(yingHuikuan.getHuikuanAmount()).doubleValue() > 0) {
                toAdd.add(new YingHuikuanMap() {{
                    setOrderId(yingHuikuan.getOrderId());
                    setFukuanId(fukuan.getId());
                    setHuikuanId(yingHuikuan.getId());
                    setAmount(yingHuikuan.getHuikuanAmount());
                }});
                break;
            } else {
                //付款额度小于还款额度

                //剩余额度大于本次付款额度
                if (templateAmount.subtract(fukuan.getPayAmount()).doubleValue() > 0) {
                    logger.info("value===> {}\n id==> {}", templateAmount, fukuan.getId());
                    toAdd.add(new YingHuikuanMap() {{
                        setOrderId(yingHuikuan.getOrderId());
                        setFukuanId(fukuan.getId());
                        setHuikuanId(yingHuikuan.getId());
                        setAmount(fukuan.getPayAmount());
                    }});
                    templateAmount = templateAmount.subtract(fukuan.getPayAmount());

                    //剩余额度小于本次付款数目 但是不等于0
                } else if (templateAmount.subtract(fukuan.getPayAmount()).doubleValue() < 0 && templateAmount.subtract(new BigDecimal("0")).doubleValue() >= 1) {
                    BigDecimal tempinner = templateAmount;

                    toAdd.add(new YingHuikuanMap() {{
                        setOrderId(yingHuikuan.getOrderId());
                        setFukuanId(fukuan.getId());
                        setHuikuanId(yingHuikuan.getId());
                        setAmount(tempinner);

                    }});
                    templateAmount = templateAmount.subtract(templateAmount);
                    break;
                }
            }

        }
        return toAdd;
    }

}



