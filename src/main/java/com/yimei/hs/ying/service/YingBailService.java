package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.dto.PageYingBailDTO;
import com.yimei.hs.ying.entity.YingBail;
import com.yimei.hs.ying.mapper.YingBailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class YingBailService {

    @Autowired
    private YingBailMapper yingBailMapper;

    @Autowired
    private YingLogService yingLogService;

    /**
     *  获取一页分页数据
     * @param pageYingBailDTO
     * @return
     */
    public Page<YingBail> getPage(PageYingBailDTO pageYingBailDTO) {
        return yingBailMapper.getPage(pageYingBailDTO);
    }

    /**
     * 查询发运
     * @param id
     * @return
     */
    public YingBail findOne(long id) {
        return yingBailMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建一条发运记录
     * @param yingBail
     * @return
     */
    @Transactional(readOnly = false)
    public int create(YingBail yingBail) {
        return yingBailMapper.insert(yingBail);
    }

    /**
     * 更新发运记录
     * @param yingBail
     * @return
     */
    @Transactional(readOnly = false)
    public int update(YingBail yingBail) {
        return yingBailMapper.updateByPrimaryKeySelective(yingBail);
    }

    /**
     * 逻辑删除
     *
     * @param orderId
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(Long orderId, long id) {
        return yingBailMapper.delete(id);
    }
}
