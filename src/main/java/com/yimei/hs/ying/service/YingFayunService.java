package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFayun;
import com.yimei.hs.ying.dto.PageYingFayunDTO;
import com.yimei.hs.ying.mapper.YingFayunMapper;
import com.yimei.hs.ying.mapper.YingSettleDownstreamMapMapper;
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

    /**
     * 逻辑删除
     *
     * @param orderId
     * @param id
     * @return
     */
    public int delete(Long orderId, long id) {
        return yingFayunMapper.delete(id);
    }
}
