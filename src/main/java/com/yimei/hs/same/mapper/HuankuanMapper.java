package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageHuankuanDTO;
import com.yimei.hs.same.entity.Huankuan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HuankuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Huankuan record);

    int delete(long id);

    int insertSelective(Huankuan record);

    Huankuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Huankuan record);

    int updateByPrimaryKey(Huankuan record);

    Page<Huankuan> getPage(PageHuankuanDTO pageHuankuanDTO);

    List<Huankuan> getListByFukuanId(@Param("fukuanId") Long fukuanId);

}