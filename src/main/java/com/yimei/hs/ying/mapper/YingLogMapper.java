package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingLog;
import com.yimei.hs.ying.dto.PageYingLogDTO;

public interface YingLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingLog record);

    int insertSelective(YingLog record);

    YingLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingLog record);

    int updateByPrimaryKey(YingLog record);

    Page<YingLog> getPage(PageYingLogDTO pageYingLogDTO);
}