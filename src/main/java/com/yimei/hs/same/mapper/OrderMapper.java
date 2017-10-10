package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageOrderDTO;
import com.yimei.hs.same.entity.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int delete(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Page<Order> getPage(PageOrderDTO pageOrderDTO);

    boolean hasOrder(@Param("ownerId") long ownerId, @Param("orderId") long orderId);

    int transfer(@Param("orderId") Long orderId, @Param("from") Long from, @Param("to") Long to);
}