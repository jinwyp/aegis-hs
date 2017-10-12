package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFayun;
import com.yimei.hs.ying.dto.PageYingFayunDTO;

import java.util.List;

public interface YingFayunMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingFayun record);

    int delete(long id);

    int insertSelective(YingFayun record);

    YingFayun selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingFayun record);

    int updateByPrimaryKey(YingFayun record);

    Page<YingFayun> getPage(PageYingFayunDTO pageYingFayunDTO);

    List<YingFayun> getList(Long orderId);


}