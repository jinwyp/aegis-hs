package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageSettleBuyerDTO;
import com.yimei.hs.same.entity.SettleBuyer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SettleBuyerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SettleBuyer record);

    int insertSelective(SettleBuyer record);

    SettleBuyer selectByPrimaryKey(Long id);

    SettleBuyer selectByPrimaryKeyDeleted(Long id);

    int updateByPrimaryKeySelective(SettleBuyer record);

    int updateByPrimaryKey(SettleBuyer record);


    Page<SettleBuyer> getPage(PageSettleBuyerDTO pageSettleBuyerDTO);


    int delete(long id);


    List<SettleBuyer> selectByOrderIdAndHsId(@Param("orderId") Long orderId, @Param("hsId") Long hsId);
}