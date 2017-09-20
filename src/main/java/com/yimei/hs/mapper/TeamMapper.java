package com.yimei.hs.mapper;

import com.yimei.hs.entity.Team;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface TeamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);

    ArrayList<Team> getAllTeams();

    ArrayList<Team> getPageTeam( @Param("pageNum") int pageNum,@Param("pageSize") int pageSize, @Param("deptId") Long deptId);

    boolean checkTeamExsit(long tid);
}