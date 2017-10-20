package com.yimei.hs.user.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.mapper.OrderMapper;
import com.yimei.hs.user.dto.PageDeptDTO;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.mapper.TeamMapper;
import com.yimei.hs.user.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api")
public class DeptController {

    private static final Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TeamMapper teamMapper;

    /**
     * 部门分页
     *
     * @param pageDeptDTO
     * @return
     */
    @GetMapping("/departments")
    public ResponseEntity<Result<Page<Dept>>> list(PageDeptDTO pageDeptDTO) {
        return Result.ok(deptService.getPage(pageDeptDTO));
    }

    /**
     * 获取部门详情
     *
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
     *
     * @return
     */
    @PostMapping("/departments")
    public ResponseEntity<Result<Dept>> create(@RequestBody Dept dept) {
        int rtn = deptService.create(dept);
        if (rtn != 1) {
            logger.error("创建部门失败: {}", dept);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(dept);
    }

    /**
     * 更新dept
     *
     * @return
     */
    @PutMapping("/departments/{id}")
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

        if (!orderMapper.selectOrderListByDepartId(pid)
                && !teamMapper.selectTeamByDepartId(pid)) {
            return Result.ok(deptService.deleteDeptById(Long.parseLong(pid)));
        } else {
            return Result.error(4001, "该订单不能删除");
        }

    }
}
