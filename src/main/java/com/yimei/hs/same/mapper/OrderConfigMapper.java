package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageOrderConfigDTO;
import com.yimei.hs.same.entity.OrderConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderConfig record);

    int deleteByOrderId(long orderId);

    int insertSelective(OrderConfig record);

    OrderConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderConfig record);

    int updateByPrimaryKey(OrderConfig record);

    Page<OrderConfig> getPage(PageOrderConfigDTO pageOrerConfigDTO);

    List<OrderConfig> getList(Long orderId);

    boolean findOneByIdAndOrderId(@Param("hsMonth") String hsMonth, @Param("orderId") Long orderId);
}