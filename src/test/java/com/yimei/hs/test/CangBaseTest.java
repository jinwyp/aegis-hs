package com.yimei.hs.test;

import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.cang.entity.*;
import org.springframework.core.ParameterizedTypeReference;

/**
 * Created by hary on 2017/9/26.
 */
abstract public class CangBaseTest extends  HsTestBase {

    protected ParameterizedTypeReference<Result<CangOrder>> typeReferenceOrder  = new ParameterizedTypeReference<Result<CangOrder>>() {};
    protected ParameterizedTypeReference<Result<CangOrderConfig>> typeReferenceOrderConfig  = new ParameterizedTypeReference<Result<CangOrderConfig>>() {};
    protected ParameterizedTypeReference<Result<CangOrderParty>> typeReferenceOrderParty  = new ParameterizedTypeReference<Result<CangOrderParty>>() {};
    protected ParameterizedTypeReference<Result<CangInvoice>> typeReferenceInvoice  = new ParameterizedTypeReference<Result<CangInvoice>>() {};
    protected ParameterizedTypeReference<Result<CangInvoiceDetail>> typeReferenceInvoiceDetail  = new ParameterizedTypeReference<Result<CangInvoiceDetail>>() {};
    protected ParameterizedTypeReference<Result<CangRuku>> typeReferenceFayun  = new ParameterizedTypeReference<Result<CangRuku>>() {};
    protected ParameterizedTypeReference<Result<CangFukuan>> typeReferenceFukuan  = new ParameterizedTypeReference<Result<CangFukuan>>() {};
    protected ParameterizedTypeReference<Result<CangHuikuan>> typeReferenceHuikuan  = new ParameterizedTypeReference<Result<CangHuikuan>>() {};
    protected ParameterizedTypeReference<Result<CangHuankuan>> typeReferenceHuankuan  = new ParameterizedTypeReference<Result<CangHuankuan>>() {};
    protected ParameterizedTypeReference<Result<CangSettleUpstream>> typeReferenceSettleUpstream  = new ParameterizedTypeReference<Result<CangSettleUpstream>>() {};
    protected ParameterizedTypeReference<Result<CangSettleDownstream>> typeReferenceSettleDownstream  = new ParameterizedTypeReference<Result<CangSettleDownstream>>() {};
    protected ParameterizedTypeReference<Result<CangSettleTraffic>> typeReferenceSettleTraffic  = new ParameterizedTypeReference<Result<CangSettleTraffic>>() {};

    protected ParameterizedTypeReference<PageResult<CangOrder>> typeReferenceOrderPage  = new ParameterizedTypeReference<PageResult<CangOrder>>() {};
    protected ParameterizedTypeReference<PageResult<CangOrderConfig>> typeReferenceOrderConfigPage  = new ParameterizedTypeReference<PageResult<CangOrderConfig>>() {};
    protected ParameterizedTypeReference<PageResult<CangOrderParty>> typeReferenceOrderPartyPage  = new ParameterizedTypeReference<PageResult<CangOrderParty>>() {};
    protected ParameterizedTypeReference<PageResult<CangInvoice>> typeReferenceInvoicePage  = new ParameterizedTypeReference<PageResult<CangInvoice>>() {};
    protected ParameterizedTypeReference<PageResult<CangInvoiceDetail>> typeReferenceInvoiceDetailPage  = new ParameterizedTypeReference<PageResult<CangInvoiceDetail>>() {};
    protected ParameterizedTypeReference<PageResult<CangRuku>> typeReferenceFayunPage  = new ParameterizedTypeReference<PageResult<CangRuku>>() {};
    protected ParameterizedTypeReference<PageResult<CangFukuan>> typeReferenceFukuanPage  = new ParameterizedTypeReference<PageResult<CangFukuan>>() {};
    protected ParameterizedTypeReference<PageResult<CangHuikuan>> typeReferenceHuikuanPage  = new ParameterizedTypeReference<PageResult<CangHuikuan>>() {};
    protected ParameterizedTypeReference<PageResult<CangHuankuan>> typeReferenceHuankuanPage  = new ParameterizedTypeReference<PageResult<CangHuankuan>>() {};
    protected ParameterizedTypeReference<PageResult<CangSettleUpstream>> typeReferenceSettleUpstreamPage  = new ParameterizedTypeReference<PageResult<CangSettleUpstream>>() {};
    protected ParameterizedTypeReference<PageResult<CangSettleDownstream>> typeReferenceSettleDownstreamPage  = new ParameterizedTypeReference<PageResult<CangSettleDownstream>>() {};
    protected ParameterizedTypeReference<PageResult<CangSettleTraffic>> typeReferenceSettleTrafficPage  = new ParameterizedTypeReference<PageResult<CangSettleTraffic>>() {};
}
