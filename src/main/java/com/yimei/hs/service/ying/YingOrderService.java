package com.yimei.hs.service.ying;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.yimei.hs.entity.YingOrder;
import com.yimei.hs.mapper.YingOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingOrderService {

    @Autowired
    private YingOrderMapper mYingOrderMapper;

    public List<YingOrder> quaryAllOrder() {

        return mYingOrderMapper.selectAllOrder();
    }

    public YingOrder quaryOrderbyId(long id) {
        return mYingOrderMapper.selectByPrimaryKey(id);
    }

    public int createOrder(YingOrder order) {
        return mYingOrderMapper.insert(order);
    }

    public int updateOrder(YingOrder record) {
        return mYingOrderMapper.updateByPrimaryKey(record);
    }
}
