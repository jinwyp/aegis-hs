package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.Dept;

import java.util.List;

public interface DeptMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(Dept record);

    Long insertSelective(Dept record);

    Dept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    Page<Dept> selectAll();

    boolean checkDepayIsExist(Long id);
}