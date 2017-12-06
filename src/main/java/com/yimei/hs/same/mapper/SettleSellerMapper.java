package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageSettleSellerDTO;
import com.yimei.hs.same.entity.SettleSeller;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SettleSellerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SettleSeller record);

    int insertSelective(SettleSeller record);

    SettleSeller selectByPrimaryKey(Long id);

    SettleSeller selectByPrimaryKeyDeleted(Long id);

    int updateByPrimaryKeySelective(SettleSeller record);

    int updateByPrimaryKey(SettleSeller record);

    List<SettleSeller> selectByOrderId(Long orderId);

    Page<SettleSeller> getPage(PageSettleSellerDTO pageSettleSellerDTO);

    int delete(long id);

    Boolean findByOrderIdAndHsId(@Param("orderId") Long orderId, @Param("hsId") Long hsId);

    int deleteHsAndOrderId(@Param("orderId") Long orderId, @Param("hsId") Long hsId);
}