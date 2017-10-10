package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageOrderDTO;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.same.entity.OrderParty;
import com.yimei.hs.same.mapper.OrderConfigMapper;
import com.yimei.hs.same.mapper.OrderMapper;
import com.yimei.hs.same.mapper.OrderPartyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderPartyMapper orderPartyMapper;

    @Autowired
    private OrderConfigMapper orderConfigMapper;

    /**
     * 获取一页订单
     *
     * @param pageOrderDTO
     * @return
     */
    public Page<Order> getPage(PageOrderDTO pageOrderDTO) {


        Page<Order> orderPage = orderMapper.getPage(pageOrderDTO);

        for (Order order : orderPage.getResults()) {
            order.setOrderPartyList(orderPartyMapper.getList(order.getId()));
            order.setOrderConfigList(orderConfigMapper.getList(order.getId()));
        }
        return orderPage;
    }

    /**
     * 获取指定订单
     *
     * @param id
     * @return
     */
    public Order findOne(long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    public int create(Order order) {

        // 插入业务线
        int rtn = orderMapper.insert(order);

        if (rtn == 0) {
            return 0;
        }

        List<OrderConfig> configList = order.getOrderConfigList();

        List<OrderParty> partyList = order.getOrderPartyList();

        int failed = 0;

        // 插入参与方
        if (partyList != null) {

            for (OrderParty orderParty : partyList) {
                orderParty.setOrderId(order.getId());
                if (orderPartyMapper.insert(orderParty) != 1) {
                    failed++;
                }
            }
        }

        // 插入核算月配置
        if (configList != null) {
            for (OrderConfig orderConfig : configList) {
                orderConfig.setOrderId(order.getId());
                if (orderConfigMapper.insert(orderConfig) != 1) {
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
    public int update(Order record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * @param orderId
     * @param from
     * @param to
     * @return
     */
    public int updateTransfer(Long orderId, Long from, Long to) {
        int rtn = orderMapper.transfer(orderId, from, to);
        if (rtn == 1) {
            // yingLogService.create(new YingLog(null, orderId, null, orderId, YingEntityType.order, "转移订单", null));
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
        return orderMapper.hasOrder(ownerId, orderId);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    public int delete(Long id) {
        return orderMapper.delete(id);
    }
}
