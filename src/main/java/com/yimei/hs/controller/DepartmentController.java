package com.yimei.hs.controller;

import com.yimei.hs.entity.Dept;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api")
public class DepartmentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DepartmentService departmentService;

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
    public ResponseEntity<PageResult<Dept>> list(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNum") int pageNum
    ) {
        return PageResult.ok(departmentService.selectAllDept());
    }

    /**
     * 获取department
     *
     * @param id
     * @return
     */
    @GetMapping("/departments/id")
    public ResponseEntity<Result<Dept>> read(@RequestParam long id) {
        if (departmentService.checkDepatIsExit(id)) {
            return Result.ok(departmentService.selectDeptById(id));
        } else {
            return Result.error(5003, "部门不存在");
        }

    }

    /**
     * 创建department
     *
     * @return
     */
    @PostMapping("/departments")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Dept>> create(@RequestBody Dept dept) {
        Long deptId = departmentService.createDept(dept);
        Result.ok(dept);
    }

    /**
     * 更新department
     *
     * @return
     */
    @PutMapping("/departments/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(@PathVariable(value = "id") Long id, @RequestParam(value = "deptName") String name) {
        Dept dept = new Dept();
        dept.setName(name);
        int success = departmentService.update(dept);
        if (success == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5005, "共享错误");
        }
    }

    /**
     * delete
     */
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Result<Integer>> delete(@PathVariable("id") String pid) {
       result.ok(departmentService.deleteDeptById(Long.parseLong(pid)));
    }
}
