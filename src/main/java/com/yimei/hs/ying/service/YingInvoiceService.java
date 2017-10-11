package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingInvoice;
import com.yimei.hs.ying.dto.PageYingInvoiceDTO;
import com.yimei.hs.ying.entity.YingInvoiceDetail;
import com.yimei.hs.ying.entity.YingOrder;
import com.yimei.hs.ying.mapper.YingInvoiceDetailMapper;
import com.yimei.hs.ying.mapper.YingInvoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class YingInvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleService.class);


    @Autowired
    private YingInvoiceMapper yingInvoiceMapper;

    @Autowired
    private YingInvoiceDetailMapper yingInvoiceDetailMapper;

    @Autowired
    private YingLogService yingLogService;

    public Page<YingInvoice> getPage(PageYingInvoiceDTO pageYingInvoiceDTO) {
        Page<YingInvoice> yingInvoicePage = yingInvoiceMapper.getPage(pageYingInvoiceDTO);
        for (YingInvoice invoice : yingInvoicePage.getResults()) {
            List<YingInvoiceDetail> details = yingInvoiceDetailMapper.getList(invoice.getId());
            invoice.setDetails(details);
        }
        return yingInvoicePage;
    }

    public YingInvoice findOne(long id) {
        return yingInvoiceMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public int create(YingInvoice yingInvoice) {

        int rtn = 0;

        List<YingInvoiceDetail> detailsList = yingInvoice.getDetails();

        if (detailsList != null) {
            rtn = yingInvoiceMapper.insert(yingInvoice);
            detailsList.forEach(new Consumer<YingInvoiceDetail>() {
                @Override
                public void accept(YingInvoiceDetail yingInvoiceDetail) {
                    yingInvoiceDetail.setInvoiceId(yingInvoice.getId());
                    yingInvoiceDetailMapper.insert(yingInvoiceDetail);
                }
            });
        }

        return rtn;
    }

    @Transactional(readOnly = false)
    public int update(YingInvoice yingInvoice) {
        return yingInvoiceMapper.updateByPrimaryKeySelective(yingInvoice);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long id) {
        yingInvoiceDetailMapper.deleteByInvoiceId(id);
         return yingInvoiceMapper.delete(id);
    }
}
