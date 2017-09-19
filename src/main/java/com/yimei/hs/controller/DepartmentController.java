package com.yimei.hs.controller;

import com.yimei.hs.entity.Dept;
import com.yimei.hs.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private DepartmentService mDepartmentService;

    /**
     * 获取所有department
     *
     * @return
     */
    @GetMapping("/departments")
    public String list() {
        mDepartmentService.selectAllDept();
        return "department";
    }

    /**
     * 获取department
     *
     * @param id
     * @return
     */
    @GetMapping("/department/:id")
    public ResponseEntity read(long id) {

        return new ResponseEntity(mDepartmentService.selectDeptById(id), HttpStatus.OK);
    }

    /**
     * 创建department
     *
     * @return
     */
    @PostMapping("/departments")
    public String create() {
        mDepartmentService.createDept(new Dept());
        return "department";
    }

    /**
     * 更新department
     *
     * @return
     */
    @PutMapping("/department/:id")
    public String update()
    {
        mDepartmentService.update(new Dept());
        return "department";
    }
}
