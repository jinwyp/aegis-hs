package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingFukuan;
import com.yimei.hs.entity.dto.ying.PageYingFukuanDTO;

public interface YingFukuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingFukuan record);

    int insertSelective(YingFukuan record);

    YingFukuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingFukuan record);

    int updateByPrimaryKey(YingFukuan record);

    Page<YingFukuan> getPage(PageYingFukuanDTO pageYingFukuanDTO);
}