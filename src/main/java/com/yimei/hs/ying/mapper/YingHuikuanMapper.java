package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingHuikuan;
import com.yimei.hs.ying.dto.PageYingHuikuanDTO;

import java.util.List;

public interface YingHuikuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingHuikuan record);

    int delete(long id);

    int insertSelective(YingHuikuan record);

    YingHuikuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingHuikuan record);

    int updateByPrimaryKey(YingHuikuan record);

    Page<YingHuikuan> getPage(PageYingHuikuanDTO pageYingHuikuanDTO);

    List<YingHuikuan> loadAll(long orderId);
}