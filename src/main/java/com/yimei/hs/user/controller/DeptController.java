package com.yimei.hs.user.controller;

import com.yimei.hs.boot.annotation.CurrentUser;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.boot.PageResult;
import com.yimei.hs.boot.Result;
import com.yimei.hs.user.dto.PageDeptDTO;
import com.yimei.hs.user.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api")
public class DeptController {

    private static final Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;

    /**
     * 部门分页
     * @param pageDeptDTO
     * @return
     */
    @GetMapping("/departments")
    public ResponseEntity<PageResult<Dept>> list(PageDeptDTO pageDeptDTO) {
        return PageResult.ok(deptService.getPage(pageDeptDTO));
    }

    /**
     * 获取部门详情
     * @param id
     * @return
     */
    @GetMapping("/departments/{id}")
    public ResponseEntity<Result<Dept>> read(@PathVariable(value = "id") long id) {
        Dept dept = deptService.findOne(id);
        if (dept == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(dept);
        }

    }

    /**
     * 创建部门
     * @return
     */
    @PostMapping("/departments")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Dept>> create(@RequestBody Dept dept) {
        deptService.create(dept);
        return Result.ok(dept);
    }

    /**
     * 更新dept
     * @return
     */
    @PutMapping("/departments/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable(value = "id") Long id,
            @RequestBody Dept dept) {
        dept.setId(id);
        int success = deptService.update(dept);
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
        return Result.ok(deptService.deleteDeptById(Long.parseLong(pid)));
    }
}
