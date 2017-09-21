package com.yimei.hs.controller;

import com.yimei.hs.boot.annotation.CurrentUser;
import com.yimei.hs.entity.Dept;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.entity.dto.UserDTO;
import com.yimei.hs.entity.dto.ying.PageDeptDTO;
import com.yimei.hs.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public ResponseEntity<PageResult<Dept>> list(PageDeptDTO pageDeptDTO
    ) {
        return PageResult.ok(departmentService.selectAllDept(pageDeptDTO));
    }

    /**
     * 获取department
     *
     * @param id
     * @return
     */
    @GetMapping("/departments/{id}")
    public ResponseEntity<Result<Dept>> read(@PathVariable(value = "id") long id) {
        logger.warn(""+id);
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
       departmentService.createDept(dept);
       return Result.ok(dept);
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
        dept.setId(id);
        dept.setName(name);
        int success = departmentService.update(dept);
        if (success == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5003, "更新失败");
        }
    }

    /**
     * delete
     */
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Result<Integer>> delete(@PathVariable("id") String pid) {
        return Result.ok(departmentService.deleteDeptById(Long.parseLong(pid)));
    }
}
