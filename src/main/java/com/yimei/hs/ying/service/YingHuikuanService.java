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


        // 查询出当前订单的所有与付款的对应记录
        List<YingHuikuanMap> huikuanMap = yingHuikuanMapMapper.loadAll(yingHuikuan.getOrderId());
        // 当前订单的所有付款记录
        List<YingFukuan> fukuanList = yingFukuanMapper.getList(yingHuikuan.getOrderId());

        // 待添加的记录 todo
        List<YingHuikuanMap> toAdd = new ArrayList<>();
        if (huikuanMap == null||huikuanMap.size()==0) {
            BigDecimal templateAmount = yingHuikuan.getHuikuanAmount();
            logger.info("value===> {}\n id==> {}",templateAmount);
            for (YingFukuan fukuan : fukuanList) {
                BigDecimal currentFukuan = fukuan.getAmount();
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
                        logger.info("value===> {}\n id==> {}",templateAmount,fukuan.getId());
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
        } else {

            //
            huikuanMap.get(0).getFukuanId();
        }
        //case 1 fukuan>=huikuan


        //case 2fukuan<huikuan

        for ( YingHuikuanMap item: toAdd) {
              yingHuikuanMapMapper.insert(item);
        }

        return rtn;
    }


    /**
     * 重建 回款-付款-映射
     * @param orderId
     * @return
     */
    public int createMap(long orderId) {
        // todo
        return 1;
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    public int delete(long orderId, long id) {
        yingHuikuanMapMapper.deleteByOrderId(orderId);
        // todo 重建所有的map记录
        return yingHuikuanMapper.delete(id);
    }
}
