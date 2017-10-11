package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageFeeDTO;
import com.yimei.hs.same.entity.Fee;
import com.yimei.hs.same.mapper.FeeMapper;
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
public class FeeService {

    private static final Logger logger = LoggerFactory.getLogger(FeeService.class);


    @Autowired
    private FeeMapper yingFeeMapper;

    /**
     * 获取一页数据
     * @param pageFeeDTO
     * @return
     */
    public Page<Fee> getPage(PageFeeDTO pageFeeDTO) {
        return yingFeeMapper.getPage(pageFeeDTO);
    }

    /**
     * 获取指定id数据
     * @param id
     * @return
     */
    public Fee findOne(long id) {
        return yingFeeMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建费用记录
     * @param yingFee
     * @return
     */
    @Transactional(readOnly = false)
    public int create(Fee yingFee) {
        return yingFeeMapper.insert(yingFee);
    }

    /**
     * 更新费用记录
     * @param yingFee
     * @return
     */
    @Transactional(readOnly = false)
    public int update(Fee yingFee) {
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
