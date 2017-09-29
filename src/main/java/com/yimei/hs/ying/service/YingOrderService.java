package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.YingEntityType;
import com.yimei.hs.ying.entity.YingLog;
import com.yimei.hs.ying.entity.YingOrder;
import com.yimei.hs.ying.entity.YingOrderConfig;
import com.yimei.hs.ying.entity.YingOrderParty;
import com.yimei.hs.ying.dto.PageYingOrderDTO;
import com.yimei.hs.ying.mapper.YingOrderConfigMapper;
import com.yimei.hs.ying.mapper.YingOrderMapper;
import com.yimei.hs.ying.mapper.YingOrderPartyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingOrderService {

    private static final Logger logger = LoggerFactory.getLogger(YingOrderService.class);

    @Autowired
    private YingOrderMapper yingOrderMapper;

    @Autowired
    private YingOrderPartyMapper yingOrderPartyMapper;

    @Autowired
    private YingOrderConfigMapper yingOrderConfigMapper;

    /**
     * 获取一页订单
     * @param pageYingOrderDTO
     * @return
     */
    public Page<YingOrder> getPage(PageYingOrderDTO pageYingOrderDTO) {
        return yingOrderMapper.getPage(pageYingOrderDTO);
    }

    /**
     * 获取指定订单
     * @param id
     * @return
     */
    public YingOrder findOne(long id) {
        return yingOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建订单
     * @param order
     * @return
     */
    public int create(YingOrder order) {

        // 插入业务线
        int rtn = yingOrderMapper.insert(order);

        List<YingOrderConfig> configList = order.getOrderConfigList();

        List<YingOrderParty> partyList = order.getOrderPartyList();

        // 插入参与方
        if (partyList != null ) {
            partyList.forEach(new Consumer<YingOrderParty>() {
                @Override
                public void accept(YingOrderParty yingOrderParty) {
                    yingOrderParty.setOrderId(order.getId());
                    yingOrderPartyMapper.insert(yingOrderParty);
                }
            });
        }

        // 插入核算月配置
        if (configList != null) {
            configList.forEach(new Consumer<YingOrderConfig>() {
                @Override
                public void accept(YingOrderConfig yingOrderConfig) {
                    yingOrderConfig.setOrderId(order.getId());
                    yingOrderConfigMapper.insert(yingOrderConfig);
                }
            });
        }

        return rtn;
    }

    /**
     *  更新订单
     * @param record
     * @return
     */
    public int update(YingOrder record) {
        return yingOrderMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * @param orderId
     * @param from
     * @param to
     * @return
     */
    public int updateTransfer(Long orderId, Long from, Long to) {
        int rtn = yingOrderMapper.transfer(orderId, from, to);
        if (rtn == 1) {
            yingLogService.create(new YingLog(null, orderId, null, orderId, YingEntityType.order, "转移订单", null));
        }
        return rtn;
    }

    /**
     *  ownerId是否拥有orderId
     * @param ownerId
     * @param orderId
     * @return
     */
    public boolean hasOrder(long ownerId, long orderId){
        return yingOrderMapper.hasOrder(ownerId,orderId) ;
    }

    @Autowired
    private YingLogService yingLogService;

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    public int delete(Long id) {
        return yingOrderMapper.delete(id);
    }
}
