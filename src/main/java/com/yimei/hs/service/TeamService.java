package com.yimei.hs.service;

import com.yimei.hs.entity.Team;
import com.yimei.hs.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class TeamService {

    @Autowired
    TeamMapper mTeamMapper;
    //获得所有tean
    public ArrayList<Team> getAllTeams(){
        mTeamMapper.getAllTeams();
        return null;
    }

    /**
     * 根据id查 团队
     * @param tid
     * @return
     */
   public Team findTeamByid(Long tid) {
       Team team = mTeamMapper.selectByPrimaryKey(tid);
       return team;
    }
}
