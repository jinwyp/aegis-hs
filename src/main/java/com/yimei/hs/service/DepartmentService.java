package com.yimei.hs.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.Dept;
import com.yimei.hs.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class DepartmentService {

    @Autowired
    private DeptMapper deptMapper;

    public Page<Dept> selectAllDept() {

        return deptMapper.selectAll();
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

    public Long createDept(Dept department){
        return deptMapper.insert(department);
    }

    public boolean checkDepatIsExit(Long id){
        return deptMapper.checkDepayIsExist(id);
    }
}
