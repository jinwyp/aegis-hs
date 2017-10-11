package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageFukuanDTO;
import com.yimei.hs.same.entity.Fukuan;

import java.util.List;

public interface FukuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Fukuan record);

    int delete(long id);

    int insertSelective(Fukuan record);

    Fukuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Fukuan record);

    int updateByPrimaryKey(Fukuan record);

    Page<Fukuan> getPage(PageFukuanDTO pageFukuanDTO);

    List<Fukuan> getList(Long orderId);
}