package com.yimei.hs.user.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.dto.PageTeamDTO;

import java.util.ArrayList;
import java.util.List;

public interface TeamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);

    ArrayList<Team> getAllTeams();

    Page<Team> getPage(PageTeamDTO pageTeamDTO);

    boolean checkTeamExsit(long tid);

    int delete(long id);


    boolean selectTeamByDepartId(String deptId);

    List<Team> getListBySameDeptId(Long deptId);
}