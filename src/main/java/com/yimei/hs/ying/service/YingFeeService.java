package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFee;
import com.yimei.hs.ying.dto.PageYingFeeDTO;
import com.yimei.hs.ying.mapper.YingFeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class YingFeeService {

    private static final Logger logger = LoggerFactory.getLogger(YingFeeService.class);


    @Autowired
    private YingFeeMapper yingFeeMapper;

    @Autowired
    private YingLogService yingLogService;

    /**
     * 获取一页数据
     * @param pageYingFeeDTO
     * @return
     */
    public Page<YingFee> getPage(PageYingFeeDTO pageYingFeeDTO) {
        return yingFeeMapper.getPage(pageYingFeeDTO);
    }

    /**
     * 获取指定id数据
     * @param id
     * @return
     */
    public YingFee findOne(long id) {
        return yingFeeMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建费用记录
     * @param yingFee
     * @return
     */
    @Transactional(readOnly = false)
    public int create(YingFee yingFee) {
        return yingFeeMapper.insert(yingFee);
    }

    /**
     * 更新费用记录
     * @param yingFee
     * @return
     */
    @Transactional(readOnly = false)
    public int update(YingFee yingFee) {
        return yingFeeMapper.updateByPrimaryKeySelective(yingFee);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long id) {
        return yingFeeMapper.delete(id);
    }

}
