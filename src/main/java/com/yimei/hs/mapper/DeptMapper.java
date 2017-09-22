package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.Dept;
import com.yimei.hs.entity.dto.PageDeptDTO;

public interface DeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dept record);

    Long insertSelective(Dept record);

    Dept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    Page<Dept> selectAll(PageDeptDTO pageDeptDTO);

    boolean checkDepptExist(Long id);
}