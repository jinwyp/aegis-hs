package com.yimei.hs.service;

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

}
