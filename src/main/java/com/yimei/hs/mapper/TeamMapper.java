package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.Team;
import com.yimei.hs.entity.dto.TeamPageDTO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface TeamMapper {
    int deleteByPrimaryKey(Long id);

    long insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);

    ArrayList<Team> getAllTeams();

    Page<Team> getPageTeam(TeamPageDTO teamPageDTO);

    boolean checkTeamExsit(long tid);
}