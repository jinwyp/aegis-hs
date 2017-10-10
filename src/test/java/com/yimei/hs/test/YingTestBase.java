package com.yimei.hs.test;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
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
    protected ParameterizedTypeReference<Result<YingFee>> typeReferenceFee = new ParameterizedTypeReference<Result<YingFee>>() {};

    protected ParameterizedTypeReference<Result<Page<YingOrder>>> typeReferenceOrderPage  = new ParameterizedTypeReference<Result<Page<YingOrder>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingOrderConfig>>> typeReferenceOrderConfigPage  = new ParameterizedTypeReference<Result<Page<YingOrderConfig>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingOrderParty>>> typeReferenceOrderPartyPage  = new ParameterizedTypeReference<Result<Page<YingOrderParty>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingInvoice>>> typeReferenceInvoicePage  = new ParameterizedTypeReference<Result<Page<YingInvoice>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingInvoiceDetail>>> typeReferenceInvoiceDetailPage  = new ParameterizedTypeReference<Result<Page<YingInvoiceDetail>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingFayun>>> typeReferenceFayunPage  = new ParameterizedTypeReference<Result<Page<YingFayun>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingFukuan>>> typeReferenceFukuanPage  = new ParameterizedTypeReference<Result<Page<YingFukuan>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingHuikuan>>> typeReferenceHuikuanPage  = new ParameterizedTypeReference<Result<Page<YingHuikuan>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingHuankuan>>> typeReferenceHuankuanPage  = new ParameterizedTypeReference<Result<Page<YingHuankuan>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingSettleUpstream>>> typeReferenceSettleUpstreamPage  = new ParameterizedTypeReference<Result<Page<YingSettleUpstream>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingSettleDownstream>>> typeReferenceSettleDownstreamPage  = new ParameterizedTypeReference<Result<Page<YingSettleDownstream>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingSettleTraffic>>> typeReferenceSettleTrafficPage  = new ParameterizedTypeReference<Result<Page<YingSettleTraffic>>>() {};
    protected ParameterizedTypeReference<Result<Page<YingFee>>> typeReferenceFeePage = new ParameterizedTypeReference<Result<Page<YingFee>>>() {};

}
