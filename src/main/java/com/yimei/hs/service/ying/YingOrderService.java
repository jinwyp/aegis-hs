package com.yimei.hs.service.ying;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingOrder;
import com.yimei.hs.mapper.YingOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingOrderService {

    private static final Logger logger = LoggerFactory.getLogger(YingOrderService.class);

    @Autowired
    private YingOrderMapper yingOrderMapper;

    public Page<YingOrder> quaryAllOrder() {
        return yingOrderMapper.selectAllOrder();
    }

    public YingOrder quaryOrderbyId(long id) {
        return yingOrderMapper.selectByPrimaryKey(id);
    }

    public int createOrder(YingOrder order) {
        return yingOrderMapper.insert(order);
    }

    public int updateOrder(YingOrder record) {
        return yingOrderMapper.updateByPrimaryKey(record);
    }
}
