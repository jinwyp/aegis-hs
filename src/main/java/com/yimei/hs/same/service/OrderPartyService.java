package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.CustomerType;
import com.yimei.hs.same.dto.PageOrderPartyDTO;
import com.yimei.hs.same.entity.OrderParty;
import com.yimei.hs.same.mapper.OrderPartyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hary on 2017/9/25.
 */
@Service
@Transactional(readOnly = true)
public class OrderPartyService {

    @Autowired
    OrderPartyMapper orderPartyMapper;

    /**
     * 获取应收订单(业务线)的参与方
     *
     * @param pageOrderPartyDTO
     * @return
     */
    public Page<OrderParty> getPage(PageOrderPartyDTO pageOrderPartyDTO) {
        return orderPartyMapper.getPage(pageOrderPartyDTO);
    }

    public int update(OrderParty yingOrderParty) {
        return orderPartyMapper.updateByPrimaryKeySelective(yingOrderParty);
    }

    public int delete(Long id) {
        return orderPartyMapper.delete(id);
    }

    public List<OrderParty> getOrderPartyListByType(Long morderId, CustomerType customerType) {
        return orderPartyMapper.getOrderPartyListByType(morderId,customerType);
    }
}
