package com.yimei.hs.user.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.dto.PageDeptDTO;
import com.yimei.hs.user.mapper.DeptMapper;
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

    public Page<Dept> getPage(PageDeptDTO pageDeptDTO) {

        return deptMapper.getPage(pageDeptDTO);
    }

    public Dept findOne(long id) {
        return deptMapper.selectByPrimaryKey(id);
    }

    public int deleteDeptById(long id) {
        return deptMapper.deleteByPrimaryKey(id);
    }

    public int update(Dept dept){
        return deptMapper.updateByPrimaryKey(dept);
    }

    public int create(Dept department){
        return deptMapper.insert(department);
    }

    public boolean checkDeptExist(Long id){
        return deptMapper.checkDeptExist(id);
    }
}
