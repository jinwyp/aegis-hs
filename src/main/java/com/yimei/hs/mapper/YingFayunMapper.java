package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingFayun;
import com.yimei.hs.entity.dto.ying.PageYingFayunDTO;

public interface YingFayunMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingFayun record);

    int insertSelective(YingFayun record);

    YingFayun selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingFayun record);

    int updateByPrimaryKey(YingFayun record);

    Page<YingFayun> getPage(PageYingFayunDTO pageYingFayunDTO);
}