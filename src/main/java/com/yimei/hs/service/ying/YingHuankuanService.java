package com.yimei.hs.service.ying;

import com.yimei.hs.mapper.YingHuankuanMapper;
import com.yimei.hs.mapper.YingHuikuanMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingHuankuanService {

    @Autowired
    private YingHuankuanMapper yingHuankuanMapper;

    @Autowired
    private YingHuikuanMapMapper yingHuikuanMapMapper;
}
