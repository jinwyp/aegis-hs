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
    private TeamMapper teamMapper;

    /**
     * 根据id查 团队
     * @param tid
     * @return
     */
   public Team findTeamByid(Long tid) {
       Team team = teamMapper.selectByPrimaryKey(tid);
       return team;
    }

    public int createTeams(Team team){
        return teamMapper.insert(team);
    }

    public int deleteTeamById(long id){
        return teamMapper.deleteByPrimaryKey(id);
    }

    public int updateTeam(Team team){

        return teamMapper.updateByPrimaryKey(team);
    }

    public Page<Team> getPage(PageTeamDTO pageTeamDTO) {
      return   teamMapper.getPage(pageTeamDTO);
    }

    public boolean checkTeamExist(long tid) {
        return teamMapper.checkTeamExsit(tid);
    }
}
