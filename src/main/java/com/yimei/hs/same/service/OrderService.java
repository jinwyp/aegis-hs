package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.EntityType;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.same.dto.PageOrderDTO;
import com.yimei.hs.same.entity.Log;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.same.entity.OrderParty;
import com.yimei.hs.same.mapper.OrderConfigMapper;
import com.yimei.hs.same.mapper.OrderMapper;
import com.yimei.hs.same.mapper.OrderPartyMapper;
import com.yimei.hs.user.mapper.PartyMapper;
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

public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderPartyMapper orderPartyMapper;

    @Autowired
    private OrderConfigMapper orderConfigMapper;

    @Autowired
    private PartyMapper partyMapper;
    @Autowired
    LogService logService;

    /**
     * 获取一页订单
     *
     * @param pageOrderDTO
     * @return
     */
    public Page<Order> getPage(PageOrderDTO pageOrderDTO) {
        Page<Order> orderPage = orderMapper.getPage(pageOrderDTO);
        if (orderPage != null) {
            for (Order order : orderPage.getResults()) {
                order.setOrderPartyList(orderPartyMapper.getList(order.getId()));
                order.setOrderConfigList(orderConfigMapper.getList(order.getId()));
            }
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
        Order order = orderMapper.selectByPrimaryKey(id);
        if (order != null) {
            for (OrderParty orderParty : order.getOrderPartyList()) {
                orderParty.setParty(partyMapper.selectByPrimaryKey(id));
            }
        }
        return order;
    }

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @Transactional(readOnly = false)
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
    @Transactional(readOnly = false)
    public int update(Order record, OrderStatus status) {

        if (status.equals(OrderStatus.UNCOMPLETED)) {

            List<OrderParty> partyList = record.getOrderPartyList();

            int failed = 0;

            // 删除原有参与方
            Order orderOld = orderMapper.selectByPrimaryKey(record.getId());
            if (orderOld != null) {
                for (OrderParty orderPartyOld : orderOld.getOrderPartyList()) {
                    orderPartyMapper.delete(orderPartyOld.getId());
                }
            }


            // 插入参与方
            if (partyList != null) {

                for (OrderParty orderParty : partyList) {
                    orderParty.setOrderId(record.getId());
                    if (orderParty.getId() == null) {
                        if (orderPartyMapper.insert(orderParty) != 1) {
                            failed++;
                        }
                    } else {
                        if (orderPartyMapper.updateByPrimaryKeySelective(orderParty) != 1) {
                            failed++;
                        }
                    }

                }
            }

            if (failed > 0) {
                return 0;
            }

            return orderMapper.updateByPrimaryKeySelective(record);
        } else if (status.equals(OrderStatus.COMPLETED)) {
            return orderMapper.updateByPrimaryKeySelective(record);
        } else {
            return 0;
        }

    }

    /**
     * @param orderId
     * @param from
     * @param to
     * @return
     */
    @Transactional(readOnly = false)
    public int updateTransfer(Long orderId, Long from, Long to) {
        try {
            return orderMapper.transfer(orderId, from, to);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * ownerId是否拥有orderId
     *
     * @param ownerId
     * @param orderId
     * @return
     */
    public boolean hasOrder(long ownerId, BusinessType businessType, long orderId) {
        return orderMapper.hasOrder(ownerId, businessType, orderId);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(Long id) {
        return orderMapper.delete(id);
    }

    /**
     * 订单跨不部门转移
     * @param orderId  订单编号
     * @param ownerId
     * @param teamId
     * @param deptId
     * @return
     */
    @Transactional(readOnly = false)
    public int updateTransferToOtherDept(Long orderId, Long ownerId, Long teamId, Long deptId) {



        return orderMapper.updateTransferToOtherDept(orderId, ownerId, teamId, deptId);
    }
}
