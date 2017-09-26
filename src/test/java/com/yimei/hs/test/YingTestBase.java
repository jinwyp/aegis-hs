package com.yimei.hs.test;

import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.entity.*;
import org.springframework.core.ParameterizedTypeReference;

/**
 * Created by hary on 2017/9/26.
 */
abstract public class YingTestBase extends HsTestBase {

    protected ParameterizedTypeReference<Result<YingOrder>> typeReferenceOrder  = new ParameterizedTypeReference<Result<YingOrder>>() {};
    protected ParameterizedTypeReference<Result<YingOrderConfig>> typeReferenceOrderConfig  = new ParameterizedTypeReference<Result<YingOrderConfig>>() {};
    protected ParameterizedTypeReference<Result<YingOrderParty>> typeReferenceOrderParty  = new ParameterizedTypeReference<Result<YingOrderParty>>() {};
    protected ParameterizedTypeReference<Result<YingInvoice>> typeReferenceInvoice  = new ParameterizedTypeReference<Result<YingInvoice>>() {};
    protected ParameterizedTypeReference<Result<YingInvoiceDetail>> typeReferenceInvoiceDetail  = new ParameterizedTypeReference<Result<YingInvoiceDetail>>() {};
    protected ParameterizedTypeReference<Result<YingFayun>> typeReferenceFayun  = new ParameterizedTypeReference<Result<YingFayun>>() {};
    protected ParameterizedTypeReference<Result<YingFukuan>> typeReferenceFukuan  = new ParameterizedTypeReference<Result<YingFukuan>>() {};
    protected ParameterizedTypeReference<Result<YingHuikuan>> typeReferenceHuikuan  = new ParameterizedTypeReference<Result<YingHuikuan>>() {};
    protected ParameterizedTypeReference<Result<YingHuankuan>> typeReferenceHuankuan  = new ParameterizedTypeReference<Result<YingHuankuan>>() {};
    protected ParameterizedTypeReference<Result<YingSettleUpstream>> typeReferenceSettleUpstream  = new ParameterizedTypeReference<Result<YingSettleUpstream>>() {};
    protected ParameterizedTypeReference<Result<YingSettleDownstream>> typeReferenceSettleDownstream  = new ParameterizedTypeReference<Result<YingSettleDownstream>>() {};
    protected ParameterizedTypeReference<Result<YingSettleTraffic>> typeReferenceSettleTraffic  = new ParameterizedTypeReference<Result<YingSettleTraffic>>() {};

    protected ParameterizedTypeReference<PageResult<YingOrder>> typeReferenceOrderPage  = new ParameterizedTypeReference<PageResult<YingOrder>>() {};
    protected ParameterizedTypeReference<PageResult<YingOrderConfig>> typeReferenceOrderConfigPage  = new ParameterizedTypeReference<PageResult<YingOrderConfig>>() {};
    protected ParameterizedTypeReference<PageResult<YingOrderParty>> typeReferenceOrderPartyPage  = new ParameterizedTypeReference<PageResult<YingOrderParty>>() {};
    protected ParameterizedTypeReference<PageResult<YingInvoice>> typeReferenceInvoicePage  = new ParameterizedTypeReference<PageResult<YingInvoice>>() {};
    protected ParameterizedTypeReference<PageResult<YingInvoiceDetail>> typeReferenceInvoiceDetailPage  = new ParameterizedTypeReference<PageResult<YingInvoiceDetail>>() {};
    protected ParameterizedTypeReference<PageResult<YingFayun>> typeReferenceFayunPage  = new ParameterizedTypeReference<PageResult<YingFayun>>() {};
    protected ParameterizedTypeReference<PageResult<YingFukuan>> typeReferenceFukuanPage  = new ParameterizedTypeReference<PageResult<YingFukuan>>() {};
    protected ParameterizedTypeReference<PageResult<YingHuikuan>> typeReferenceHuikuanPage  = new ParameterizedTypeReference<PageResult<YingHuikuan>>() {};
    protected ParameterizedTypeReference<PageResult<YingHuankuan>> typeReferenceHuankuanPage  = new ParameterizedTypeReference<PageResult<YingHuankuan>>() {};
    protected ParameterizedTypeReference<PageResult<YingSettleUpstream>> typeReferenceSettleUpstreamPage  = new ParameterizedTypeReference<PageResult<YingSettleUpstream>>() {};
    protected ParameterizedTypeReference<PageResult<YingSettleDownstream>> typeReferenceSettleDownstreamPage  = new ParameterizedTypeReference<PageResult<YingSettleDownstream>>() {};
    protected ParameterizedTypeReference<PageResult<YingSettleTraffic>> typeReferenceSettleTrafficPage  = new ParameterizedTypeReference<PageResult<YingSettleTraffic>>() {};

}
