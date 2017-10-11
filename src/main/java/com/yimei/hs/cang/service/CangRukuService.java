package com.yimei.hs.cang.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.dto.PageCangRukuDTO;
import com.yimei.hs.cang.entity.CangRuku;
import com.yimei.hs.cang.mapper.CangRukuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class CangRukuService {

    @Autowired
    CangRukuMapper cangRukuMapper;

    public Page<CangRuku> getPage(PageCangRukuDTO pageCangRu) {
        return cangRukuMapper.getPage(pageCangRu);
    }

    public CangRuku findOne(long id) {
        return cangRukuMapper.selectByPrimaryKey(id);
    }

    public int create(CangRuku cangRuku) {
        return cangRukuMapper.insert(cangRuku);
    }

    public int update(CangRuku cangRuku) {
        return cangRukuMapper.updateByPrimaryKey(cangRuku);
    }

    public int delete(Long id) {
        return cangRukuMapper.delete(id);
    }
}
