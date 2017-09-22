package com.yimei.hs.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.Team;
import com.yimei.hs.entity.dto.PageTeamDTO;
import com.yimei.hs.mapper.TeamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class TeamService {

    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    private TeamMapper mTeamMapper;

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

    public Page<Team> getTeamPage(PageTeamDTO pageTeamDTO) {
      return   mTeamMapper.getPageTeam(pageTeamDTO);
    }

    public boolean checkTeamExist(long tid) {
        return mTeamMapper.checkTeamExsit(tid);
    }
}
