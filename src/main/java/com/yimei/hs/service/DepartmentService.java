package com.yimei.hs.service;

import com.yimei.hs.entity.Dept;
import com.yimei.hs.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class DepartmentService {

    @Autowired
    private DeptMapper mDeptMapper;

    public List<Dept> selectAllDept() {

        return mDeptMapper.selectAll();
    }

    public Dept selectDeptById(long id) {

        return mDeptMapper.selectByPrimaryKey(id);
    }

    public int deleteDeptById(long id) {
        return mDeptMapper.deleteByPrimaryKey(id);
    }
    public int update(Dept dept){
        return mDeptMapper.updateByPrimaryKey(dept);
    }

    public int createDept(Dept department){
        return mDeptMapper.insert(department);
    }

    public boolean checkDepatIsExit(Long id){
        return mDeptMapper.checkDepayIsExist(id);
    }
}
