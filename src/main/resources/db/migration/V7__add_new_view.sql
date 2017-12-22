use hsdb;

-- 【1079】上游供应商未开票吨位=【1040】核算结算量-汇总：进项或销项=”进项发票“且发票类型 = “货款发票”且开票单位 = “上游供应商ID”的票面数量
create view v_1079_temp as
select
invoice.orderId,
invoice.hsId,
sum(case when invoice.invoiceType='GOODS_INVOICE' and invoice.invoiceDirection='INCOME'
    then
    detail.cargoAmount
    else
    0.00
    end)  as  unOpenInvoiceAmount,
sum(case when invoice.invoiceType='GOODS_INVOICE' and invoice.invoiceDirection='INCOME'
    then
    detail.priceAndTax
    else
    0.00
    end)  as  unOpenInvoicePrice
from hs_same_invoice_detail detail
     inner join hs_same_invoice invoice on detail.invoiceId= invoice.id
     inner join hs_same_order orders on invoice.orderId=orders.id
where detail.deleted =0 and   invoice.openCompanyId=orders.upstreamId
group by  orderId,hsId;


create view v_1079_cang as
select
base.orderId,
base.hsId,
IFNULL(v_1041_cang.finalSettleAmount,0.00)-IFNULL(v_1079_temp.unOpenInvoiceAmount,0.00)  as upstreamUninvoiceAmount,
IFNULL(v_1046_cang.purchaseCargoAmountofMoney,0.00)-IFNULL(v_1079_temp.unOpenInvoicePrice,0.00)    as upstreamUninvoicePrice
from base
     left join v_1079_temp on v_1079_temp.hsId=base.hsId
     left join v_1041_cang on base.hsId=v_1041_cang.hsId
     left join v_1046_cang on  base.hsId=v_1046_cang.hsId
group by  orderId,hsId;


create view v_1079_ying as
select
base.orderId,
base.hsId,
IFNULL(v_1041_ying.finalSettleAmount,0.00)-IFNULL(v_1079_temp.unOpenInvoiceAmount,0.00)  as upstreamUnInvoiceAmount,
IFNULL(v_1046_ying.purchaseCargoAmountofMoney,0.00)-IFNULL(v_1079_temp.unOpenInvoicePrice,0.00)    as upstreamUninvoicePrice
from base
     left join v_1079_temp on v_1079_temp.hsId=base.hsId
     left join v_1041_ying on base.hsId=v_1041_ying.hsId
     left join v_1046_ying on base.hsId=v_1046_ying.hsId
group by  orderId,hsId;