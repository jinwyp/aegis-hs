package com.yimei.hs.user.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.dto.PageDeptDTO;
import com.yimei.hs.user.mapper.DeptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class DeptService {

    private static final Logger logger = LoggerFactory.getLogger(DeptService.class);

    @Autowired
    private DeptMapper deptMapper;

    public Page<Dept> getPage(PageDeptDTO pageDeptDTO) {

        return deptMapper.getPage(pageDeptDTO);
    }

    public Dept findOne(long id) {
        return deptMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public int deleteDeptById(long id) {
        return deptMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public int update(Dept dept){
        return deptMapper.updateByPrimaryKeySelective(dept);
    }

    @Transactional(readOnly = false)
    public int create(Dept Dept){
        return deptMapper.insert(Dept);
    }

    public boolean checkDeptExist(Long id){
        return deptMapper.checkDeptExist(id);
    }
}
