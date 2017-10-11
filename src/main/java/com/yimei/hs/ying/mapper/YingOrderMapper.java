package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingOrder;
import com.yimei.hs.ying.dto.PageYingOrderDTO;
import org.apache.ibatis.annotations.Param;

public interface YingOrderMapper {
    int deleteByPrimaryKey(Long id);

    int delete(Long id);

    int insert(YingOrder record);

    int insertSelective(YingOrder record);

    YingOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingOrder record);

    int updateByPrimaryKey(YingOrder record);

    Page<YingOrder> getPage(PageYingOrderDTO pageYingOrderDTO);

    boolean hasOrder(@Param("ownerId") long ownerId, @Param("orderId") long orderId);

    int transfer(@Param("id") Long id, @Param("from") Long from, @Param("to") Long to);
}