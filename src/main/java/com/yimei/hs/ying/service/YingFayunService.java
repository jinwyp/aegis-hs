package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.service.LogService;
import com.yimei.hs.ying.entity.YingFayun;
import com.yimei.hs.ying.dto.PageYingFayunDTO;
import com.yimei.hs.ying.mapper.YingFayunMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class YingFayunService {

    @Autowired
    private YingFayunMapper yingFayunMapper;

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
    @Transactional(readOnly = false)
    public int create(YingFayun yingFayun) {
        return yingFayunMapper.insert(yingFayun);
    }

    /**
     * 更新发运记录
     * @param yingFayun
     * @return
     */
    @Transactional(readOnly = false)
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
    @Transactional(readOnly = false)
    public int delete(Long id,Long orderId) {
        return yingFayunMapper.delete(id);
    }
}
