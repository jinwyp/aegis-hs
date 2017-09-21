package com.yimei.hs.service.ying;

import com.yimei.hs.entity.YingHuikuan;
import com.yimei.hs.mapper.YingHuikuanMapMapper;
import com.yimei.hs.mapper.YingHuikuanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingHuikuanService {

    @Autowired
    private YingHuikuanMapMapper yingHuikuanMapMapper;

    @Autowired
    private YingHuikuanMapper yingHuikuanMapper;




}
