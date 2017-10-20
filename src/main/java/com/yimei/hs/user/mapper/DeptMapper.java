package com.yimei.hs.user.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.dto.PageDeptDTO;

import java.util.List;

public interface DeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dept record);

    Long insertSelective(Dept record);

    Dept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    Page<Dept> getPage(PageDeptDTO pageDeptDTO);

    boolean checkDeptExist(Long id);
}