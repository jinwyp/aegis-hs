package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.dto.PageYingBailDTO;
import com.yimei.hs.ying.entity.YingBail;

import java.util.List;

public interface YingBailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingBail record);

    int delete(long id);

    int insertSelective(YingBail record);

    YingBail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingBail record);

    int updateByPrimaryKey(YingBail record);

    Page<YingBail> getPage(PageYingBailDTO pageYingBailDTO);

    List<YingBail> getList(Long orderId);


}