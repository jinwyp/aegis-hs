package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.dto.PageYingOrderPartyDTO;
import com.yimei.hs.ying.entity.YingOrderParty;

import java.util.List;

public interface YingOrderPartyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingOrderParty record);

    int insertSelective(YingOrderParty record);

    YingOrderParty selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingOrderParty record);

    int updateByPrimaryKey(YingOrderParty record);

    Page<YingOrderParty> getPage(PageYingOrderPartyDTO pageYingOrderPartyDTO);

    int delete(Long id);

    List<YingOrderParty> getList(Long orderId);
}