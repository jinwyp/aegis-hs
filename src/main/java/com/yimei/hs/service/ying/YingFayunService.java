package com.yimei.hs.service.ying;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingFayun;
import com.yimei.hs.entity.dto.ying.PageYingFayunDTO;
import com.yimei.hs.mapper.YingFayunMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingFayunService {

    @Autowired
    private YingFayunMapper yingFayunMapper;

    @Autowired
    private YingLogService yingLogService;

    /**
     *  获取一页分页数据
     * @param pageYingFayunDTO
     * @return
     */
    public Page<YingFayun> getPage(PageYingFayunDTO pageYingFayunDTO) {
        return yingFayunMapper.getPage(pageYingFayunDTO);
    }

    /**
     * 查询发运
     * @param id
     * @return
     */
    public YingFayun findOne(long id) {
        return yingFayunMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建一条发运记录
     * @param yingFayun
     * @return
     */
    public int create(YingFayun yingFayun) {
        return yingFayunMapper.insert(yingFayun);
    }

    /**
     * 更新发运记录
     * @param yingFayun
     * @return
     */
    public int update(YingFayun yingFayun) {
        return yingFayunMapper.updateByPrimaryKeySelective(yingFayun);
    }
}
