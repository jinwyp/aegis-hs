package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.ying.dto.PageYingFukuanDTO;

import java.util.List;

public interface YingFukuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingFukuan record);

    int delete(long id);

    int insertSelective(YingFukuan record);

    YingFukuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingFukuan record);

    int updateByPrimaryKey(YingFukuan record);

    Page<YingFukuan> getPage(PageYingFukuanDTO pageYingFukuanDTO);

    List<YingFukuan> getList(Long orderId);
}