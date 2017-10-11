package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.EntityType;
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
     *
     * @param pageYingOrderDTO
     * @return
     */
    public Page<YingOrder> getPage(PageYingOrderDTO pageYingOrderDTO) {


        Page<YingOrder> yingOrderPages = yingOrderMapper.getPage(pageYingOrderDTO);

        for (YingOrder yingOrder : yingOrderPages.getResults()) {
            yingOrder.setOrderPartyList(yingOrderPartyMapper.getList(yingOrder.getId()));
            yingOrder.setOrderConfigList(yingOrderConfigMapper.getList(yingOrder.getId()));
        }
        return yingOrderPages;
    }

    /**
     * 获取指定订单
     *
     * @param id
     * @return
     */
    public YingOrder findOne(long id) {
        return yingOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    public int create(YingOrder order) {

        // 插入业务线
        int rtn = yingOrderMapper.insert(order);

        if (rtn == 0) {
            return 0;
        }

        List<YingOrderConfig> configList = order.getOrderConfigList();

        List<YingOrderParty> partyList = order.getOrderPartyList();

        int failed = 0;

        // 插入参与方
        if (partyList != null) {

            for (YingOrderParty yingOrderParty : partyList) {
                yingOrderParty.setOrderId(order.getId());
                if (yingOrderPartyMapper.insert(yingOrderParty) != 1) {
                    failed++;
                }
            }
        }

        // 插入核算月配置
        if (configList != null) {
            for (YingOrderConfig yingOrderConfig : configList) {
                yingOrderConfig.setOrderId(order.getId());
                if( yingOrderConfigMapper.insert(yingOrderConfig) !=  1) {
                    failed++;
                }
            }
        }

        if (failed > 0) {
            return 0;
        }

        return rtn;
    }

    /**
     * 更新订单
     *
     * @param record
     * @return
     */
    public int update(YingOrder record) {
        return yingOrderMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * @param orderId
     * @param from
     * @param to
     * @return
     */
    public int updateTransfer(Long orderId, Long from, Long to) {
        int rtn = yingOrderMapper.transfer(orderId, from, to);
        if (rtn == 1) {
            yingLogService.create(new YingLog(null, orderId, null, orderId, EntityType.order, "转移订单", null));
        }
        return rtn;
    }

    /**
     * ownerId是否拥有orderId
     *
     * @param ownerId
     * @param orderId
     * @return
     */
    public boolean hasOrder(long ownerId, long orderId) {
        return yingOrderMapper.hasOrder(ownerId, orderId);
    }

    @Autowired
    private YingLogService yingLogService;

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    public int delete(Long id) {
        return yingOrderMapper.delete(id);
    }
}
