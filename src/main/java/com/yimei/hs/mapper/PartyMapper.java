package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.Party;
import com.yimei.hs.entity.dto.PartyPageDTO;
import org.apache.ibatis.annotations.Param;

public interface PartyMapper {
    int deleteByPrimaryKey(Long id);

    long insert(Party record);

    int insertSelective(Party record);

    Party selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Party record);

    int updateByPrimaryKey(Party record);

    Page<Party> selectParty(PartyPageDTO partyPageDTO);
}