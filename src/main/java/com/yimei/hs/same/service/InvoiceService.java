package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageInvoiceDTO;
import com.yimei.hs.same.entity.Invoice;
import com.yimei.hs.same.entity.InvoiceDetail;
import com.yimei.hs.same.mapper.InvoiceDetailMapper;
import com.yimei.hs.same.mapper.InvoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);


    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceDetailMapper invoiceDetailMapper;

    /**
     * @param pageInvoiceDTO
     * @return
     */
    public Page<Invoice> getPage(PageInvoiceDTO pageInvoiceDTO) {
        Page<Invoice> yingInvoicePage = invoiceMapper.getPage(pageInvoiceDTO);
        for (Invoice invoice : yingInvoicePage.getResults()) {
            List<InvoiceDetail> details = invoiceDetailMapper.getList(invoice.getId());
            BigDecimal totalPriceAndTax= details.stream().map(InvoiceDetail::getPriceAndTax).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalCargoAmount= details.stream().map(InvoiceDetail::getCargoAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            invoice.setDetails(details);
        }
        return yingInvoicePage;
    }

    /**
     * @param id
     * @return
     */
    public Invoice findOne(long id) {
        return invoiceMapper.selectByPrimaryKey(id);
    }

    /**
     * @param yingInvoice
     * @return
     */
    @Transactional(readOnly = false)
    public int create(Invoice yingInvoice) {

        //录入判断
//        Long openInvoiceId = yingInvoice.getOpenCompanyId();
//        Long receiverId = yingInvoice.getReceiverId();
//        int openCompanyType = orderPartyService.getPartyInfoByOrderIdAndCustId(openInvoiceId, yingInvoice.getOrderId());
//        int receiverType = orderPartyService.getPartyInfoByOrderIdAndCustId(receiverId, yingInvoice.getOrderId());
//
//        //  1: ccs账务公司, 2: 资金方, 3: 外部'
//
//        if (openCompanyType == 1) {
//            //发票号 货品种类可为空
//            if (yingInvoice.) {
//            }
//        } else {
//            if (openCompanyType==3 && receiverType==3) {
//                //                货品种类可为空
//            } else if (openCompanyType==3 && receiverType==1) {
//                //         货品种类可为空
//
//            }
//        }
        int rtn = 0;

        List<InvoiceDetail> detailsList = yingInvoice.getDetails();

        if (detailsList != null) {
            rtn = invoiceMapper.insert(yingInvoice);
            detailsList.forEach(new Consumer<InvoiceDetail>() {
                @Override
                public void accept(InvoiceDetail yingInvoiceDetail) {
                    yingInvoiceDetail.setInvoiceId(yingInvoice.getId());
                    invoiceDetailMapper.insert(yingInvoiceDetail);
                }
            });
        }

        return rtn;
    }

    /**
     * @param yingInvoice
     * @return
     */
    @Transactional(readOnly = false)
    public int update(Invoice yingInvoice) {
        int rtn = invoiceMapper.updateByPrimaryKeySelective(yingInvoice);

        List<InvoiceDetail> invoiceDetails = yingInvoice.getDetails();
        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            if (invoiceDetail.getId() == null) {
                invoiceDetailMapper.insert(invoiceDetail);
            } else {
                invoiceDetailMapper.updateByPrimaryKeySelective(invoiceDetail);
            }

        }

        return rtn;
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long id) {
        invoiceDetailMapper.deleteByInvoiceId(id);
        return invoiceMapper.delete(id);
    }
}
