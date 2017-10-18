package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageOrderConfigDTO;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.same.mapper.OrderConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hary on 2017/9/25.
 */
@Service
@Transactional(readOnly = true)
public class OrderConfigService {

    @Autowired
    OrderConfigMapper orderConfigMapper;

    /**
     *
     * @param pageOrderConfigDTO
     * @return
     */
    public Page<OrderConfig> getPage(PageOrderConfigDTO pageOrderConfigDTO) {
        return orderConfigMapper.getPage(pageOrderConfigDTO);
    }

    /**
     * 获取某笔订单的所有核算月配置
     * @param orderId
     * @return
     */
    public List<OrderConfig> getList(long orderId) {
        PageOrderConfigDTO dto = new PageOrderConfigDTO();
        dto.setOrderId(orderId);
        return orderConfigMapper.getPage(dto).getResults();
    }

    /**
     *
     * @param orderConfig
     * @return
     */
    @Transactional(readOnly = false)
    public int create(OrderConfig orderConfig) {
        return orderConfigMapper.insert(orderConfig);
    }

    /**
     *
     * @param id
     * @return
     */
    public OrderConfig findOne(Long id) {
        return orderConfigMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @param orderConfig
     * @return
     */
    @Transactional(readOnly = false)
    public int update(OrderConfig orderConfig) {
        return orderConfigMapper.updateByPrimaryKeySelective(orderConfig);
    }

    public boolean findOneByIdAndOrderId(String hsMonth, Long morderId) {
        return orderConfigMapper.findOneByIdAndOrderId(hsMonth, morderId);
    }
}
