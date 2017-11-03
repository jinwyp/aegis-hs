package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageTransferDTO;
import com.yimei.hs.same.entity.Transfer;

public interface TransferMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Transfer record);

    int insertSelective(Transfer record);

    Transfer selectByPrimaryKey(Long id);

    Transfer selectByPrimaryKeyDeleted(Long id);

    int updateByPrimaryKeySelective(Transfer record);

    int updateByPrimaryKey(Transfer record);

    Page<Transfer> getPage(PageTransferDTO pageTransferDTO);
}