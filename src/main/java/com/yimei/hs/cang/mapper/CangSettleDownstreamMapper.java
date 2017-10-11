package com.yimei.hs.cang.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.dto.PageCangSettleUpstreamDTO;
import com.yimei.hs.cang.entity.CangSettleDownstream;


import java.util.List;

public interface CangSettleDownstreamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangSettleDownstream record);

    int insertSelective(CangSettleDownstream record);

    CangSettleDownstream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangSettleDownstream record);

    int updateByPrimaryKey(CangSettleDownstream record);

    List<CangSettleDownstream> selectByOrderId(Long orderId);

    Page<CangSettleDownstream> getPage(PageCangSettleUpstreamDTO pageCangSettleUpstreamDTO);

    int delete(long id);
}