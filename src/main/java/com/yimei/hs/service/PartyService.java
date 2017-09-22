package com.yimei.hs.service;


import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.Party;
import com.yimei.hs.entity.dto.PagePartyDTO;
import com.yimei.hs.mapper.PartyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class PartyService {

    @Autowired
    PartyMapper partyMapper;

    public Page<Party> getPage(PagePartyDTO pagePartyDTO) {
        return partyMapper.getPage(pagePartyDTO);
    }

    public Party findOne(long id) {
        return partyMapper.selectByPrimaryKey(id);
    }

    public int create(Party party) {
        return partyMapper.insert(party);
    }

    public int update(Party party) {
        return partyMapper.updateByPrimaryKey(party);
    }
}
