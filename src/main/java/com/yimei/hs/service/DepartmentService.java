package com.yimei.hs.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.Dept;
import com.yimei.hs.entity.dto.PageDeptDTO;
import com.yimei.hs.mapper.DeptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DeptMapper deptMapper;

    public Page<Dept> selectAllDept(PageDeptDTO pageDeptDTO) {

        return deptMapper.selectAll(pageDeptDTO);
    }

    public Dept selectDeptById(long id) {

        return deptMapper.selectByPrimaryKey(id);
    }

    public int deleteDeptById(long id) {
        return deptMapper.deleteByPrimaryKey(id);
    }
    public int update(Dept dept){
        return deptMapper.updateByPrimaryKey(dept);
    }

    public int createDept(Dept department){
        return deptMapper.insert(department);
    }

    public boolean checkDeptExist(Long id){
        return deptMapper.checkDepptExist(id);
    }
}
