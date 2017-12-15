package com.yimei.hs.user.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.dto.PageTeamDTO;
import com.yimei.hs.user.dto.PageUserTeamMapDTO;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.UserTeamMap;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface UserTeamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserTeamMap record);

    int insertSelective(UserTeamMap record);

    UserTeamMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserTeamMap record);

    int updateByPrimaryKey(UserTeamMap record);

    ArrayList<UserTeamMap> getAllTeams();

    Page<UserTeamMap> getPage(PageUserTeamMapDTO pageTeamDTO);

    boolean checkTeamExist(@Param("tid") long tid, @Param("userId") long userId);

    int delete(long id);

    List<UserTeamMap> getListUserId(Long userId);


    int deleteByUserId(Long id);

    int deleteByIdandUserId(@Param("id") Long id,@Param("userId") Long userId);
}