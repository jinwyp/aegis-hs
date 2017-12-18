package com.yimei.hs.user.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.dto.PageTeamDTO;
import com.yimei.hs.user.entity.UserTeamMap;
import com.yimei.hs.user.mapper.TeamMapper;
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
public class TeamService {

    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserTeamMapper userTeamMapper;

    /**
     * 根据id查 团队
     * @param tid
     * @return
     */
   public Team findOne(Long tid) {
       Team team = teamMapper.selectByPrimaryKey(tid);
       return team;
    }

    @Transactional(readOnly = false)
    public int create(Team team){
        return teamMapper.insert(team);
    }

    @Transactional(readOnly = false)
    public int delete(long id){
        return teamMapper.delete(id);
    }

    @Transactional(readOnly = false)
    public int update(Team team){

        return teamMapper.updateByPrimaryKeySelective(team);
    }

    public Page<Team> getPage(PageTeamDTO pageTeamDTO) {

        Page<Team> page = teamMapper.getPage(pageTeamDTO);
        List<Team> teams = page.getResults();
        for (Team team:teams) {
           List<Long> teamList= userTeamMapper.selectByTeamId(team.getId());
           team.setUserIdList(teamList);
        }

      return  page;
    }

    public boolean checkTeamExist(long tid) {
        return teamMapper.checkTeamExsit(tid);
    }

    public List<Team> getListBySameDeptId(Long deptId) {
        return teamMapper.getListBySameDeptId(deptId);
    }
}
