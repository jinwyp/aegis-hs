package com.yimei.hs.user.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.dto.PageTeamDTO;
import com.yimei.hs.user.dto.PageUserTeamMapDTO;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.user.entity.UserTeamMap;
import com.yimei.hs.user.mapper.DeptMapper;
import com.yimei.hs.user.mapper.UserMapper;
import com.yimei.hs.user.mapper.UserTeamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class UserTeamService {

    private static final Logger logger = LoggerFactory.getLogger(UserTeamService.class);

    @Autowired
    private UserTeamMapper userTeamMapper;

    @Autowired
    private UserMapper userMapper;

    private DeptMapper deptMapper;


    /**
     * 根据id查 团队
     *
     * @param tid
     * @return
     */
    public UserTeamMap findOne(Long tid) {
        UserTeamMap userTeamMap = userTeamMapper.selectByPrimaryKey(tid);
        return userTeamMap;
    }

    @Transactional(readOnly = false)
    public int create(UserTeamMap userTeamMap) {
        return userTeamMapper.insert(userTeamMap);
    }


    @Transactional(readOnly = false)
    public int update(UserTeamMap userTeamMap) {

        return userTeamMapper.updateByPrimaryKeySelective(userTeamMap);
    }

    public Page<UserTeamMap> getPage(PageUserTeamMapDTO pageUserTeamMapDTO) {
        return userTeamMapper.getPage(pageUserTeamMapDTO);
    }

    public boolean checkTeamExist(long tid, long userId) {
        return userTeamMapper.checkTeamExist(tid, userId);
    }


    //根据用户id查团队列表
    public List<UserTeamMap> getListUserId(Long userId) {
        return userTeamMapper.getListUserId(userId);
    }

    @Transactional(readOnly = false)
    public int delete(long id) {
        return userTeamMapper.delete(id);
    }

    //删除用户的某个团队
    @Transactional(readOnly = false)
    public int deleteByIdandUserId(Long id, Long userId) {

        return userTeamMapper.deleteByIdandUserId(id, userId);
    }

    //删除与该用户有关的团队
    @Transactional(readOnly = false)
    public int deleteByUserId(Long userId) {
        return userTeamMapper.deleteByUserId(userId);
    }


    @Transactional(readOnly = false)
    public int deleteTeamInDepart(long departId) {
        List<User> userTeamMaps = userMapper.getUsersOfSameDeptByDepart(departId);
        int rtn = 0;
        for (User user : userTeamMaps) {
            rtn = userTeamMapper.deleteByUserId(user.getId());
        }
        return rtn;
    }
}
