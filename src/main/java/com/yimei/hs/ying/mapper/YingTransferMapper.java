package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingTransfer;
import com.yimei.hs.ying.dto.PageYingTransferDTO;

public interface YingTransferMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingTransfer record);

    int insertSelective(YingTransfer record);

    YingTransfer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingTransfer record);

    int updateByPrimaryKey(YingTransfer record);

    Page<YingTransfer> getPage(PageYingTransferDTO pageYingTransferDTO);
}