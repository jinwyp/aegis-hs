package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingHuikuan;
import com.yimei.hs.entity.dto.ying.PageYingHuikuanDTO;

public interface YingHuikuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingHuikuan record);

    int insertSelective(YingHuikuan record);

    YingHuikuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingHuikuan record);

    int updateByPrimaryKey(YingHuikuan record);

    Page<YingHuikuan> getPage(PageYingHuikuanDTO pageYingHuikuanDTO);
}