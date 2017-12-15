package com.yimei.hs.user.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.ext.annotation.CurrentUser;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.dto.PageTeamDTO;
import com.yimei.hs.user.dto.PageUserTeamMapDTO;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.user.entity.UserTeamMap;
import com.yimei.hs.user.service.DeptService;
import com.yimei.hs.user.service.TeamService;
import com.yimei.hs.user.service.UserTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Controller
@RequestMapping("/api")
public class UserTeamMapController {

    private static final Logger logger = LoggerFactory.getLogger(UserTeamMapController.class);

    @Autowired
    private UserTeamService userTeamService;

    @Autowired
    private DeptService deptService;


    /**
     * @param pageUserTeamMapDTO
     * @return
     */
    @GetMapping("/userTeamsMaps")
    public ResponseEntity<Result<Page<UserTeamMap>>> list(PageUserTeamMapDTO pageUserTeamMapDTO) {
        logger.info("get teams: {}", pageUserTeamMapDTO);
        return Result.ok(userTeamService.getPage(pageUserTeamMapDTO));
    }


    /**
     * 创建team
     *
     * @return
     */
    @PostMapping("/userTeamsMaps")
    public ResponseEntity<Result<UserTeamMap>> create(@RequestBody UserTeamMap map) {
        ResponseEntity teamResponseEntity;

        if (!userTeamService.checkTeamExist(map.getTeamId(), map.getUserId())) {
            userTeamService.create(map);
            return Result.ok(map);
        } else {
            return Result.error(5001, "记录已存在");
        }
    }

    /**
     * 获取指定id的team
     *
     * @param id
     * @return
     */
    @GetMapping("/userTeamsMaps/{id}")
    public ResponseEntity<Result<UserTeamMap>> read(@PathVariable("id") long id) {
        UserTeamMap team = userTeamService.findOne(id);
        if (team == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(team);
    }

    /**
     * 更新team
     *
     * @return
     */
    @PutMapping("/userTeamsMaps/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("id") Long id,
            @RequestBody UserTeamMap team) {
        team.setId(id);
        int status = userTeamService.update(team);
        if (status == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5003, "更新失败");
        }
    }


    /**
     * 获取同一部门所有团队
     */
    @GetMapping("/userTeamsMaps/userId{userId}")
    public ResponseEntity<Result<List<UserTeamMap>>> getListBySameDeptId(
            @CurrentUser User user,
            @PathVariable("useId") Long userId
    ) {

        assert (user.getId() == userId);

        return Result.ok(userTeamService.getListUserId(userId));
    }


    /**
     * 删除id
     */
    @DeleteMapping("/userTeamsMaps/{id}")
    public ResponseEntity<Result<Integer>> delete(@PathVariable("id") long id) {

        int rtn = userTeamService.delete(id);
        if (rtn != 1) {
            return Result.error(4001, "删除失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }

    /**
     * 删除用户的某个团队
     */
    @DeleteMapping("/userTeamsMaps/{userId}/{id}")
    public ResponseEntity<Result<Integer>> deleteByIdAndUserId(
            @PathVariable("id") long id,
            @PathVariable("userId") long userId) {

        int rtn = userTeamService.deleteByIdandUserId(id, userId);
        if (rtn != 1) {
            return Result.error(4001, "删除失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }


    //删除与该用户有关的团队
    @DeleteMapping("/userTeamsMaps/userId{userId}")
    public ResponseEntity<Result<Integer>> deleteByUserId(

            @PathVariable("userId") long userId) {

        int rtn = userTeamService.deleteByUserId(userId);
        if (rtn ==0) {
            return Result.error(4001, "删除失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }


    /**
     * 删除该部门有关的团队（订单转移时使用）
     */
    @DeleteMapping("/userTeamsMaps/dept{depatId}")
    public ResponseEntity<Result<Integer>> deleteTeamInDepart(@PathVariable("depatId") long depatId) {

        int rtn = userTeamService.deleteTeamInDepart(depatId);
        if (rtn == 0) {
            return Result.error(4001, "删除失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }
}
