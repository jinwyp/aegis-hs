package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageJiekuanDTO;
import com.yimei.hs.same.entity.Jiekuan;

import java.util.List;

public interface JiekuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Jiekuan record);

    int insertSelective(Jiekuan record);

    Jiekuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Jiekuan record);

    int updateByPrimaryKey(Jiekuan record);

    List<Jiekuan> getListByFukuanId(Long id);

    Page<Jiekuan> getPage(PageJiekuanDTO pageJiekuanDTO);

    int delete(long id);
}