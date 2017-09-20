package com.yimei.hs.mapper;

import com.yimei.hs.entity.Dept;

import java.util.List;

public interface DeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    List<Dept> selectAll();

    boolean checkDepayIsExist(Long id);
}