package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingSettleUpstream;
import com.yimei.hs.ying.dto.PageYingSettleUpstreamDTO;

import java.util.List;

public interface YingSettleUpstreamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingSettleUpstream record);

    int insertSelective(YingSettleUpstream record);

    YingSettleUpstream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingSettleUpstream record);

    int updateByPrimaryKey(YingSettleUpstream record);

    List<YingSettleUpstream> selectByOrderId(Long orderId);

    Page<YingSettleUpstream> getPage(PageYingSettleUpstreamDTO pageYingSettleUpstreamDTO);

    int delete(long id);
}