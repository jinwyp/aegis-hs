package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageOrderConfigDTO;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.same.mapper.OrderConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/25.
 */
@Service
public class OrderConfigService {

    @Autowired
    OrderConfigMapper orderConfigMapper;

    public Page<OrderConfig> getPage(PageOrderConfigDTO pageOrerConfigDTO) {
        return orderConfigMapper.getPage(pageOrerConfigDTO);
    }

    public int create(OrderConfig orderConfig) {
        return orderConfigMapper.insert(orderConfig);
    }

    public OrderConfig findOne(Long id) {
        return orderConfigMapper.selectByPrimaryKey(id);
    }

    public int update(OrderConfig orderConfig) {
        return orderConfigMapper.updateByPrimaryKeySelective(orderConfig);
    }
}
