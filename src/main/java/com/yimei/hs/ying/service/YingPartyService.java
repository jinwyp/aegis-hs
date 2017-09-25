package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.dto.PageYingOrderPartyDTO;
import com.yimei.hs.ying.entity.YingOrderParty;
import com.yimei.hs.ying.mapper.YingOrderPartyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/25.
 */
@Service
public class YingPartyService {

    @Autowired
    YingOrderPartyMapper yingOrderPartyMapper;

    public Page<YingOrderParty> getPage(PageYingOrderPartyDTO pageYingOrderPartyDTO) {
        return yingOrderPartyMapper.getPage(pageYingOrderPartyDTO);
    }
}
