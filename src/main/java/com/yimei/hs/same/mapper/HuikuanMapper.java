package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageHuikuanDTO;
import com.yimei.hs.same.entity.Fukuan;
import com.yimei.hs.same.entity.Huikuan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HuikuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Huikuan record);

    int delete(long id);

    int insertSelective(Huikuan record);

    Huikuan selectByPrimaryKey(Long id);

    Huikuan selectByPrimaryKeyDeleted(Long id);

    int updateByPrimaryKeySelective(Huikuan record);

    int updateByPrimaryKey(Huikuan record);

    Page<Huikuan> getPage(PageHuikuanDTO pageHuikuanDTO);

    List<Huikuan> loadAll(long orderId);

    List<Huikuan> getListByFukuanID(@Param("fukuanId") Long fukuanId);

    List<Huikuan> getUnfinshedByOrderId(Long orderId);

    List<Fukuan> getFukuanListByHuikuanId(Long huikuanId);

    List<Huikuan> gelistByhsIdAndOrderId(@Param("orderId") Long orderId, @Param("hsId") Long hsId);
}