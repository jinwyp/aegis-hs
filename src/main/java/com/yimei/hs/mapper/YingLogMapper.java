package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingLog;
import com.yimei.hs.entity.dto.ying.PageYingLogDTO;

public interface YingLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingLog record);

    int insertSelective(YingLog record);

    YingLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingLog record);

    int updateByPrimaryKey(YingLog record);

    Page<YingLog> getPage(PageYingLogDTO pageYingLogDTO);
}