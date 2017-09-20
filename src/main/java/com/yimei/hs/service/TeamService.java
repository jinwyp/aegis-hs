package com.yimei.hs.service;

import com.yimei.hs.entity.Team;
import com.yimei.hs.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class TeamService {

    @Autowired
    TeamMapper mTeamMapper;
//    //获得所有tean
//    public ArrayList<Team> getAllTeams(@NotNull int pageSize, @NotNull int paseNum, @Null Long deptId){
////        ArrayList<Team> teams= mTeamMapper.getAllTeams(pageSize,paseNum,deptId);
//        return teams;
//    }

    /**
     * 根据id查 团队
     * @param tid
     * @return
     */
   public Team findTeamByid(Long tid) {
       Team team = mTeamMapper.selectByPrimaryKey(tid);
       return team;
    }

    public int createTeams(Team team){
        return mTeamMapper.insert(team);
    }

    public int deleteTeamById(long id){
        return mTeamMapper.deleteByPrimaryKey(id);
    }

    public int updateTeam(Team team){

        return mTeamMapper.updateByPrimaryKey(team);
    }
    /**
     * 分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ArrayList<Team> getTeamPage(int pageSize,int pageNum ,Long deptID) {
        pageNum = (pageNum - 1) * pageSize;
      return   mTeamMapper.getPageTeam(pageNum,pageSize,deptID);
    }

    public boolean checkTeamExist(long tid) {
        return mTeamMapper.checkTeamExsit(tid);
    }
}
