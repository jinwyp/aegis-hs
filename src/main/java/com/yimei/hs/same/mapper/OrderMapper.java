package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageOrderDTO;
import com.yimei.hs.same.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int delete(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    Order selectByPrimaryKeyDeleted(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Page<Order> getPage(PageOrderDTO pageOrderDTO);

    boolean hasOrder(@Param("ownerId") long ownerId, @Param("businessType") BusinessType businessType, @Param("orderId") long orderId);

    int transfer(@Param("id") Long id, @Param("from") Long from, @Param("to") Long to);

    boolean selectOrderListByDepartId(@Param("deptId") String deptId);


    int updateTransferToOtherDept(@Param("orderId") Long orderId,@Param("ownerId") Long ownerId,@Param("teamId") Long teamId,  @Param("deptId")Long deptId);
}