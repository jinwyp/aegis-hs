package com.yimei.hs.same.service;

import com.yimei.hs.same.mapper.InvoiceDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InvoiceDetailService {
    @Autowired
    private InvoiceDetailMapper invoiceDetailMapper;

    public int delete(long id) {
        return invoiceDetailMapper.delete(id);
    }
}
