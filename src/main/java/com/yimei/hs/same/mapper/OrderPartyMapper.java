package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageOrderPartyDTO;
import com.yimei.hs.same.entity.OrderParty;

import java.util.List;

public interface OrderPartyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderParty record);

    int insertSelective(OrderParty record);

    OrderParty selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderParty record);

    int updateByPrimaryKey(OrderParty record);

    Page<OrderParty> getPage(PageOrderPartyDTO pageOrderPartyDTO);

    int delete(Long id);

    List<OrderParty> getList(Long orderId);
}