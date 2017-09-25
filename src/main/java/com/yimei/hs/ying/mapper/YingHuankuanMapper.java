package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingHuankuan;
import com.yimei.hs.ying.dto.PageYingHuankuanDTO;

public interface YingHuankuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingHuankuan record);

    int insertSelective(YingHuankuan record);

    YingHuankuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingHuankuan record);

    int updateByPrimaryKey(YingHuankuan record);

    Page<YingHuankuan> getPage(PageYingHuankuanDTO pageYingHuankuanDTO);
}