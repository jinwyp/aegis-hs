package com.yimei.hs.controller;

import com.yimei.hs.entity.Dept;
import com.yimei.hs.entity.dto.ResponseData;
import com.yimei.hs.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.security.tools.policytool.Resources_de;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api")
public class DepartmentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DepartmentService mDepartmentService;

    /**
     * 获取所有dept
     * 分页参数
     * page=10
     * pageSize=20
     * <p>
     * 查询条件参数
     * name   :  模糊查询
     *
     * @return
     */
    @GetMapping("/departments")
    public ResponseEntity<ResponseData> list(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNum") int pageNum
    ) {

        ResponseData responseData = new ResponseData();
        ArrayList<Dept> depts = (ArrayList<Dept>) mDepartmentService.selectAllDept();
        responseData.setData(depts);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    /**
     * 获取department
     *
     * @param id
     * @return
     */
    @GetMapping("/departments/id")
    public ResponseEntity<ResponseData> read(@RequestParam long id) {
        ResponseData responseData = new ResponseData();
        if (mDepartmentService.checkDepatIsExit(id)) {
            responseData.setData(mDepartmentService.selectDeptById(id));
            return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
        } else {
            responseData.setRmg("部门不存在");
            return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
        }

    }

    /**
     * 创建department
     *
     * @return
     */
    @PostMapping("/departments")
    @Transactional(readOnly = false)
    public ResponseEntity<ResponseData> create(@RequestBody Dept dept) {

        ResponseData responseData = new ResponseData();
        mDepartmentService.createDept(dept);
        if (dept.getId() != null) {
            responseData.setData(dept);
        } else {
            responseData.setRmg("添加部门失败");
        }
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    /**
     * 更新department
     *
     * @return
     */
    @PutMapping("/departments/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<ResponseData> update(@PathVariable(value = "id") String id, @RequestParam(value = "deptName") String name) {
        ResponseData responseData = new ResponseData();
        Long Lid = Long.parseLong(id);
        if (mDepartmentService.checkDepatIsExit(Lid)) {
            Dept dept = mDepartmentService.selectDeptById(Lid);
            dept.setName(name);
            int success = mDepartmentService.update(dept);
            if (success == 1) {
                responseData.setRmg("操作成功");
                responseData.setStatus(1);
            } else {
                responseData.setRmg("操作失败");
                responseData.setStatus(-1);
            }
        } else {

        }

        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    /**
     * delete
     */
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<ResponseData> delete(@PathVariable("id") String pid) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus(mDepartmentService.deleteDeptById(Long.parseLong(pid)));
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }
}
