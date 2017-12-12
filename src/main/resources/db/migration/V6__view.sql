use hsdb;

create  view base as
select
orders.id as orderId,
config.id as hsId,
orders.upstreamId,
config.hsMonth,
config.contractBaseInterest,
config.maxPrepayRate,
config.tradeAddPrice,
config.unInvoicedRate,
config.weightedPrice,
config.expectHKDays
from hs_same_order orders
     left join hs_same_order_config config on orders.id=config.orderId and config.deleted=0;


--运费相关 付款运费金额
create view v_1001 as
select
base.orderId,
base.hsId,
ROUND(sum(case when  payUsage= 'FREIGNHT' then IFNULL(payAmount,0.00) else 0 end),2)as totalPayTrafficFee,
ROUND(sum(case when  payUsage= 'PAYMENT_FOR_GOODS' then IFNULL(payAmount,0.00) else 0 end),2)as totalPayGoodsFee,
ROUND(sum(case when  payUsage= 'TRADE_DEFICIT' then IFNULL(payAmount,0.00) else 0 end ),2)as totalTradeGapFee,
ROUND(sum(IFNULL(payAmount,0.00)), 2)as totalPaymentAmount,
ROUND(sum(case when  fukuan.receiveCompanyId=base.upstreamId then IFNULL(payAmount,0.00) else 0 end ),2)  as forCapitalPressure
from base
left join hs_same_fukuan fukuan on base.hsId=fukuan.hsId and   deleted=0
group by  orderId, hsId;


--create view v_1002 as
--select
--base.orderId,
--base.hsId,
--ROUND(sum(IFNULL(payAmount,0.00)) ,2)as totalTradeGapFee
--from hs_same_fukuan
--where payUsage= 'TRADE_DEFICIT' and deleted=0
--group by  orderId, hsId;
--
--
--create view v_1003 as
--select
--orderId,
--hsId,
--ROUND(sum(IFNULL(payAmount,0.00)) ,2)as totalPaymentAmount
--from hs_same_fukuan
--where deleted=0
--group by  orderId, hsId;


--借款合计

create view v_1004 as
select
base.orderId,
base.hsId,
ROUND(sum(IFNULL(amount,0.00)) ,2)as totalLoadMoney
from   base 
left join  hs_same_jiekuan jiekuan on base.hsId=jiekuan.hsId and  jiekuan.deleted=0
group by  orderId, hsId;


--借款预估成本
create view v_1005 as
select
base.orderId,
base.hsId,
ROUND(amount*IFNULL(useInterest,0.00)*IFNULL(useDays,0.00)/360 ,2)as loadEstimateCost
from  base
left join  hs_same_jiekuan jiekuan on base.hsId=jiekuan.hsId and jiekuan.deleted =0;



--查询未对应的借款
--create view temp_jiekuan as


--
--  累计未还款金额数据 nonRepaymentLoanMoney
--  还款预估成本未 totalUnrepaymentEstimateCost
create view v_1006 as
select  
base.orderId,
base.hsId,
ROUND(IFNULL(sum(IFNULL(amount,0.00)*IFNULL(useInterest,0.00) * IFNULL(useDays,0.00)/360),0.00),2)  as totalUnrepaymentEstimateCost
from base 
left join hs_same_jiekuan jiekuan on base.hsId=jiekuan.hsId and base.orderId=jiekuan.orderId and  deleted=0
LEFT OUTER JOIN  hs_same_huankuan_map map  on  jiekuan.orderId=map.orderId and map.deleted=0
where map.jiekuanId IS null
group by  orderId, hsId;



--还款相关 1007  1008  1011
--1007	还款本金合计	totalRepaymentPrincipeAmount	【还款】		汇总：还款本金
--1008	还款利息合计	totalrepaymentInterest	【还款】		汇总：还款利息
--1011	还款服务费合计	totalServiceCharge	【还款】		汇总：还款服务费
--10  瑞茂通服务费合计 totalccsPayServiceCharge  【还款】    汇总：还款服务费
--10  瑞茂通服务费待还状态 totalccsPayUnpromiseServiceCharge  


create view v_1007 as
select
base.orderId,
base.hsId,
ROUND(sum(IFNULL(map.principal,0.00)),2) as totalRepaymentPrincipeAmount,
ROUND(sum(IFNULL(map.interest,0.00)),2) as  totalRepaymentInterest,
ROUND(sum(IFNULL(map.fee,0.00)),2) as  totalServiceCharge,
ROUND(sum(case when map.ccsPay=1 then map.fee else 0 end) ,2)  as  totalccsPayServiceCharge,
ROUND(sum(case when map.ccsPay=1 and huankuan.promise=false  then map.fee else 0 end) ,2)  as  totalccsPayUnpromiseServiceCharge
from base
     left join hs_same_huankuan huankuan on base.hsId=huankuan.hsId and huankuan.deleted=0
     left join hs_same_huankuan_map map on huankuan.id= map.huankuanId and  map.deleted=0
group by  orderId, hsId;

--还款状态  1009  1010
--1009	登记未还本金金额	totalUnpayPrincipal	【还款】		汇总：还款状态= “未还款”的还款本金
--1010	登记未还利息金额	totalUnpayInterest	【还款】		汇总：还款状态= “未还款”的还款利息
create view v_1009 as
select
base.orderId,
base.hsId,
ROUND(sum(IFNULL(map.principal,0.00)) ,2)as totalUnpayPrincipal,
ROUND(sum(IFNULL(map.interest,0.00)),2)as  totalUnpayInterest,
ROUND(sum(IFNULL(map.fee,0.00)),2)as  totalUnpayFee
from base 
left join hs_same_huankuan huankuan on  base.hsId =huankuan.hsId  and huankuan.deleted=0 and promise=false 
left join hs_same_huankuan_map map on huankuan.id= map.huankuanId and map.deleted=0
group by  orderId, hsId;

--  1012 外部资金使用成本   【1006】未还款预估成本 +【1008】还款利息合计 + 【1011】还款服务费合计


create view v_1012 as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1006.totalUnrepaymentEstimateCost,0.00)+IFNULL(v_1007.totalRepaymentInterest,0.00)+IFNULL(v_1007.totalServiceCharge,0.00) ,2)as outCapitalAmout
from base 
left join v_1007 on  v_1007.hsId=base.hsId
left join v_1006 on  v_1006.hsId=v_1007.hsId and v_1006.orderId=v_1007.orderId;


--1013 已回款金额  【1006】未还款预估成本 +【1008】还款利息合计 + 【1011】还款服务费合计
create view v_1013 as
select
base.orderId,
base.hsId,
ROUND(sum(IFNULL(huikuanAmount,0.00)) ,2)as totalHuikuanPaymentMoney
from base
left join hs_same_huikuan huikuan on base.hsId= huikuan.hsId and huikuan.deleted =0
group by  orderId, hsId;

--付货款金额
create view v_1014 as
select
v_1001.orderId,
v_1001.hsId,
ROUND(IFNULL(v_1001.totalPayGoodsFee,0.00),2) as payCargoAmount
from v_1001;

--1015未回款金额

create view v_1015 as
select
base.orderId,
base.hsId,
case  when IFNULL(v_1001.totalPayGoodsFee,0.00)+ IFNULL(v_1001.totalPayTrafficFee,0.00)> IFNULL(v_1013.totalHuikuanPaymentMoney,0.00)
THEN  ROUND(
  IFNULL(v_1001.totalPayGoodsFee,0.00)+ IFNULL(v_1001.totalPayTrafficFee,0.00) -IFNULL(v_1013.totalHuikuanPaymentMoney,0.00),2)
ELSE 0.00 end  as unpaymentMoney
from base
left join v_1001 on base.hsId=v_1001.hsId
left JOIN v_1013  on base.hsId = v_1013.hsId;

--1016 未回款金额预估收益

create view v_1016 as
select
v_1015.orderId,
v_1015.hsId,
ROUND(IFNULL(v_1015.unpaymentMoney,0.00) * IFNULL(config.contractBaseInterest,0.00) * IFNULL(config.expectHKDays,0.00) / 360 ,2)as unpaymentEstimateProfile
from v_1015
     left JOIN hs_same_order_config config  on config.id = v_1015.hsId and  config.deleted=0;


--1017 计息天数  每条回款-付款记录：计息天数 = 回款日期 - 付款日期 - 【买方结算】折扣天数

create view v_1017 as
select
DISTINCT
base.orderId,
base.hsId,
IFNULL(
case when
         case  when
                datediff(huikuan.huikuanDate,fukuan.payDate)<0
               then 0 else datediff(huikuan.huikuanDate,fukuan.payDate)
               end
               -IFNULL(seller.discountDays,0.00)
                <0
then 0
          else
              case  when datediff(huikuan.huikuanDate,fukuan.payDate)<0
               then 0 else datediff(huikuan.huikuanDate,fukuan.payDate)
               end
               -IFNULL(seller.discountDays,0.00)
                <0
          end
,0) as interestDays
from base 
     left join hs_same_huikuan huikuan on  base.hsId=huikuan.hsId
     left join hs_same_huikuan_map map on map.huikuanId =huikuan.id
     left join hs_same_fukuan  fukuan on fukuan.id=map.fukuanId
     left join hs_same_settle_seller seller on huikuan.orderId=seller.orderId and  seller.deleted=0;

--1018 实际使用率
create view v_1018 as
select
base.orderId,
base.hsId,
ROUND(IFNULL(config.contractBaseInterest,0.00)- IFNULL(seller.discountInterest,0.00) ,2)as actualUtilizationRate
from base 
     left join  hs_same_order_config config on  base.hsId=config.id and config.deleted=0
     left join hs_same_settle_seller seller on config.id=seller.hsId and  seller.deleted=0;

--1019 应计利息
create view v_1019 as
select

base.orderId,
base.hsId,
case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end as time,

CASE WHEN IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)>0
          AND IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)<60
THEN IFNULL(map.amount,0.00)*IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)
     *IFNULL(v_1018.actualUtilizationRate,0.00)/360

WHEN IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)>=60
     AND IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)<90
THEN IFNULL(map.amount,0.00)*((case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end-60.00)*
                                                                                                                                                                          (v_1018.actualUtilizationRate+0.05)+60*v_1018.actualUtilizationRate)/360
WHEN IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)>=90
THEN IFNULL(map.amount,0.00)*
                         ((case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end-90)
                          *(v_1018.actualUtilizationRate+0.05)+30*(v_1018.actualUtilizationRate+0.05)+60*v_1018.actualUtilizationRate)/360
ELSE 0 END as rate

from base 
     left join hs_same_huikuan_map map on base.orderId=map.orderId and  map.deleted=0
     left join hs_same_huikuan huikuan on huikuan.id=map.huikuanId  and huikuan.hsId=base.hsId and base.orderId=huikuan.orderId
     left join hs_same_fukuan fukuan on fukuan.id=map.fukuanId  and fukuan.hsId=base.hsId and base.orderId=fukuan.orderId and fukuan.deleted=0
     left join v_1018 on v_1018.hsId=huikuan.hsId  and   v_1018.orderId=base.orderId
     left join hs_same_settle_seller seller on fukuan.hsId=seller.hsId  and  seller.deleted=0;



--1020 已回款应计利息合计
create view v_1020 as
select
base.orderId,
base.hsId,
ROUND(sum(IFNULL(rate,0.00)) ,2)as totalPaymentedRateMoney
from base 
left  join  v_1019 on  base.hsId=v_1019.hsId
group by orderId,hsId;


--1021
create view v_1021 as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1016.unpaymentEstimateProfile,0.00)+IFNULL(v_1020.totalPaymentedRateMoney,0.00)-IFNULL(seller.discountAmount,0.00),2) as contractRateProfile
from base 
     left join v_1016 on  base.hsId=v_1016.hsId
     left join v_1020 on v_1016.hsId=v_1020.hsId
     left join hs_same_settle_seller seller on v_1016.hsId=seller.hsId and seller.orderId=v_1016.orderId  and seller.deleted=0;

--1022 贴现息

create view v_1022 as
select
base.orderId,
base.hsId,
CASE WHEN huikuanMode ='BANK_ACCEPTANCE'  and huikuan.huikuanBankDiscount=1
THEN ROUND( IFNULL(DATEDIFF(huikuanBankPaperExpire,huikuanBankPaperDate),0.00) *IFNULL(huikuanAmount ,0.00)* IFNULL(huikuanBankDiscountRate,0.00)*1.17/360,2)
WHEN huikuanMode ='BUSINESS_ACCEPTANCE'  and huikuan.huikuanBusinessDiscount=1
THEN ROUND(IFNULL(DATEDIFF(huikuanBusinessPaperExpire,huikuanBusinessPaperDate),0) *IFNULL(huikuanAmount ,0.00)* IFNULL(huikuanBusinessDiscountRate,0.00)*1.17/360,2)
ELSE 0.00 END as tiexianRate
from base 
left join hs_same_huikuan huikuan on  base.hsId=huikuan.hsId and huikuan.deleted= 0;


--1023 贴现息合计
create  view v_1023 as
select
base.orderId,
base.hsId,
ROUND(sum(IFNULL(v_1022.tiexianRate,0.00)) ,2)as tiexianRateAmount
from base 
left join v_1022 on  base.hsId=v_1022.hsId
group by orderId,hsId; 

--1024 买方结算   1024  1025 1026
create view v_1024 as
select
base.orderId,
base.hsId,
ROUND(sum(IFNULL(amount,0.00)),2) as totalBuyerNums,
ROUND(sum(IFNULL(money,0.00)) ,2)as totalBuyerMoney,
ROUND(sum(IFNULL(settleGap,0.00)),2) as totalBuyersettleGap
from base 
left join hs_same_settle_buyer on  base.hsId=hs_same_settle_buyer.hsId and deleted=0
group by orderId,hsId;

--1027 费用相关 1027->1034

--1027	代收代垫运费	dsddFee	【费用】	毛利表	汇总：费用科目 = “代收代垫运费”的费用金额
--1028	含税汽运费	hsqyFee	【费用】	毛利表	汇总：费用科目 = “含税汽运费”的费用金额
--1029	含税水运费	hssyFee	【费用】	毛利表	汇总：费用科目 = “含税水运费”的费用金额
--1030	含税火运费	hshyFee	【费用】	毛利表	汇总：费用科目 = “含税火运费”的费用金额
--1031	服务费	serviceFee	【费用】	毛利表	汇总：费用科目 = “服务费”的费用金额
--1032	监管费	superviseFee	【费用】	毛利表	汇总：费用科目 = “监管费”的费用金额
--1033	业务费	businessFee	【费用】	毛利表	汇总：费用科目 = “业务费”的费用金额
--1034	销售费用总额	salesFeeAmount	【1028】含税汽运费 + 【1029】含税水运费 + 【1030】含税火运费 + 【1031】监管费 + 【1032】管理费 + 【1033】业务费
--  forCapitalPressure  【费用】对方单位 = “上游供应商ID”的费用金额
create view v_1027 as
select
base.orderId,
base.hsId,
hs_same_fee.otherPartyId,
ROUND(sum(case when name='HELP_RECIVE_PAY_FEE' then IFNULL(amount ,0.00)else 0 end),2) as dsddFee,
ROUND(sum(case when name='TAX_MOTRO_FREIGHT' then  IFNULL(amount ,0.00) else 0 end) ,2)as hsqyFee,
ROUND(sum(case when name='TAX_SHIP_FREIGHT' then  IFNULL(amount ,0.00) else 0 end) ,2)as hssyFee,
ROUND(sum(case when name='TAX_RAIL_FREIGHT' then  IFNULL(amount ,0.00)else 0 end) ,2)as hshyFee,
ROUND(sum(case when name='SERVICE_FEE' then  IFNULL(amount ,0.00) else 0 end) ,2)as serviceFee,
ROUND(sum(case when name='SUPERVISE_FEE' then  IFNULL(amount ,0.00)  else 0 end),2) as superviseFee,
ROUND(sum(case when name='BUSINESS_FEE' then  IFNULL(amount ,0.00)  else 0 end) ,2)as businessFee,
ROUND(sum(case when name !='HELP_RECIVE_PAY_FEE' then  IFNULL(amount ,0.00)  else 0 end),2) as salesFeeAmount,
ROUND(sum(case when  hs_same_fee.otherPartyId=base.upstreamId then IFNULL(amount,0.00) else 0 end ),2)  as forCapitalPressure
from base
left join  hs_same_fee on  base.hsId=hs_same_fee.hsId and   deleted=0
group by orderId,hsId;

--发票相关
--1035 贸易公司已收到的进项数量 金额 --1035  1036

create view v_1035 as
select
invoice.orderId,
invoice.hsId,
ROUND(sum(IFNULL(detail.cargoAmount,0.00)) ,2)as tradingCompanyInTypeNum,
ROUND(sum(IFNULL(detail.priceAndTax,0.00)) ,2)as tradingCompanyInTpeMoneyAmount
from hs_same_invoice_detail detail
     inner join hs_same_invoice invoice on detail.invoiceId= invoice.id
     inner join hs_same_order orders on invoice.orderId=orders.id
     inner join hs_same_order_party party on party.orderId=orders.id and party.custType='TRAFFICKCER'and invoice.receiverId=party.customerId
where detail.deleted =0 and invoice.invoiceDirection='INCOME' and invoice.openCompanyId=orders.upstreamId
group by  orderId,hsId;


--1037 ccs一手到进项数量
--1037Css已收到进项数量   1038Css已收到进项金额
create view v_1037 as
select
invoice.orderId,
invoice.hsId,
ROUND(sum(IFNULL(detail.cargoAmount,0.00)) ,2)as totalCSSIntypeNumber,
ROUND(sum(IFNULL(detail.priceAndTax,0.00)),2) as totalCCSIntypeMoney
from hs_same_invoice_detail detail
     inner join hs_same_invoice invoice on detail.invoiceId= invoice.id
     inner join hs_same_order orders on invoice.orderId=orders.id
where detail.deleted =0 and invoice.invoiceDirection='INCOME' and invoice.receiverId=orders.mainAccounting
group by orderId,hsId;


--  invoicedMoneyAmount 1039占压表已开票金额
create view v_1039 as
select
invoice.orderId,
invoice.hsId,
sum(
case when  invoice.invoiceDirection='INCOME' 
then detail.cargoAmount else 0 end 
) as invoicedMoneyNum,
sum(
case when  invoice.invoiceDirection='INCOME' 
then detail.priceAndTax else 0 end 

) as invoicedMoneyAmount,

sum(detail.priceAndTax) as forunInvoicedAmount
from hs_same_invoice_detail detail
     inner join hs_same_invoice invoice on detail.invoiceId= invoice.id
     inner join hs_same_order orders on invoice.orderId=orders.id
where detail.deleted =0 and   invoice.openCompanyId=orders.upstreamId
group by  orderId,hsId;
-- select
-- invoice.orderId,
-- invoice.hsId,
-- ROUND(sum(IFNULL(detail.cargoAmount,0.00)),2) as invoicedMoneyNum,
-- ROUND(sum(IFNULL(detail.priceAndTax,0.00)) ,2)as invoicedMoneyAmount
-- from hs_same_invoice_detail detail
--      inner join hs_same_invoice invoice on detail.invoiceId= invoice.id
--      inner join hs_same_order orders on invoice.orderId=orders.id
-- where detail.deleted =0 and invoice.invoiceDirection='INCOME' and invoice.openCompanyId=orders.upstreamId
-- group by  orderId,hsId;


--v_2001 已到场数量  未到场数量  发运总数量
create view  v_2001 as
select
base.orderId,
base.hsId,
ROUND(sum(case when  arriveStatus = 'ARRIVE' then IFNULL(fyamount ,0.00)else 0 end ) ,2)as  totalArriveNum,
ROUND(sum(case when  arriveStatus=  'UNARRIVE' then IFNULL(fyamount ,0.00) else 0 end ),2) as  totalUnarriveNum ,
ROUND(sum(IFNULL(fyamount,0.00)) ,2)as totalFayunNum
from base
left join  hsdb.hs_ying_fayun  fayun on base.hsId =fayun.hsId  and deleted=0
group by OrderId,hsId;

--2004  保证金相关
--2004收上游保证金	totalUpstreamBail	【保证金】		汇总： 保证金类型 = “收上游保证金”的保证金金额
--2005退上游保证金	totalRefundUpBail	【保证金】		汇总： 保证金类型 = “退上游保证金”的保证金金额
--2006付下游保证金	totalPayDownBail	【保证金】		汇总： 保证金类型 = “付下游保证金”的保证金金额
--2007下游退保证金	totalRefundDownBail	【保证金】		汇总： 保证金类型 = “下游退保证金”的保证金金额

create view v_2004 as
select
base.orderId,
base.hsId,
ROUND(sum(case when bailType='RECV_UP' then IFNULL(bailAmount ,0.00) else 0 end) ,2)as totalUpstreamBail,
ROUND(sum(case when bailType='BACK_UP' then IFNULL(bailAmount ,0.00) else 0 end) ,2)as totalRefundUpBail,
ROUND(sum(case when bailType='PAY_DOWN' then IFNULL(bailAmount ,0.00) else 0 end) ,2)as totalPayDownBail,
ROUND(sum(case when bailType='BACK_DOWN' then IFNULL(bailAmount ,0.00) else 0 end),2) as totalRefundDownBail
from base
left join  hs_ying_bail on base.hsId=hs_ying_bail.hsId and  deleted=0
group by OrderId,hsId;


--2008	上游保证金余额	balanceUpstreamBail	计算		【2004】收上游保证金 - 【2005】退上游保证金
--2009	下游保证金余额	balanceDownStreamBail	计算		【2006】付下游保证金 - 【2007】下游退保证金
create view v_2008 as
select
base.orderId,
base.hsId,
ROUND(IFNULL(totalUpstreamBail,0.00)-IFNULL(totalRefundUpBail ,0.00),0.00) as balanceUpstreamBail,
ROUND(IFNULL(totalPayDownBail,0.00)-IFNULL(totalRefundDownBail ,0.00),0.00)as balanceDownStreamBail
from base
left join  v_2004 on base.hsId=v_2004.hsId;


-- 3014  不含税费用     【1028】含税汽运费/1.11+【1029】含税水运费/1.11+【1030】含税火运费/1.11+【1031】监管费/1.06+【1032】服务费/1.06      
create view v_3014  as
select
base.orderId,
base.hsId,
ROUND((
IFNULL(v_1027.hsqyFee,0.00)+IFNULL(v_1027.hssyFee,0.00)+IFNULL(v_1027.hshyFee,0.00))/1.11
+(IFNULL(v_1027.superviseFee,0.00)+IFNULL(v_1027.serviceFee,0.0))/1.06
,2)
as withoutTaxFee
from base
left join v_1027  on base.hsId=v_1027.hsId ;

--出库入库相关
--3001	入库总数量	totalInstorageNum	【入库】	备查账	汇总：入库数量
--3002	入库总金额	totalInstorageAmount	【入库】		汇总：入库金额
--3004	入库单价	instorageUnitPrice	计算		【3002】入库总金额 ／ 【3001】入库总数量
--3009   在途库存数量 totalInstorageTranitNum
-- 3011  已入库库存数量 totalInstoragedNum  占压表 汇总：入库状态 = “已入库”的入库数量

create view v_3001  as
select
base.orderId,
base.hsId,
sum(IFNULL(rukuAmount,0.00)) as totalInstorageNum,
sum( IFNULL(rukuPrice,0.00)) as totalInstorageAmount,
IFNULL(sum(IFNULL(rukuPrice,0.00))/sum(IFNULL(rukuAmount,0.00)),0.00) as instorageUnitPrice,
sum(IFNULL(case when rukuStatus='IN_TRANIT' then rukuAmount else 0 end,0.00))  totalInstorageTranitNum,
sum(IFNULL(case when rukuStatus='IN_TRANIT' then rukuPrice else 0 end ,0.00))  totalInstorageTranitPrice,
sum(IFNULL(case when rukuStatus='IN_STORAGE' then rukuAmount else 0 end,0.00))  totalInstoragedNum,
sum(IFNULL(case when rukuStatus='IN_STORAGE' then rukuPrice else 0 end,0.00))  totalInstoragedNumMoney

from base
left join hs_cang_ruku ruku on base.hsId=ruku.hsId and  deleted =0
group by orderId,hsId;

--3003	已出库数量	totalOutstorageNum	【出库】	备查账	汇总：出库数量
--3007  已出库金额	totalOutstorageMoney	【出库】		汇总:出库金额


create view v_3003 as
select
base.orderId,
base.hsId,
ROUND(sum(IFNULL(chukuAmount,0.00)) ,2)as totalOutstorageNum,
ROUND(sum(IFNULL(chukuPrice,0.00)),2)as totalOutstorageMoney
from base
left join hs_cang_chuku chuku on base.hsId=chuku.hsId and deleted =0
group by orderId,hsId;


--3005	库存数量	totalStockNum	计算	备查账/占压	【3001】入库总数量 - 【3003】已出库数量
--3011  inOutRate  已入库库存数量-【3003】已出库数量
create view v_3005  as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_3001.totalInstorageNum,0.00)-IFNULL(v_3003.totalOutstorageNum ,0.00),2)as totalStockNum,
case when IFNULL(v_3001.totalInstorageNum,0.00)=0.00
then  0.00 else IFNULL(v_3003.totalOutstorageNum ,0.00)/IFNULL(v_3001.totalInstorageNum,0.00) end  as inOutRate
from  base 
left  join v_3001 on base.hsId=v_3001.hsId
left join v_3003 on base.hsId=v_3003.hsId;



--3006	库存金额	totalStockMoney	  【3004】入库单价 * 【3005】库存数量 / 1.17 +【3005】库存数量 / 【3001】入库总数量*【3014】不含税费用
create view v_3006  as
select
base.orderId,
base.hsId,
ROUND(
IFNULL(v_3001.instorageUnitPrice ,0.00)
*IFNULL(v_3005.totalStockNum ,0.00)
/1.17,2)
+
ROUND(case when v_3001.totalInstorageNum !=0  then IFNULL(v_3005.totalStockNum ,0.00)/v_3001.totalInstorageNum * v_3014.withoutTaxFee
else 0.00 end ,2)
as totalStockMoney
from  base
left join v_3001 on base.hsId=v_3001.hsId
left join v_3014 on base.hsId=v_3014.hsId
left join v_3005 on v_3005.hsId=base.hsId;

-- 3010  在途库存金额  【入库】  占压表 【3009】*【3004】入库单价 / 1.17 + 【3009】在途库存数量 / 【3001】入库总数量*【3014】不含税费用     
create view v_3010 as
select 
base.orderId,
base.hsId,
ROUND(

IFNULL(v_3001.totalInstorageTranitNum,0.00)*
IFNULL(v_3001.instorageUnitPrice,0.00) /1.17
+
case when 
v_3001.totalInstorageNum !=0 
then
 IFNULL(v_3001.totalInstorageTranitNum,0.00)/
 IFNULL(v_3001.totalInstorageNum,0.00) *IFNULL(v_3014.withoutTaxFee,0) 
else 0.00 end 
,2)
as totalInstorageTranitPrice



from base
left join v_3001 on base.hsId=v_3001.hsId
left join v_3014 on base.hsId=v_3014.hsId;



create view v_3012 as
select 
base.orderId,
base.hsId,
IFNULL(v_3001.totalInstoragedNum,0.00)-IFNULL(v_3003.totalOutstorageNum,0.00) as totalInstorageRemainNum
from base
left join v_3001 on base.hsId=v_3001.hsId
left join v_3003 on base.hsId=v_3003.hsId;


-- 3013  剩余库存金额  计算  占压表 【3012】剩余库存数量*【3004】入库单价 / 1.17 +【3012】剩余库存数量 / 【3001】入库总数量*【3014】不含税费用      
create view v_3013 as
select 
base.orderId,
base.hsId,

IFNULL(v_3012.totalInstorageRemainNum,0.00)*IFNULL(v_3001.instorageUnitPrice,0.00) /1.17
+IFNULL(v_3012.totalInstorageRemainNum,0.00)/IFNULL(v_3001.totalInstorageNum,0.00) *v_3014.withoutTaxFee


as totalInstorageRemainPrice
from base
left join v_3012 on base.hsId=v_3012.hsId
left join v_3001 on base.hsId=v_3001.hsId
left join v_3014 on base.hsId=v_3014.hsId;





--应收
--1040 核算结算量  finalSettleAmount
--1041	贸易公司加价	tradingCompanyAddMoney
--1042	买方未结算数量	unsettlerBuyerNumber
--1043	买方未结算金额	unsettlerBuyerMoneyAmount
--1044	销售货款总额	saleCargoAmountofMoney
create view v_1041_cang as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_3001.totalInstorageNum ,0.00),2)as finalSettleAmount,
ROUND(IFNULL(v_3001.totalInstorageNum ,0.00) * IFNULL(config.tradeAddPrice ,0.00),2)as tradingCompanyAddMoney,
ROUND(IFNULL(v_3001.totalInstorageNum ,0.00)-IFNULL(v_1024.totalBuyerNums ,0.00),2) as  unsettlerBuyerNumber,
ROUND( ROUND(IFNULL(v_3001.totalInstorageNum ,0.00)-IFNULL(v_1024.totalBuyerNums ,0.00),2)* IFNULL(v_3001.instorageUnitPrice,0.00),2)  as unsettlerBuyerMoneyAmount
from base
left join  v_3001 on base.hsId =v_3001.hsId
left join v_3003 on base.hsId=v_3003.hsId
left join v_1013 on base.hsId=v_1013.hsId
left join v_1024 on base.hsId=v_1024.hsId
left join hs_same_order_config config on config.id=base.hsId;





--应收
create view v_1041_ying as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_2001.totalFayunNum,0.00)-IFNULL(v_1024.totalBuyersettleGap,0.00) ,2)as finalSettleAmount,
ROUND(IFNULL(v_2001.totalFayunNum,0.00)-IFNULL(v_1024.totalBuyersettleGap,0.00) -IFNULL(v_1024.totalBuyerNums,0.00),2) as  unsettlerBuyerNumber,
IFNULL(ROUND((IFNULL(v_2001.totalFayunNum,0.00)-IFNULL(v_1024.totalBuyersettleGap,0.00) ) * config.tradeAddPrice ,2),0.00)as tradingCompanyAddMoney,
ROUND((IFNULL(v_2001.totalFayunNum,0.00)-IFNULL(v_1024.totalBuyersettleGap,0.00) -IFNULL(v_1024.totalBuyerNums,0.00) ) * IFNULL(config.weightedPrice ,0.00),2) as unsettlerBuyerMoneyAmount,
ROUND((IFNULL(v_2001.totalFayunNum,0.00)-IFNULL(v_1024.totalBuyersettleGap,0.00) -IFNULL(v_1024.totalBuyerNums,0.00) ) * IFNULL(config.weightedPrice ,0.00)+IFNULL(v_1024.totalBuyerMoney,0.00),2)as saleCargoAmountofMoney
from base 
     left join v_2001 on base.hsId=v_2001.hsId
     left join v_1024 on base.hsId=v_1024.hsId
     left join hs_same_order_config config on config.id=base.hsId;




--1045 瑞茂通总收益  【1021】合同利息收益 - 【1012】外部资金使用成本 + 【1023】贴现息合计
create view v_1045 as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1021.contractRateProfile,0.00)-IFNULL(v_1012.outCapitalAmout,0.00)+IFNULL(v_1023.tiexianRateAmount ,0.00),2)as ccsProfile
from base
left join  v_1021 on base.hsId=v_1021.hsId
left join v_1012 on v_1021.hsId=v_1012.hsId
left join v_1023 on v_1021.hsId=v_1023.hsId;





create view v_1068 as
select 
base.orderId,
base.hsId,
ROUND(IFNULL((v_1027.hsqyFee+v_1027.hssyFee+v_1027.hshyFee)*1.06+(v_1027.superviseFee+v_1027.serviceFee)*1.12 ,0.00) ,2)as feeAndAddTax
from base 
left join  v_1027 on base.hsId=v_1027.hsId;




--1046 采购货款总额 【1044】销售货款总额 - 【1045】瑞茂通总收益 -【1068】费用及补税
create view v_1046_ying as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1041_ying.saleCargoAmountofMoney,0.00)-IFNULL(v_1045.ccsProfile ,0.00),2)- IFNULL(v_1068.feeAndAddTax,0.00) as purchaseCargoAmountOfMoney
from base
left join v_1041_ying on  base.hsId=v_1041_ying.hsId
left join v_1045 on base.hsId=v_1045.hsId
left join v_1068 on base.hsId=v_1068.hsId;


create view v_1046_cang as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_3001.totalInstorageAmount,0.00),2)as purchaseCargoAmountofMoney
from base 
left join v_3001 on base.hsId =v_3001.hsId;


-- IF（【3002】入库总金额>0?【1046】采购货款总额 + 【1045】瑞茂通总收益+【1068】费用及补税 : 0）
create view v_1044_cang as
select
base.orderId,
base.hsId,
case when  v_3001.totalInstorageAmount>0
then v_1046_cang.purchaseCargoAmountOfMoney+v_1045.ccsProfile +v_1068.feeAndAddTax
else  0.00
end
as saleCargoAmountofMoney
from base
     left join v_1045      on base.hsId=v_1045.hsId
     left join v_1046_cang on base.hsId=v_1046_cang.hsId
     left join v_1068 on base.hsId=v_1068.hsId
     left join v_3001 on base.hsId=v_3001.hsId;






--1047 外部资金使用率  【1004】借款金额合计 - 【1007】还款本金合计  + 【1009】登记未还款本金
create view v_1047 as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1004.totalLoadMoney,0.00)-IFNULL(v_1007.totalRepaymentPrincipeAmount,0.00)+IFNULL(v_1009.totalUnpayPrincipal ,0.00),2)as externalCapitalPaymentAmount
from base
     left join v_1004 on base.hsId=v_1004.hsId
     left join v_1007 on base.hsId=v_1007.hsId
     left join v_1009 on base.hsId=v_1009.hsId;

-- 1077  CCS付外部资金成本      【1008】还款利息合计 + 汇总：客户/ccs还服务费= “ccs还服务费”的服务费  -【1010】登记未还款利息 -汇总：客户/ccs还服务费= “ccs还服务费”且还款状态=“未还款”的服务费  0     
create view v_1077 as
select 
base.orderId,
base.hsId,
v_1007.totalRepaymentInterest  + v_1007.totalccsPayServiceCharge 
- v_1009.totalUnpayInterest
- v_1007.totalccsPayUnpromiseServiceCharge  as  ccsExternalCapitalCost
from  base
left join v_1007 on base.hsId=v_1007.hsId
left join v_1009 on base.hsId=v_1009.hsId;

--1048 ownerCapitalPaymentAmount 自由资金付款金额【1003】付款金额合计 - 【1047】外部资金付款金额 + 【1077】CCS付外部资金成本 + 【1008】还款利息合计（——ccs支付服务费）  -【1010】登记未还款利息 - 【2008】上游保证金余额-【1002】付贸易差价金额
create view v_1048_ying as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1001.totalPaymentAmount,0.00)+
IFNULL(v_1077.ccsExternalCapitalCost,0.00)
-IFNULL(v_1047.externalCapitalPaymentAmount ,0.00)
-IFNULL(v_1001.totalTradeGapFee,0.00),2)
as ownerCapitalPaymentAmount
from base
     left join v_1001 on base.hsId=v_1001.hsId
     left join v_1047 on base.hsId=v_1047.hsId 
     left join v_1077 on base.hsId=v_1077.hsId;


--1048【1003】付款金额合计  + 【1077】CCS付外部资金成本 - 【1047】外部资金付款金额-1002付贸易逆差
create view v_1048_cang as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1001.totalPaymentAmount,0.00)
-IFNULL(v_1047.externalCapitalPaymentAmount ,0.00)
-IFNULL(v_1001.totalTradeGapFee,0.00),2)
as ownerCapitalPaymentAmount
from base
     left join v_1001 on base.hsId=v_1001.hsId
     left join v_1047 on base.hsId=v_1047.hsId ;


--1049  【付款】汇总收款单位 = “上游供应商ID”的付款金额  +【1077】CCS付外部资金成本 - 【1046】采购货款总额 - 【费用】对方单位 = “上游供应商ID”的费用金额  - 【2008】上游保证金余额
create view v_1049_ying as
select
base.orderId,
base.hsId,
IFNULL(v_1001.forCapitalPressure,0.00)+
IFNULL(v_1077.ccsExternalCapitalCost,0.00)-
IFNULL(v_1046_ying.purchaseCargoAmountOfMoney,0.00)-
IFNULL(v_1027.forCapitalPressure,0.00)-
IFNULL(v_2008.balanceUpstreamBail,0.00)
as upstreamCapitalPressure
from base 
left join v_1046_ying on base.hsId= v_1046_ying.hsId
left join v_1027  on base.hsId=v_1027.hsId
left join v_1001  on base.hsId=v_1001.hsId 
left join v_2008  on base.hsId=v_2008.hsId 
left join v_1077 on base.hsId=v_1077.hsId;


-- 【付款】汇总收款单位 = “上游供应商ID”的付款金额  +【1077】CCS付外部资金成本 - 【1046】采购货款总额 - 【费用】对方单位 = “上游供应商ID”的费用金额
create view v_1049_cang as
select
base.orderId,
base.hsId,
IFNULL(v_1001.forCapitalPressure,0.00) -
IFNULL(v_1046_cang.purchaseCargoAmountOfMoney,0.00)- 
IFNULL(v_1027.forCapitalPressure,0.00)
as upstreamCapitalPressure
from base 
left join v_1046_cang on base.hsId= v_1046_cang.hsId
left join v_1027  on base.hsId=v_1027.hsId
left join v_1001  on base.hsId=v_1001.hsId;


--1050 下游资金占压	downstreamCapitalPressure	计算	备查账／占压表	【1044】销售货款总额 - 【1013】已回款金额 +【2009】下游保证金余额

create view v_1050_ying as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1041_ying.saleCargoAmountofMoney,0.00)-IFNULL(v_1013.totalHuikuanPaymentMoney,0.00)+IFNULL(v_2008.balanceDownStreamBail ,0.00),2)as downstreamCapitalPressure
from base 
     left join v_1041_ying on base.hsId =v_1041_ying.hsId
     left join v_1013 on v_1041_ying.hsId=v_1013.hsId
     left join v_2008 on v_1041_ying.hsId=v_2008.hsId;

--【1044】销售货款总额 - 【1013】已回款金额
create view v_1050_cang as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_3003.totalOutstorageMoney,0.00)-IFNULL(v_1013.totalHuikuanPaymentMoney ,0.00),2)
as downstreamCapitalPressure
from base
     left join v_3003 on base.hsId =v_3003.hsId
     left join v_1013 on base.hsId= v_1013.hsId;
 


--1051 CCS未收到进项数量	cssUninTypeNum	计算	备查账	【1040】核算结算量 - 【1037】CCS已收进项数量

create view v_1051_ying as
select
base.orderId,
base.hsId,
ROUND(IFNULL(IFNULL(v_1041_ying.finalSettleAmount,0.00)-IFNULL(v_1037.totalCSSIntypeNumber ,0.00),0.00),2)as cssUninTypeNum
from base
     left join v_1041_ying on base.hsId=v_1041_ying.hsId
     left join v_1037 on v_1037.hsId=v_1041_ying.hsId;

create view v_1051_cang as
select
base.orderId,
base.hsId,
ROUND(IFNULL(IFNULL(v_1041_cang.finalSettleAmount,0.00)-IFNULL(v_1037.totalCSSIntypeNumber ,0.00),0.00),2)as cssUninTypeNum
from base
     left join v_1041_cang on base.hsId=v_1041_cang.hsId
     left join v_1037 on v_1037.hsId=v_1041_cang.hsId;

--1052 CCS未收到进项金额	cssUninTypeMoney	     	【1046】采购货款总额 + 【1041】贸易公司加价 + 【1034】销售费用总额 - 【1038】CCS已收进项金额 - 【1027】代收代垫运费
--1053	占压表未开票金额	unInvoicedAmountofMoney		【1046】采购货款总额 - 【1039】占压表已开票金额 - 【1027】代收代垫运费 
create view v_1052_ying  as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1046_ying.purchaseCargoAmountofMoney,0.00)+IFNULL(v_1027.salesFeeAmount,0.00)+IFNULL(v_1041_ying.tradingCompanyAddMoney,0.00)-IFNULL(v_1037.totalCCSIntypeMoney,0.00)-IFNULL(v_1027.dsddFee ,0.00),2)as cssUninTypeMoney,
ROUND(IFNULL(v_1046_ying.purchaseCargoAmountofMoney,0.00)-IFNULL(v_1039.forunInvoicedAmount,0.00)-IFNULL(v_1027.forCapitalPressure ,0.00),2) as unInvoicedAmountofMoney
from base
     left join v_1046_ying on base.hsId =v_1046_ying.hsId
     left join v_1041_ying on base.hsId=v_1041_ying.hsId
     left join v_1037 on v_1037.hsId=base.hsId
     left join v_1027 on v_1027.hsId=base.hsId
     left join v_1039 on v_1039.hsId=base.hsId;



create view v_1052_cang  as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1046_cang.purchaseCargoAmountofMoney,0.00)+IFNULL(v_1027.salesFeeAmount,0.00)+IFNULL(v_1041_cang.tradingCompanyAddMoney,0.00)-IFNULL(v_1037.totalCCSIntypeMoney,0.00)-IFNULL(v_1027.dsddFee ,0.00),2)as cssUninTypeMoney,
ROUND(IFNULL(v_1046_cang.purchaseCargoAmountofMoney,0.00)-IFNULL(v_1039.forunInvoicedAmount,0.00)-IFNULL(v_1027.forCapitalPressure ,0.00),2) as unInvoicedAmountofMoney
from base
     left join v_1046_cang on base.hsId =v_1046_cang.hsId
     left join v_1041_cang on base.hsId=v_1041_cang.hsId
     left join v_1037 on v_1037.hsId=base.hsId
     left join v_1027 on v_1027.hsId=base.hsId
     left join v_1039 on v_1039.hsId=base.hsId;


--1054	预收款	yingPrePayment	计算	占压表	IF（【1013】已回款金额>=【1025】买方已结算金额 ）？【1013】已回款金额 -【1025】买方已结算金额-【2009】下游保证金余额：0 -【2009】下游保证金余额
--1054	预收款	cangPrePayment	计算	占压表	IF（【1014】付货款金额 <= 【1013】已回款金额？【1014】付货款金额 - 【1013】已回款金额 ：0 ）
create view v_1054_ying  as
select
base.orderId,
base.hsId,
case when IFNULL(v_1013.totalHuikuanPaymentMoney ,0.00)>=IFNULL(v_1024.totalBuyerMoney,0.00)
then ROUND(IFNULL(v_1013.totalHuikuanPaymentMoney ,0.00)-IFNULL(v_1024.totalBuyerMoney,0.00)- IFNULL(v_2008.balanceDownStreamBail,0.00),2) else - ROUND(IFNULL(v_2008.balanceDownStreamBail,0.00),2) end as yingPrePayment
from base 
     left join v_1013 on base.hsId=v_1013.hsId
     left join v_1024 on v_1013.hsId=v_1024.hsId
     left join v_2008 on v_1013.hsId=v_2008.hsId;



-- IF（【1013】已回款金额>【3007】已出库金额？ 【1013】已回款金额 -【3007】已出库金额：0 ）
create view v_1054_cang  as
select
base.orderId,
base.hsId,
case when IFNULL(v_1013.totalHuikuanPaymentMoney ,0.00)>=IFNULL(v_3003.totalOutstorageMoney ,0.00)
then
ROUND(IFNULL(v_1013.totalHuikuanPaymentMoney ,0.00)-IFNULL(v_3003.totalOutstorageMoney ,0.00),2)
else 0.00 end
as cangPrePayment
from base
     left join v_1013 on base.hsId=v_1013.hsId
     left join v_3003 on v_1013.hsId=v_3003.hsId;





--1055 毛利结算量	settleGrossProfileNum	计算	毛利表	【1040】核算用结算量
--仓押毛利结算量 【3003】已出库数量
create view v_1055 as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1041_ying.finalSettleAmount ,0.00),2)as settleGrossProfileNum
from  base
left join  v_1041_ying on base.hsId=v_1041_ying.hsId;


--1056 yingshou采购含税总额	purchaseIncludeTaxTotalAmount	计算	毛利表	【1046】采购货款总额
-- 仓押采购含税总额   purchaseIncludeTaxTotalAmount【3004】入库单价 * 【1055】毛利结算量
create view v_1056_cang  as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_3001.instorageUnitPrice ,0.00)* IFNULL(v_3003.totalOutstorageNum  ,0.00),2)as purchaseIncludeTaxTotalAmount
from  base
left join v_3001 on base.hsId=v_3001.hsId
left join v_3003 on  v_3001.hsId=v_3003.hsId;


--1057	仓押销售含税总额	saleIncludeTaxTotalAmount	【1056】采购含税总额 + 【1045】瑞茂通总收益*【3008】出库入库比+【1068】费用及补税*【3008】出库入库比
--1057  应收 【1044】销售货款总额
create view v_1057_cang  as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1056_cang.purchaseIncludeTaxTotalAmount ,0.00)+ 
  IFNULL(v_1045.ccsProfile ,0.00)*v_3005.inOutRate +
  IFNULL(v_1068.feeAndAddTax,0.00)*v_3005.inOutRate,2)
as saleIncludeTaxTotalAmount
from base
     left join v_1056_cang on base.hsId=v_1056_cang.hsId
      left join v_1045 on  v_1056_cang.hsId=v_1045.hsId
      left join v_3005 on  v_3005.hsId=base.hsId
      left join v_1068 on  base.hsId=v_1068.hsId;

--1058	毛利贸易公司加价	tradeCompanyAddMoney	计算	毛利表	【1055】毛利结算量 * 【核算月配置】贸易公司加价
create view v_1058_ying  as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1055.settleGrossProfileNum ,0.00)* IFNULL(config.tradeAddPrice  ,0.00),2)as tradeCompanyAddMoney
from base
     left join v_1055 on base.hsId =v_1055.hsId
     left join hs_same_order_config config on  v_1055.hsId=config.id;

create view v_1058_cang  as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_3003.totalOutstorageNum ,0.00)* IFNULL(config.tradeAddPrice  ,0.00),2)as tradeCompanyAddMoney
from base
     left join v_3003 on base.hsId=v_3003.hsId
     left join hs_same_order_config config on  v_3003.hsId=config.id;


--1059	不含税收入	withoutTaxIncome	计算	毛利表	（【1057】销售含税总额 - 【1027】代收代垫运费 ）／ 1.17
create view v_1059_ying  as
select
base.orderId,
base.hsId,
ROUND((IFNULL(v_1041_ying.saleCargoAmountofMoney ,0.00)- IFNULL(v_1027.dsddFee,0.00))/1.17 ,2) as withoutTaxIncome
from  base
     left join v_1041_ying on base.hsId=v_1041_ying.hsId
     left join v_1027  on  v_1027.hsId=v_1041_ying.hsId
group by hsId, orderId;


create view v_1059_cang  as
select
base.orderId,
base.hsId,
ROUND((IFNULL(v_1057_cang.saleIncludeTaxTotalAmount ,0.00)- IFNULL(v_1027.dsddFee,0.00))/1.17 ,2) as withoutTaxIncome
from base 
     left join v_1057_cang on base.hsId=v_1057_cang.hsId
     left join v_1027  on  v_1027.hsId=v_1057_cang.hsId;


--1060	不含税成本	withoutTaxCost	计算	毛利表	（【1056】采购含税总额 - 【1027】代收代垫运费 + 【1058】毛利贸易公司加价）／1.17
create view v_1060_cang  as
select
base.orderId,
base.hsId,
ROUND((IFNULL(v_1056_cang.purchaseIncludeTaxTotalAmount ,0.00)- IFNULL(v_1027.dsddFee,0.00)+IFNULL(v_1058_cang.tradeCompanyAddMoney,0.00))/1.17  ,2)as withoutTaxCost
from base
     left join v_1056_cang on base.hsId=v_1056_cang.hsId
     left join v_1027  on  v_1027.hsId=v_1056_cang.hsId
     left join v_1058_cang  on  v_1056_cang.hsId=v_1058_cang.hsId;


create view v_1060_ying  as
select
base.orderId,
base.hsId,
ROUND((IFNULL(v_1046_ying.purchaseCargoAmountofMoney ,0.00)-IFNULL( v_1027.dsddFee,0.00)+IFNULL(v_1058_ying.tradeCompanyAddMoney,0.00))/1.17  ,2)as withoutTaxCost
from base
     left join v_1046_ying on base.hsId=v_1046_ying.hsId
     left join v_1027  on  v_1027.hsId=v_1046_ying.hsId
     left join v_1058_ying  on  v_1046_ying.hsId=v_1058_ying.hsId;


--1061	应交增值税	vat	计算	毛利表	IF（【1059】不含税收入 <= 【1060】不含税成本？0 : （【1059】不含税收入 - 【1060】不含税成本）*0.17）
--1062	税金及附加	additionalTax	计算	毛利表	【1061】应交增值税 * 0.12
create view v_1061_cang  as
select
base.orderId,
base.hsId,
ROUND(IFNULL(
case when IFNULL(v_1059_cang.withoutTaxIncome ,0.00)<= IFNULL(v_1060_cang.withoutTaxCost ,0.00)then 0 else (IFNULL(v_1059_cang.withoutTaxIncome ,0.00)-IFNULL(v_1060_cang.withoutTaxCost,0.00))*0.17 end,
0.00) ,2)as vat,
ROUND(IFNULL(
case when IFNULL(v_1059_cang.withoutTaxIncome ,0.00)<= IFNULL(v_1060_cang.withoutTaxCost ,0.00)then 0 else (IFNULL(v_1059_cang.withoutTaxIncome ,0.00)-IFNULL(v_1060_cang.withoutTaxCost,0.00))*0.17*0.12 end,
0.00) ,2)as AdditionalTax
from base  
     left join v_1059_cang  on base.hsId=v_1059_cang.hsId
     left join v_1060_cang  on  v_1060_cang.hsId = v_1059_cang.hsId;


create view v_1061_ying  as
select
base.orderId,
base.hsId,
ROUND(IFNULL(
case when IFNULL(v_1059_ying.withoutTaxIncome ,0.00)<= IFNULL(v_1060_ying.withoutTaxCost ,0.00)then 0 else (IFNULL(v_1059_ying.withoutTaxIncome,0.00) -IFNULL(v_1060_ying.withoutTaxCost,0.00))*0.17 end,
0.00),2) as vat,
ROUND(IFNULL(
case when IFNULL(v_1059_ying.withoutTaxIncome ,0.00)<= IFNULL(v_1060_ying.withoutTaxCost ,0.00)then 0 else (IFNULL(v_1059_ying.withoutTaxIncome ,0.00)-IFNULL(v_1060_ying.withoutTaxCost,0.00))*0.17*0.12 end,
0.00),2)as additionalTax
from base 
     left join v_1059_ying  on base.hsId=v_1059_ying.hsId
     left join v_1060_ying  on  v_1060_ying.hsId = v_1059_ying.hsId;



--1063	印花税	stampDuty	计算	毛利表	（【1057】销售含税总额 + 【1056】采购含税总额 + 【1058】毛利贸易公司加价 - 【1027】代收代垫运费 * 2 ） * 0.0003
create view v_1063_cang  as
select
base.orderId,
base.hsId,
ROUND((IFNULL(v_1057_cang.saleIncludeTaxTotalAmount ,0.00)+IFNULL(v_1056_cang.purchaseIncludeTaxTotalAmount,0.00)+IFNULL(v_1058_cang.tradeCompanyAddMoney,0.00)- IFNULL(v_1027.dsddFee,0.00)*2)*0.0003 ,2) as stampDuty
from base
     left join v_1057_cang  on base.hsId=v_1057_cang.hsId
     left join v_1027  on  v_1027.hsId=v_1057_cang.hsId
     left join v_1056_cang on  v_1056_cang.hsId=v_1057_cang.hsId
     left join v_1058_cang  on  v_1058_cang.hsId=v_1057_cang.hsId;



create view v_1063_ying  as
select
base.orderId,
base.hsId,
ROUND((IFNULL(v_1041_ying.saleCargoAmountofMoney ,0.00)+IFNULL(v_1046_ying.purchaseCargoAmountofMoney,0.00)+IFNULL(v_1058_ying.tradeCompanyAddMoney,0.00)- IFNULL(v_1027.dsddFee,0.00)*2)*0.0003 ,2) as stampDuty
from base
     left join v_1041_ying on  base.hsId=v_1041_ying.hsId
     left join v_1027  on  v_1027.hsId=v_1041_ying.hsId
     left join v_1046_ying on  v_1046_ying.hsId=v_1041_ying.hsId
     left join v_1058_ying  on  v_1058_ying.hsId=v_1041_ying.hsId;

--1064	经营毛利	opreationCrocsProfile	计算	毛利表	【1059】不含税收入 - 【1060】不含税成本 - 【1062】税金及附加 - 【1063】印花税 -
--（【1028】含税汽运费 + 【1029】含税水运费 + 【1030】含税火运费）／1.11 - 【1031】监管费 ／1.06 - 【1031】服务费 ／1.06 - 【1033】业务费

create view v_1064_cang as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_1059_cang.withoutTaxIncome,0.00)-IFNULL(v_1060_cang.withoutTaxCost,0.00)-IFNULL(v_1061_cang.AdditionalTax,0.00)-IFNULL(v_1063_cang.stampDuty
,0.00)-(IFNULL(v_1027.hsqyFee,0.00)+IFNULL(v_1027.hssyFee,0.00)+IFNULL(v_1027.hshyFee,0.00))/1.11-IFNULL(v_1027.superviseFee,0.00)/1.06-IFNULL(v_1027.serviceFee,0.00)/1.06-IFNULL(v_1027.businessFee,0.00),2) as opreationCrossProfile
from base
left join v_1059_cang on  base.hsId=v_1059_cang.hsId
left join v_1060_cang on v_1059_cang.hsId=v_1060_cang.hsId
left join v_1061_cang  on v_1059_cang.hsId=v_1061_cang.hsId
left join v_1063_cang  on v_1059_cang.hsId=v_1063_cang.hsId
left join v_1027  on v_1059_cang.hsId=v_1027.hsId;



create view v_1064_ying as
select
v_1059_ying.orderId,
v_1059_ying.hsId,
ROUND(IFNULL(v_1059_ying.withoutTaxIncome,0.00)-IFNULL(v_1060_ying.withoutTaxCost,0.00)-IFNULL(v_1061_ying.additionalTax,0.00)-IFNULL(v_1063_ying.stampDuty
                                                                                                                               ,0.00)-(IFNULL(v_1027.hsqyFee,0.00)+IFNULL(v_1027.hssyFee,0.00)+IFNULL(v_1027.hshyFee,0.00))/1.11-IFNULL(v_1027.superviseFee,0.00)/1.06-IFNULL(v_1027.serviceFee,0.00)/1.06-IFNULL(v_1027.businessFee ,0.00),2)as opreationCrossProfile
from v_1059_ying
     left join v_1060_ying on v_1059_ying.hsId=v_1060_ying.hsId
     left join v_1061_ying  on v_1059_ying.hsId=v_1061_ying.hsId
     left join v_1063_ying  on v_1059_ying.hsId=v_1063_ying.hsId
     left join v_1027  on v_1059_ying.hsId=v_1027.hsId;


--1065	单吨毛利	crossProfileATon	计算	毛利表	【1064】经营毛利 / 【1055】毛利结算量

create view v_1065_ying as
select
base.orderId,
base.hsId,
IFNULL(ROUND(IFNULL(v_1064_ying.opreationCrossProfile ,0.00)/ IFNULL(v_1055.settleGrossProfileNum ,0.00),2),0.00)as crossProfileATon
from base
     left join v_1064_ying on base.hsId=v_1064_ying.hsId
     left join v_1055  on v_1064_ying.hsId=v_1055.hsId;

create view v_1065_cang as
select
base.orderId,
base.hsId,
IFNULL(ROUND(IFNULL(v_1064_cang.opreationCrossProfile,0.00) / IFNULL(v_3003.totalOutstorageNum ,0.00),2),0.00)as crossProfileATon
from base
     left join v_1064_cang on base.hsId=v_1064_cang.hsId
     left join v_3003  on v_1064_cang.hsId=v_3003.hsId;


--1066	自有资金占压	ownerCapitalPressure	【1049】资金占压（供应商）- 【1047】外部资金付款金额
create view v_1066_cang as
select
base.orderId,
base.hsId,
IFNULL(v_1049_cang.upstreamCapitalPressure,0.00)-
IFNULL(v_1047.externalCapitalPaymentAmount,0.00)
as ownerCapitalPressure
from  base
     left join v_1049_cang on base.hsId=v_1049_cang.hsId and base.orderId=v_1049_cang.orderId
     left join v_1047 on base.hsId=v_1047.hsId and base.orderId=v_1047.orderId;



create view v_1066_ying as
select
base.orderId,
base.hsId,
IFNULL(v_1049_ying.upstreamCapitalPressure,0.00)-
IFNULL(v_1047.externalCapitalPaymentAmount,0.00)
as ownerCapitalPressure
from  base
     left join v_1049_ying on base.hsId=v_1049_ying.hsId and base.orderId=v_1049_ying.orderId
     left join v_1047 on base.hsId=v_1047.hsId and base.orderId=v_1047.orderId;



--1067	下游已结算未回款金额	settledDownstreamHuikuanMoneny	计算	占压表	【1050】下游占压总额-【1043】买方未结算金额+【1054】预收款
-- IF（汇总：【卖方（下游）结算】结算金额>【1013】已回款金额？【卖方（下游）结算】结算金额-【1013】已回款金额：0）
create view v_2010_cang as
select
base.orderId,
base.hsId,
case when
seller.orderId is not null and seller.hsId is not null and seller.amount>v_1013.totalHuikuanPaymentMoney 
 then seller.amount-v_1013.totalHuikuanPaymentMoney 
else
0.00
end
as settledDownstreamHuikuanMoneny
from base 
     left join hs_same_settle_seller seller on base.hsId=seller.Id
     left join v_1013 on base.hsId=v_1013.hsId;

-- 【1050】下游占压总额-【1043】买方未结算金额+【1054】预收款
create view v_2010_ying as
select 
       base.orderId,
       base.hsId,
      ROUND(IFNULL( v_1050_ying.downstreamCapitalPressure ,0.00)-IFNULL(v_1041_ying.unsettlerBuyerMoneyAmount ,0.00)+IFNULL(v_1054_ying.yingPrePayment  ,0.00),2)as settledDownstreamHuikuanMoneny
from  base
     left join v_1050_ying on base.hsId=v_1050_ying.hsId
     left join v_1041_ying on v_1041_ying.hsId=v_1050_ying.hsId
     left join v_1054_ying on v_1054_ying.hsId=v_1050_ying.hsId;


--  1075 【付款】汇总收款单位 = “参与方X”的付款金额  - 【费用】对方单位 = “上游供应商ID”的费用金额

create view v_1075_fukuan as
select 
base.orderId,
base.hsId,
fukuan.receiveCompanyId,
sum(fukuan.payAmount) as payAmount
from base 
left join hs_same_fukuan  fukuan on base.hsId=fukuan.hsId and  fukuan.deleted=0
group by orderId, hsId, receiveCompanyId;


create view v_1075_fee  as
select
base.orderId,
base.hsId,
hs_same_fee.otherPartyId,
ROUND(sum(IFNULL(amount,0.00)),2)  as amount
from base
left join  hs_same_fee on  base.hsId=hs_same_fee.hsId and   deleted=0
group by orderId,hsId, hs_same_fee.otherPartyId;

-- 1075  资金占压（参与方X）      【付款】汇总收款单位 = “参与方X”的付款金额  - 【费用】对方单位 = “参与方X”的费用金额  同应收     
create view v_1075 as
select
base.orderId,
base.hsId,
v_1075_fukuan.receiveCompanyId,
v_1075_fee.otherPartyId,
IFNULL(v_1075_fukuan.payAmount,0.00) - IFNULL(v_1075_fee.amount,0.00) as partiesCapitalPressure
from base
left join  v_1075_fukuan on  base.hsId=v_1075_fukuan.hsId 
left join  v_1075_fee on  base.hsId=v_1075_fee.hsId  and v_1075_fukuan.receiveCompanyId=v_1075_fee.otherPartyId;



create view v_1076_invoice as
select
invoice.orderId,
invoice.hsId,
invoice.openCompanyId,
sum(IFNULL(detail.priceAndTax,0.00)) as unInvoicePrice
from hs_same_invoice_detail detail
     inner join hs_same_invoice invoice on detail.invoiceId= invoice.id 
     inner join hs_same_order orders on invoice.orderId=orders.id
where detail.deleted =0  and  invoice.deleted=0
group by orderId,hsId,openCompanyId;


create view v_1076 as 
select
base.orderId,
base.hsId,
v_1075_fee.otherPartyId,
IFNULL(v_1075_fee.amount,0.00)- IFNULL(v_1076_invoice.unInvoicePrice,0.00) as  unInvoicePrice
from base 
left join v_1075_fee on base.hsId=v_1075_fee.hsId
left join v_1076_invoice on  base.hsId=v_1076_invoice.hsId  and  v_1075_fee.otherPartyId=v_1076_invoice.openCompanyId;



-- IF（【3007】已出库金额>【1013】已回款金额？【3007】已出库金额-【1013】已回款金额：0）
create view v_1078 as
select
base.orderId,
base.hsId,
case when
 v_3003.totalOutstorageMoney > v_1013.totalHuikuanPaymentMoney 
then v_3003.totalOutstorageMoney-v_1013.totalHuikuanPaymentMoney
else  0
end  as  unsettleSellerMoneyAmount
from base 
left join v_3003 on  base.hsId= v_3003.hsId
left join v_1013 on  base.hsId= v_1013.hsId ;










