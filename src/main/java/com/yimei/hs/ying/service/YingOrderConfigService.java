package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.dto.PageYingOrderConfigDTO;
import com.yimei.hs.ying.entity.YingOrderConfig;
import com.yimei.hs.ying.mapper.YingOrderConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/25.
 */
@Service
public class YingOrderConfigService {

    @Autowired
    YingOrderConfigMapper yingOrderConfigMapper;

    public Page<YingOrderConfig> getPage(PageYingOrderConfigDTO pageYingOrerConfigDTO) {
        return yingOrderConfigMapper.getPage(pageYingOrerConfigDTO);
    }

    public int create(YingOrderConfig yingOrderConfig) {
        return yingOrderConfigMapper.insert(yingOrderConfig);
    }

    public YingOrderConfig findOne(Long id) {
        return yingOrderConfigMapper.selectByPrimaryKey(id);
    }
}
