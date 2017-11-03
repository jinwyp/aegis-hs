package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.CustomerType;
import com.yimei.hs.same.dto.PageOrderPartyDTO;
import com.yimei.hs.same.entity.InvoiceAnalysis;
import com.yimei.hs.same.entity.OrderParty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderPartyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderParty record);

    int insertSelective(OrderParty record);

    OrderParty selectByPrimaryKey(Long id);

    OrderParty selectByPrimaryKeyDeleted(Long id);

    int updateByPrimaryKeySelective(OrderParty record);

    int updateByPrimaryKey(OrderParty record);

    Page<OrderParty> getPage(PageOrderPartyDTO pageOrderPartyDTO);

    int delete(Long id);

    List<OrderParty> getList(Long orderId);

    List<OrderParty> getOrderPartyListByType(@Param("orderId")Long orderId,@Param("customerType") CustomerType customerType);

    List<OrderParty> findListByCustTypeAndOrderId( @Param("custType") String custType,  @Param("orderId")  Long orderId);

}