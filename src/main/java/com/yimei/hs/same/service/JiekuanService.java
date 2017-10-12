package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageJiekuanDTO;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.same.mapper.JiekuanMapper;
import com.yimei.hs.same.mapper.HuikuanMapMapper;
import com.yimei.hs.same.mapper.HuikuanMapper;
import com.yimei.hs.same.mapper.JiekuanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class JiekuanService {

    private static final Logger logger = LoggerFactory.getLogger(JiekuanService.class);

    @Autowired
    JiekuanMapper jiekuanMapper;


    /**
     * 获取一页借款记录
     *
     * @param pageJiekuanDTO
     * @return
     */
    public Page<Jiekuan> getPage(PageJiekuanDTO pageJiekuanDTO) {
        Page<Jiekuan> page = jiekuanMapper.getPage(pageJiekuanDTO);
        return page;
    }

    /**
     * 创建借款
     * @param fukuan
     * @return
     */
    @Transactional(readOnly = false)
    public int create(Jiekuan fukuan) {
        int rtn = jiekuanMapper.insert(fukuan);
        return rtn;
    }

    /**
     * 查找借款
     * @param id
     * @return
     */
    public Jiekuan findOne(long id) {
        return jiekuanMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新借款
     * @param fukuan
     * @return
     */
    @Transactional(readOnly = false)
    public int update(Jiekuan fukuan) {
        return jiekuanMapper.updateByPrimaryKeySelective(fukuan);
    }

    /**
     * 删除借款记录
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long id) {
        return jiekuanMapper.delete(id);
    }


    /**
     *
     * @param orderId
     * @return
     */
    public List<Jiekuan> huankuanUnfinished(Long orderId) {
        return null;
    }
}
