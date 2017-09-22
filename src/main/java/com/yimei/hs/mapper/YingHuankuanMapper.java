package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingFukuan;
import com.yimei.hs.entity.YingHuankuan;
import com.yimei.hs.entity.dto.ying.PageYingHuankuanDTO;

public interface YingHuankuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingHuankuan record);

    int insertSelective(YingHuankuan record);

    YingHuankuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingHuankuan record);

    int updateByPrimaryKey(YingHuankuan record);

    Page<YingFukuan> getPage(PageYingHuankuanDTO pageYingHuankuanDTO);
}