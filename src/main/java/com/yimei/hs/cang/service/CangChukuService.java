package com.yimei.hs.cang.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.dto.PageCangChukuDTO;
import com.yimei.hs.cang.entity.CangChuku;
import com.yimei.hs.cang.mapper.CangChukuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class CangChukuService {

    @Autowired
    CangChukuMapper cangChukuMapper;

    public Page<CangChuku> getPage(PageCangChukuDTO pageCangChukuDTO) {
        return cangChukuMapper.getPage(pageCangChukuDTO);
    }

    public CangChuku findOne(long id) {
        return cangChukuMapper.selectByPrimaryKey(id);
    }

    public int create(CangChuku cangRuku) {
        return cangChukuMapper.insert(cangRuku);
    }

    public int update(CangChuku cangRuku) {
        return cangChukuMapper.updateByPrimaryKey(cangRuku);
    }

    public int delete(Long id) {
       return cangChukuMapper.delete(id);
    }
}
