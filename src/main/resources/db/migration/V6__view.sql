use hsdb;
--运费相关 付款运费金额
create view v_1001 as
select
orderId,
hsId,
ROUND(sum(IFNULL(payAmount,0.00)) ,0.00)as totalPayTrafficFee
from hs_same_fukuan
where payUsage= 'FREIGNHT' and deleted=0
group by  orderId, hsId;


create view v_1002 as
select
orderId,
hsId,
ROUND(sum(IFNULL(payAmount,0.00)) ,2)as totalTradeGapFee
from hs_same_fukuan
where payUsage= 'TRADE_DEFICIT' and deleted=0
group by  orderId, hsId;


create view v_1003 as
select
orderId,
hsId,
ROUND(sum(IFNULL(payAmount,0.00)) ,2)as totalPaymentAmount
from hs_same_fukuan
where deleted=0
group by  orderId, hsId;


--借款合计

create view v_1004 as
select
orderId,
hsId,
ROUND(sum(IFNULL(amount,0.00)) ,2)as totalLoadMoney
from hs_same_jiekuan
where deleted=0
group by  orderId, hsId;

--借款预估成本
create view v_1005 as
select
orderId,
hsId,
ROUND(amount*IFNULL(useInterest,0.00)*IFNULL(useDays,0.00)/360 ,2)as loadEstimateCost
from hs_same_jiekuan
where deleted =0
group by  orderId, hsId;


--查询未对应的借款
--create view temp_jiekuan as


--未还款预估成本
create view v_1006 as
select
jiekuan.hsId,
jiekuan.orderId,
jiekuan.id,
ROUND(IFNULL(sum(IFNULL(amount,0.00)*IFNULL(useInterest,0.00) * IFNULL(useDays,0.00)/360),0.00),2)  as totalUnrepaymentEstimateCost
from hs_same_jiekuan jiekuan
     left join hs_same_huankuan_map map
     on jiekuan.id=map.jiekuanId
where map.jiekuanId is NUll and jiekuan.deleted=0
GROUP BY hsId,orderId;


--还款相关 1007  1008  1011
--1007	还款本金合计	totalRepaymentPrincipeAmount	【还款】		汇总：还款本金
--1008	还款利息合计	totalrepaymentInterest	【还款】		汇总：还款利息
--1011	还款服务费合计	totalServiceCharge	【还款】		汇总：还款服务费
create view v_1007 as
select
huankuan.orderId,
huankuan.hsId,
ROUND(sum(IFNULL(map.principal,0.00)),2) as totalRepaymentPrincipeAmount,
ROUND(sum(IFNULL(map.interest,0.00)),2) as  totalRepaymentInterest,
ROUND(sum(IFNULL(map.fee,0.00)),2) as  totalServiceCharge
from hs_same_huankuan huankuan
left join hs_same_huankuan_map map on huankuan.id= map.huankuanId
where huankuan.deleted=0
group by  orderId, hsId;

--还款状态  1009  1010
--1009	登记未还本金金额	totalUnpayPrincipal	【还款】		汇总：还款状态= “未还款”的还款本金
--1010	登记未还利息金额	totalUnpayInterest	【还款】		汇总：还款状态= “未还款”的还款利息
create view v_1009 as
select
huankuan.orderId,
huankuan.hsId,
ROUND(sum(IFNULL(map.principal,0.00)) ,2)as totalUnpayPrincipal,
ROUND(sum(IFNULL(map.interest,0.00)),2)as  totalUnpayInterest,
ROUND(sum(IFNULL(map.fee,0.00)),2)as  totalUnpayFee
from hs_same_huankuan huankuan
left join hs_same_huankuan_map map on huankuan.id= map.huankuanId
where huankuan.deleted=0 and promise=false and map.deleted=0
group by  orderId, hsId;

--  1012 外部资金使用成本   【1006】未还款预估成本 +【1008】还款利息合计 + 【1011】还款服务费合计


create view v_1012 as
select
v_1007.orderId,
v_1007.hsId,
ROUND(IFNULL(v_1006.totalUnrepaymentEstimateCost,0.00)+IFNULL(v_1007.totalRepaymentInterest,0.00)+IFNULL(v_1007.totalServiceCharge,0.00) ,2)as outCapitalAmout
from v_1007
left join v_1006 on  v_1006.hsId=v_1007.hsId and v_1006.orderId=v_1007.orderId;


--1013 已回款金额  【1006】未还款预估成本 +【1008】还款利息合计 + 【1011】还款服务费合计
create view v_1013 as
select
orderId,
hsId,
ROUND(sum(IFNULL(huikuanAmount,0.00)) ,2)as totalHuikuanPaymentMoney
from hs_same_huikuan
where deleted =0
group by  orderId, hsId;

--付货款金额
create view v_1014 as
select

v_1003.orderId,
v_1003.hsId,
ROUND(IFNULL(v_1003.totalPaymentAmount,0.00)-IFNULL(v_1001.totalPayTrafficFee,0.00)-IFNULL(v_1002.totalTradeGapFee,0.00),2) as payCargoAmount
from v_1003
left join v_1001 on v_1001.hsId =v_1003.hsId
left join v_1002 on v_1002.hsId=v_1003.hsId
group by  orderId, hsId;


--1015未回款金额

create view v_1015 as
select
v_1014.orderId,
v_1014.hsId,
case  when IFNULL(v_1014.payCargoAmount,0.00) > IFNULL(v_1013.totalHuikuanPaymentMoney,0.00)
THEN  ROUND(IFNULL(v_1014.payCargoAmount,0.00) - IFNULL(v_1013.totalHuikuanPaymentMoney,0.00),2)
ELSE 0 end  as unpaymentMoney
from v_1014
     left JOIN v_1013  on v_1014.hsId = v_1013.hsId
group by orderId,hsId;

--1016 未回款金额预估收益

create view v_1016 as
select
v_1015.orderId,
v_1015.hsId,
ROUND(IFNULL(v_1015.unpaymentMoney,0.00) * IFNULL(config.contractBaseInterest,0.00) * IFNULL(config.expectHKDays,0.00) / 360 ,2)as unpaymentEstimateProfile
from v_1015
     left JOIN hs_same_order_config config  on config.id = v_1015.hsId
group by orderId,hsId;


--1017 计息天数  每条回款-付款记录：计息天数 = 回款日期 - 付款日期 - 【买方结算】折扣天数

create view v_1017 as
select
huikuan.orderId,
huikuan.hsId,
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
          end as interestDays
from hs_same_huikuan huikuan
     left join hs_same_huikuan_map map on map.huikuanId =huikuan.id
     left join hs_same_fukuan  fukuan on fukuan.id=map.fukuanId
     left join hs_same_settle_seller seller on huikuan.orderId=seller.orderId;

--1018 实际使用率
create view v_1018 as
select
seller.orderId,
seller.hsId,
ROUND(IFNULL(config.contractBaseInterest,0.00)- IFNULL(seller.discountInterest,0.00) ,2)as actualUtilizationRate
from hs_same_order_config config
     left join hs_same_settle_seller seller on config.id=seller.hsId
group by orderId,hsId;

--1019 应计利息
create view v_1019 as
select
huikuan.hsId,
huikuan.orderId,
map.amount,
v_1018.actualUtilizationRate,
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

from hs_same_huikuan_map map
     left join hs_same_huikuan huikuan on huikuan.id=map.huikuanId
     left join hs_same_fukuan fukuan on fukuan.id=map.fukuanId
     left join v_1018 on v_1018.hsId=huikuan.hsId
     left join hs_same_settle_seller seller on fukuan.hsId=seller.hsId;



--1020 已回款应计利息合计
create view v_1020 as
select
orderId,
hsId,
ROUND(sum(IFNULL(rate,0.00)) ,2)as totalPaymentedRateMoney
from v_1019
group by orderId,hsId;


--1021
create view v_1021 as
select
v_1016.orderId,
v_1016.hsId,
ROUND(IFNULL(v_1016.unpaymentEstimateProfile,0.00)+IFNULL(v_1020.totalPaymentedRateMoney,0.00)-IFNULL(seller.discountAmount,0.00),2) as contractRateProfile
from v_1016
     left join v_1020 on v_1016.hsId=v_1020.hsId
     left join hs_same_settle_seller seller on v_1016.hsId=seller.hsId and seller.orderId=v_1016.orderId
group by orderId,hsId;

--1022 贴现息

create view v_1022 as
select
id,
orderId,
hsId,
CASE WHEN huikuanMode ='BANK_ACCEPTANCE'
THEN ROUND(TIMEDIFF(huikuanBankPaperExpire,huikuanBankPaperDate)/24 *IFNULL(huikuanAmount ,0.00)* IFNULL(huikuanBankDiscountRate,0.00)*1.17/360,2)
ELSE 0 END as tiexianRate
from hs_same_huikuan
where deleted= 0;


--1023 贴现息合计
create  view v_1023 as
select
orderId,
hsId,
ROUND(sum(IFNULL(tiexianRate,0.00)) ,2)as tiexianRateAmount
from v_1022
group by orderId,hsId;

--1024 买方结算   1024  1025 1026
create view v_1024 as
select
orderId,
hsId,
ROUND(sum(IFNULL(amount,0.00)),2) as totalBuyerNums,
ROUND(sum(IFNULL(money,0.00)) ,2)as totalBuyerMoney,
ROUND(sum(IFNULL(settleGap,0.00)),2) as totalBuyersettleGap
from hs_same_settle_buyer
where deleted=0
group by orderId,hsId;

--1027 费用相关 1027->1034

--1027	代收代垫运费	dsddFee	【费用】	毛利表	汇总：费用科目 = “代收代垫运费”的费用金额
--1028	含税汽运费	hsqyFee	【费用】	毛利表	汇总：费用科目 = “含税汽运费”的费用金额
--1029	含税水运费	hssyFee	【费用】	毛利表	汇总：费用科目 = “含税水运费”的费用金额
--1030	含税火运费	hshyFee	【费用】	毛利表	汇总：费用科目 = “含税火运费”的费用金额
--1031	服务费	serviceFee	【费用】	毛利表	汇总：费用科目 = “监管费”的费用金额
--1032	管理费	superviseFee	【费用】	毛利表	汇总：费用科目 = “管理费”的费用金额
--1033	业务费	businessFee	【费用】	毛利表	汇总：费用科目 = “业务费”的费用金额
--1034	销售费用总额	salesFeeAmount	【1028】含税汽运费 + 【1029】含税水运费 + 【1030】含税火运费 + 【1031】监管费 + 【1032】管理费 + 【1033】业务费
create view v_1027 as
select
orderId,
hsId,
ROUND(sum(case when name='HELP_RECIVE_PAY_FEE' then IFNULL(amount ,0.00)else 0 end),2) as dsddFee,
ROUND(sum(case when name='TAX_MOTRO_FREIGHT' then  IFNULL(amount ,0.00) else 0 end) ,2)as hsqyFee,
ROUND(sum(case when name='TAX_SHIP_FREIGHT' then  IFNULL(amount ,0.00) else 0 end) ,2)as hssyFee,
ROUND(sum(case when name='TAX_RAIL_FREIGHT' then  IFNULL(amount ,0.00)else 0 end) ,2)as hshyFee,
ROUND(sum(case when name='SERVICE_FEE' then  IFNULL(amount ,0.00) else 0 end) ,2)as serviceFee,
ROUND(sum(case when name='SUPERVISE_FEE' then  IFNULL(amount ,0.00)  else 0 end),2) as superviseFee,
ROUND(sum(case when name='BUSINESS_FEE' then  IFNULL(amount ,0.00)  else 0 end) ,2)as businessFee,
ROUND(sum(case when name !='HELP_RECIVE_PAY_FEE' then  IFNULL(amount ,0.00)  else 0 end),2) as salesFeeAmount
from hs_same_fee
where  deleted=0
group by orderId,hsId;

--发票相关
--1035 贸易公司已收到的进项数量 金额 --1035  1036

create view v_1035 as
select
invoice.hsId,
invoice.orderId,
ROUND(sum(IFNULL(detail.cargoAmount,0.00)) ,2)as tradingCompanyInTypeNum,
ROUND(sum(IFNULL(detail.priceAndTax,0.00)) ,2)as tradingCompanyInTpeMoneyAmount
from hs_same_invoice_detail detail
     inner join hs_same_invoice invoice on detail.invoiceId= invoice.id
     inner join hs_same_order orders on invoice.orderId=orders.id
     inner join hs_same_order_party party on party.orderId=orders.id and party.custType='TRAFFICKCER'and invoice.receiverId=party.customerId
where detail.deleted =0 and invoice.invoiceDirection='INCOME' and invoice.openCompanyId=orders.upstreamId
group by  hsId,orderId;


--1037 ccs一手到进项数量
--1037Css已收到进项数量   1038Css已收到进项金额
create view v_1037 as
select
invoice.hsId,
invoice.orderId,
ROUND(sum(IFNULL(detail.cargoAmount,0.00)) ,2)as totalCSSIntypeNumber,
ROUND(sum(IFNULL(detail.priceAndTax,0.00)),2) as totalCCSIntypeMoney
from hs_same_invoice_detail detail
     inner join hs_same_invoice invoice on detail.invoiceId= invoice.id
     inner join hs_same_order orders on invoice.orderId=orders.id
where detail.deleted =0 and invoice.invoiceDirection='INCOME' and invoice.receiverId=orders.mainAccounting
group by hsId,orderId;


--  invoicedMoneyAmount 1039占压表已开票金额
create view v_1039 as
select
invoice.hsId,
invoice.orderId,
ROUND(sum(IFNULL(detail.cargoAmount,0.00)),2) as invoicedMoneyNum,
ROUND(sum(IFNULL(detail.priceAndTax,0.00)) ,2)as invoicedMoneyAmount
from hs_same_invoice_detail detail
     inner join hs_same_invoice invoice on detail.invoiceId= invoice.id
     inner join hs_same_order orders on invoice.orderId=orders.id
where detail.deleted =0 and invoice.invoiceDirection='INCOME' and invoice.openCompanyId=orders.upstreamId
group by  hsId,orderId;


--v_2001 已到场数量  未到场数量  发运总数量
create view  v_2001 as
select
orderId,
hsId,
ROUND(sum(case when  arriveStatus = 'ARRIVE' then IFNULL(fyamount ,0.00)else 0 end ) ,2)as  totalArriveNum,
ROUND(sum(case when  arriveStatus=  'UNARRIVE' then IFNULL(fyamount ,0.00) else 0 end ),2) as  totalUnarriveNum ,
ROUND(sum(hsdb.hs_ying_fayun.fyamount) ,2)as totalFayunNum
from hsdb.hs_ying_fayun
where deleted = 0
group by orderId, hsId;

--2004  保证金相关
--2004收上游保证金	totalUpstreamBail	【保证金】		汇总： 保证金类型 = “收上游保证金”的保证金金额
--2005退上游保证金	totalRefundUpBail	【保证金】		汇总： 保证金类型 = “退上游保证金”的保证金金额
--2006付下游保证金	totalPayDownBail	【保证金】		汇总： 保证金类型 = “付下游保证金”的保证金金额
--2007下游退保证金	totalRefundDownBail	【保证金】		汇总： 保证金类型 = “下游退保证金”的保证金金额
create view v_2004 as
select
hsId,
orderId,
ROUND(sum(case when bailType='RECV_UP' then IFNULL(bailAmount ,0.00) else 0 end) ,2)as totalUpstreamBail,
ROUND(sum(case when bailType='BACK_UP' then IFNULL(bailAmount ,0.00) else 0 end) ,2)as totalRefundUpBail,
ROUND(sum(case when bailType='PAY_DOWN' then IFNULL(bailAmount ,0.00) else 0 end) ,2)as totalPayDownBail,
ROUND(sum(case when bailType='BACK_DOWN' then IFNULL(bailAmount ,0.00) else 0 end),2) as totalRefundDownBail
from hs_ying_bail
where  deleted=0
group by hsId,OrderId;

--2008	上游保证金余额	balanceUpstreamBail	计算		【2004】收上游保证金 - 【2005】退上游保证金
--2009	下游保证金余额	balanceDownStreamBail	计算		【2006】付下游保证金 - 【2007】下游退保证金
create view v_2008 as
select
hsId,
orderId,
ROUND(IFNULL(totalUpstreamBail,0.00)-IFNULL(totalRefundUpBail ,0.00),0.00) as balanceUpstreamBail,
ROUND(IFNULL(totalPayDownBail,0.00)-IFNULL(totalRefundDownBail ,0.00),0.00)as balanceDownStreamBail
from v_2004
group by hsId,OrderId;



--出库入库相关
--3001	入库总数量	totalInstorageNum	【入库】	备查账	汇总：入库数量
--3002	入库总金额	totalInstorageAmount	【入库】		汇总：入库金额
--3004	入库单价	instorageUnitPrice	计算		【3002】入库总金额 ／ 【3001】入库总数量
create view v_3001  as
select
hsId,
orderId,
sum(IFNULL(rukuAmount,0.00)) as totalInstorageNum,
sum(IFNULL(rukuAmount,0.00) * IFNULL(rukuPrice,0.00)) as totalInstorageAmount,
sum(IFNULL(rukuAmount,0.00) * IFNULL(rukuPrice,0.00))/sum(IFNULL(rukuAmount,0.00)) as instorageUnitPrice
from hs_cang_ruku
where deleted =0
group by hsId, orderId;

--3003	已出库数量	totalOutstorageNum	【出库】	备查账	汇总：出库数量
--3007  已出库金额	totalOutstorageMoney	【出库】		汇总:出库金额

create view v_3003 as
select
chuku.orderId,
chuku.hsId,
ROUND(sum(IFNULL(chukuAmount,0.00)) ,2)as totalOutstorageNum,
ROUND(sum(IFNULL(chukuPrice,0.00)*IFNULL(chukuAmount,0.00)) ,2)as totalOutstorageMoney
from hs_cang_chuku chuku
where deleted =0
group by hsId, orderId;


--3005	库存数量	totalStockNum	计算	备查账/占压	【3001】入库总数量 - 【3003】已出库数量
create view v_3005  as
select
v_3001.hsId,
v_3001.orderId,
ROUND(IFNULL(v_3001.totalInstorageNum,0.00)-IFNULL(v_3003.totalOutstorageNum ,0.00),2)as totalStockNum
from v_3001
     left join v_3003 on v_3001.hsId=v_3003.hsId
group by hsId, orderId;

--3006	库存金额	totalStockMoney	计算	占压	【3004】入库单价 * 【3005】库存数量
create view v_3006  as
select
v_3001.hsId,
v_3001.orderId,
ROUND(IFNULL(v_3001.instorageUnitPrice ,0.00)*IFNULL(v_3005.totalStockNum ,0.00),2)as totalStockMoney
from v_3001
     left join v_3005 on v_3005.hsId=v_3001.hsId
group by hsId, orderId;



--应收
--1040 核算结算量  yinalSettleAmount
--1041	贸易公司加价	tradingCompanyAddMoney
--1042	买方未结算数量	unsettlerBuyerNumber
--1043	卖方未结算金额	unsettlerBuyerMoneyAmount
--1044	销售货款总额	saleCargoAmountofMoney
create view v_1041_cang as
select
v_3001.hsId,
v_3001.orderId,
ROUND(IFNULL(v_3001.totalInstorageNum ,0.00),2)as finalSettleAmount,
ROUND(IFNULL(v_3001.totalInstorageNum ,0.00)-IFNULL(v_1024.totalBuyerNums ,0.00),2)as  unsettlerBuyerNumber,
ROUND(IFNULL(v_3001.totalInstorageNum ,0.00) * IFNULL(config.tradeAddPrice ,0.00),2)as tradingCompanyAddMoney,
ROUND((IFNULL(v_3001.totalInstorageNum ,0.00)-IFNULL(v_1024.totalBuyerNums,0.00)) * IFNULL(config.weightedPrice,0.00) ,2)as unsettlerBuyerMoneyAmount,
ROUND((IFNULL(v_3001.totalInstorageNum ,0.00)-IFNULL(v_1024.totalBuyerNums,0.00)) * IFNULL(config.weightedPrice ,0.00)+IFNULL(v_1024.totalBuyerMoney ,0.00),2)as saleCargoAmountofMoney
from v_3001
     left join v_1024 on v_3001.hsId=v_1024.hsId
     left join hs_same_order_config config on config.id=v_3001.hsId
group by hsId, orderId;

--应收
create view v_1041_ying as
select
v_2001.hsId,
v_2001.orderId,
ROUND(IFNULL(v_2001.totalFayunNum,0.00)-IFNULL(v_1024.totalBuyersettleGap,0.00) ,2)as finalSettleAmount,
ROUND(IFNULL(v_2001.totalFayunNum,0.00)-IFNULL(v_1024.totalBuyersettleGap,0.00) -IFNULL(v_1024.totalBuyerNums,0.00),2) as  unsettlerBuyerNumber,
ROUND((IFNULL(v_2001.totalFayunNum,0.00)-IFNULL(v_1024.totalBuyersettleGap,0.00) ) * config.tradeAddPrice ,2)as tradingCompanyAddMoney,
ROUND((IFNULL(v_2001.totalFayunNum,0.00)-IFNULL(v_1024.totalBuyersettleGap,0.00) -IFNULL(v_1024.totalBuyerNums,0.00) ) * IFNULL(config.weightedPrice ,0.00),2) as unsettlerBuyerMoneyAmount,
ROUND((IFNULL(v_2001.totalFayunNum,0.00)-IFNULL(v_1024.totalBuyersettleGap,0.00) -IFNULL(v_1024.totalBuyerNums,0.00) ) * IFNULL(config.weightedPrice ,0.00)+IFNULL(v_1024.totalBuyerMoney,0.00),2)as saleCargoAmountofMoney
from v_2001
     left join v_1024 on v_2001.hsId=v_1024.hsId
     left join hs_same_order_config config on config.id=v_2001.hsId
group by hsId, orderId;




--1045 瑞茂通总收益  【1021】合同利息收益 - 【1012】外部资金使用成本 + 【1023】贴现息合计
create view v_1045 as
select
v_1021.hsId,
v_1021.orderId,
ROUND(IFNULL(v_1021.contractRateProfile,0.00)-IFNULL(v_1012.outCapitalAmout,0.00)+IFNULL(v_1023.tiexianRateAmount ,0.00),2)as ccsProfile
from v_1021
left join v_1012 on v_1021.hsId=v_1012.hsId
left join v_1023 on v_1021.hsId=v_1023.hsId
group by hsId, orderId;


--1046 采购货款总额 【1044】销售货款总额 - 【1045】瑞茂通总收益
create view v_1046_ying as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
ROUND(IFNULL(v_1041_ying.saleCargoAmountofMoney,0.00)-IFNULL(v_1045.ccsProfile ,0.00),2)as purchaseCargoAmountOfMoney
from v_1041_ying
     left join v_1045 on v_1041_ying.hsId=v_1045.hsId
group by hsId, orderId;


create view v_1046_cang as
select
v_1041_cang.hsId,
v_1041_cang.orderId,
ROUND(IFNULL(v_1041_cang.saleCargoAmountofMoney,0.00)-IFNULL(v_1045.ccsProfile ,0.00),2)as purchaseCargoAmountofMoney
from v_1041_cang
     left join v_1045 on v_1041_cang.hsId=v_1045.hsId
group by hsId, orderId;

--1047 外部资金使用率  【1004】借款金额合计 - 【1007】还款本金合计  + 【1009】登记未还款本金
create view v_1047 as
select
v_1004.hsId,
v_1004.orderId,
ROUND(IFNULL(v_1004.totalLoadMoney,0.00)-IFNULL(v_1007.totalRepaymentPrincipeAmount,0.00)+IFNULL(v_1009.totalUnpayPrincipal ,0.00),2)as externalCapitalPaymentAmount
from v_1004
     left join v_1007 on v_1004.hsId=v_1007.hsId
     left join v_1009 on v_1009.hsId=v_1007.hsId
group by hsId, orderId;

--1048 ownerCapitalPaymentAmount 自由资金付款金额【1003】付款金额合计 - 【1047】外部资金付款金额 + 【1008】还款利息合计  -【1010】登记未还款利息 - 【2008】上游保证金余额
create view v_1048_ying as
select
v_1003.hsId,
v_1003.orderId,
ROUND(IFNULL(v_1003.totalPaymentAmount,0.00)-IFNULL(v_1047.externalCapitalPaymentAmount,0.00)+IFNULL(v_1007.totalRepaymentInterest,0.00)+IFNULL(v_1009.totalUnpayInterest,0.00)-IFNULL(v_2008.balanceUpstreamBail ,0.00) ,2)as ownerCapitalPaymentAmount
from v_1003
     left join v_1047 on v_1003.hsId=v_1047.hsId
     left join v_1007 on v_1003.hsId=v_1007.hsId
     left join v_1009 on v_1003.hsId=v_1009.hsId
     left join v_2008 on v_1003.hsId=v_2008.hsId
group by hsId, orderId;

--1048【1003】付款金额合计 - 【1047】外部资金付款金额
create view v_1048_cang as
select
v_1003.hsId,
v_1003.orderId,
ROUND(IFNULL(v_1003.totalPaymentAmount,0.00)-IFNULL(v_1047.externalCapitalPaymentAmount ,0.00),2)as ownerCapitalPaymentAmount
from v_1003
     left join v_1047 on v_1003.hsId=v_1047.hsId
group by hsId, orderId;

--1049  上游资金占压	upstreamCapitalPressure	计算	备查账／占压表	【1047】外部资金付款金额 + 【1048】自有资金付款金额 - 【1046】采购货款总额
create view v_1049_ying as
select
seller.hsId,
seller.orderId,
case  when seller.orderId is not null and seller.hsId is not null
then
ROUND(IFNULL(IFNULL(v_1047.externalCapitalPaymentAmount,0.00)+  IFNULL(v_1048_ying.ownerCapitalPaymentAmount,0.00)-  IFNULL(v_1046_ying.purchaseCargoAmountofMoney,0.00)
       - IFNULL(v_1002.totalTradeGapFee,0.00)-v_1001.totalPayTrafficFee,0.00),2)
else
ROUND(IFNULL(IFNULL(v_1047.externalCapitalPaymentAmount,0.00)+  IFNULL(v_1048_ying.ownerCapitalPaymentAmount,0.00)-  IFNULL(v_1046_ying.purchaseCargoAmountofMoney,0.00)
       - IFNULL(v_1002.totalTradeGapFee,0.00),0.00),2)
end
as upstreamCapitalPressure
from hs_same_settle_seller seller
     left join v_1048_ying on v_1048_ying.hsId=seller.hsId
     left join v_1046_ying on v_1046_ying.hsId=seller.hsId
     left join v_1002 on v_1002.hsId=seller.hsId
     left join v_1047  on seller.orderId=v_1048_ying.orderId and seller.hsId=v_1047.hsId
     left join v_1001  on seller.orderId=seller.orderId and seller.hsId=v_1047.hsId
where deleted=0
group by hsId, orderId;

create view v_1049_cang as
select
v_1047.hsId,
v_1047.orderId,
ROUND(IFNULL(v_1047.externalCapitalPaymentAmount,0.00)+IFNULL(v_1048_cang.ownerCapitalPaymentAmount,0.00)-IFNULL(v_1046_cang.purchaseCargoAmountofMoney ,0.00),2)as upstreamCapitalPressure
from v_1047
     left join v_1048_cang on v_1048_cang.hsId=v_1047.hsId
     left join v_1046_cang on v_1046_cang.hsId=v_1047.hsId
group by hsId, orderId;
--1050 下游资金占压	downstreamCapitalPressure	计算	备查账／占压表	【1044】销售货款总额 - 【1013】已回款金额 +【2009】下游保证金余额

create view v_1050_ying as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
ROUND(IFNULL(v_1041_ying.saleCargoAmountofMoney,0.00)-IFNULL(v_1013.totalHuikuanPaymentMoney,0.00)+IFNULL(v_2008.balanceDownStreamBail ,0.00),2)as downstreamCapitalPressure
from v_1041_ying
     left join v_1013 on v_1041_ying.hsId=v_1013.hsId
     left join v_2008 on v_1041_ying.hsId=v_2008.hsId
group by hsId, orderId;

--【1044】销售货款总额 - 【1013】已回款金额
create view v_1050_cang as
select
v_1041_cang.hsId,
v_1041_cang.orderId,
ROUND(IFNULL(v_1041_cang.saleCargoAmountofMoney,0.00)-IFNULL(v_1013.totalHuikuanPaymentMoney ,0.00),2)as downstreamCapitalPressure
from v_1041_cang
     left join v_1013 on v_1041_cang.hsId=v_1013.hsId
     left join v_2008 on v_1041_cang.hsId=v_2008.hsId
group by hsId, orderId;


--1051 CCS未收到进项数量	cssUninTypeNum	计算	备查账	【1040】核算结算量 - 【1037】CCS已收进项数量

create view v_1051_ying as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
ROUND(IFNULL(IFNULL(v_1041_ying.finalSettleAmount,0.00)-IFNULL(v_1037.totalCSSIntypeNumber ,0.00),0.00),2)as cssUninTypeNum
from v_1041_ying
     left join v_1037 on v_1037.hsId=v_1041_ying.hsId
group by hsId, orderId;

create view v_1051_cang as
select
v_1041_cang.hsId,
v_1041_cang.orderId,
ROUND(IFNULL(IFNULL(v_1041_cang.finalSettleAmount,0.00)-IFNULL(v_1037.totalCSSIntypeNumber ,0.00),0.00),2)as cssUninTypeNum
from v_1041_cang
     left join v_1037 on v_1037.hsId=v_1041_cang.hsId
group by hsId, orderId;

--1052 CCS未收到进项金额	cssUninTypeMoney	     	【1046】采购货款总额 + 【1041】贸易公司加价 - 【1038】CCS已收进项金额 - 【1027】代收代垫运费
--1053	占压表未开票金额	unInvoicedAmountofMoney		【1046】采购货款总额 + 【1041】贸易公司加价 - 【1039】占压表已开票金额 - 【1027】代收代垫运费
create view v_1052_ying  as
select
v_1046_ying.hsId,
v_1046_ying.orderId,
ROUND(IFNULL(v_1046_ying.purchaseCargoAmountofMoney,0.00)+IFNULL(v_1041_ying.tradingCompanyAddMoney,0.00)-IFNULL(v_1037.totalCCSIntypeMoney,0.00)-IFNULL(v_1027.dsddFee ,0.00),2)as cssUninTypeMoney,
ROUND(IFNULL(v_1046_ying.purchaseCargoAmountofMoney,0.00)+IFNULL(v_1041_ying.tradingCompanyAddMoney,0.00)-IFNULL(v_1039.invoicedMoneyAmount,0.00)-IFNULL(v_1027.dsddFee ,0.00),2)as unInvoicedAmountofMoney
from v_1046_ying
     left join v_1041_ying on v_1046_ying.hsId=v_1041_ying.hsId
     left join v_1037 on v_1037.hsId=v_1046_ying.hsId
     left join v_1027 on v_1027.hsId=v_1046_ying.hsId
     left join v_1039 on v_1039.hsId=v_1046_ying.hsId
group by hsId, orderId;



create view v_1052_cang  as
select
v_1046_cang.hsId,
v_1046_cang.orderId,
ROUND(IFNULL(v_1046_cang.purchaseCargoAmountofMoney,0.00)+IFNULL(v_1041_cang.tradingCompanyAddMoney,0.00)-IFNULL(v_1037.totalCCSIntypeMoney,0.00)-IFNULL(v_1027.dsddFee ,0.00),2)as cssUninTypeMoney,
ROUND(IFNULL(v_1046_cang.purchaseCargoAmountofMoney,0.00)+IFNULL(v_1041_cang.tradingCompanyAddMoney,0.00)-IFNULL(v_1039.invoicedMoneyAmount,0.00)-IFNULL(v_1027.dsddFee ,0.00),2)as unInvoicedAmountofMoney
from v_1046_cang
     left join v_1041_cang on v_1046_cang.hsId=v_1041_cang.hsId
     left join v_1037 on v_1037.hsId=v_1046_cang.hsId
     left join v_1027 on v_1027.hsId=v_1046_cang.hsId
     left join v_1039 on v_1039.hsId=v_1046_cang.hsId
group by hsId, orderId;

--1054	预收款	yingPrePayment	计算	占压表	IF（【1014】付货款金额 <= 【1013】已回款金额）？【1014】付货款金额 - 【1013】已回款金额 + 【2009】下游保证金余额  ：0 +【2009】下游保证金余额
--1054	预收款	cangPrePayment	计算	占压表	IF（【1014】付货款金额 <= 【1013】已回款金额？【1014】付货款金额 - 【1013】已回款金额 ：0 ）
create view v_1054_ying  as
select
v_1013.hsId,
v_1013.orderId,
case when IFNULL(v_1013.totalHuikuanPaymentMoney ,0.00)>IFNULL(v_1024.totalBuyerMoney,0.00)
then ROUND(IFNULL(v_1013.totalHuikuanPaymentMoney ,0.00)-IFNULL(v_1024.totalBuyerMoney,0.00)+ IFNULL(v_2008.balanceDownStreamBail,0.00),2) else ROUND(IFNULL(v_2008.balanceDownStreamBail,0.00),2) end as yingPrePayment
from v_1013
     left join v_1024 on v_1013.hsId=v_1024.hsId
     left join v_2008 on v_1013.hsId=v_2008.hsId
group by hsId, orderId;


create view v_1054_cang  as
select
v_1013.hsId,
v_1013.orderId,
ROUND(IFNULL(v_1013.totalHuikuanPaymentMoney ,0.00)-IFNULL(v_3003.totalOutstorageMoney ,0.00),2)as cangPrePayment
from v_1013
     left join v_3003 on v_1013.hsId=v_3003.hsId
group by hsId, orderId;




--1055 毛利结算量	settleGrossProfileNum	计算	毛利表	【1040】核算用结算量
--仓押毛利结算量 【3003】已出库数量
create view v_1055 as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
ROUND(IFNULL(v_1041_ying.finalSettleAmount ,0.00),2)as settleGrossProfileNum
from v_1041_ying
group by hsId, orderId;

--1056 yingshou采购含税总额	purchaseIncludeTaxTotalAmount	计算	毛利表	【1046】采购货款总额
-- 仓押采购含税总额   purchaseIncludeTaxTotalAmount【3004】入库单价 * 【1055】毛利结算量
create view v_1056_cang  as
select
v_3001.hsId,
v_3001.orderId,
ROUND(IFNULL(v_3001.instorageUnitPrice ,0.00)* IFNULL(v_3003.totalOutstorageNum  ,0.00),2)as purchaseIncludeTaxTotalAmount
from v_3001
left join v_3003 on  v_3001.hsId=v_3003.hsId
group by hsId, orderId;

--1057	仓押销售含税总额	saleIncludeTaxTotalAmount	【1056】采购含税总额 + 【1045】瑞茂通总收益
--1057  应收 【1044】销售货款总额
create view v_1057_cang  as
select
v_1056_cang.hsId,
v_1056_cang.orderId,
ROUND(IFNULL(v_1056_cang.purchaseIncludeTaxTotalAmount ,0.00)* IFNULL(v_1045.ccsProfile ,0.00) ,2)as saleIncludeTaxTotalAmount
from v_1056_cang
     left join v_1045 on  v_1056_cang.hsId=v_1045.hsId
group by hsId, orderId;

--1058	毛利贸易公司加价	tradeCompanyAddMoney	计算	毛利表	【1055】毛利结算量 * 【核算月配置】贸易公司加价
create view v_1058_ying  as
select
v_1055.hsId,
v_1055.orderId,
ROUND(IFNULL(v_1055.settleGrossProfileNum ,0.00)* IFNULL(config.tradeAddPrice  ,0.00),2)as tradeCompanyAddMoney
from v_1055
     left join hs_same_order_config config on  v_1055.hsId=config.id
group by hsId, orderId;

create view v_1058_cang  as
select
v_3003.hsId,
v_3003.orderId,
ROUND(IFNULL(v_3003.totalOutstorageNum ,0.00)* IFNULL(config.tradeAddPrice  ,0.00),2)as tradeCompanyAddMoney
from v_3003
     left join hs_same_order_config config on  v_3003.hsId=config.id
group by hsId, orderId;
--1059	不含税收入	withoutTaxIncome	计算	毛利表	（【1057】销售含税总额 - 【1027】代收代垫运费 ）／ 1.17
create view v_1059_ying  as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
ROUND((IFNULL(v_1041_ying.saleCargoAmountofMoney ,0.00)- IFNULL(v_1027.dsddFee,0.00))/1.17 ,2) as withoutTaxIncome
from v_1041_ying
     left join v_1027  on  v_1027.hsId=v_1041_ying.hsId
group by hsId, orderId;

create view v_1059_cang  as
select
v_1057_cang.hsId,
v_1057_cang.orderId,
ROUND((IFNULL(v_1057_cang.saleIncludeTaxTotalAmount ,0.00)- IFNULL(v_1027.dsddFee,0.00))/1.17 ,2) as withoutTaxIncome
from v_1057_cang
     left join v_1027  on  v_1027.hsId=v_1057_cang.hsId
group by hsId, orderId;

--1060	不含税成本	withoutTaxCost	计算	毛利表	（【1056】采购含税总额 - 【1027】代收代垫运费 + 【1058】毛利贸易公司加价）／1.17
create view v_1060_cang  as
select
v_1056_cang.hsId,
v_1056_cang.orderId,
ROUND((IFNULL(v_1056_cang.purchaseIncludeTaxTotalAmount ,0.00)- IFNULL(v_1027.dsddFee,0.00)+IFNULL(v_1058_cang.tradeCompanyAddMoney,0.00))/1.17  ,2)as withoutTaxCost
from v_1056_cang
     left join v_1027  on  v_1027.hsId=v_1056_cang.hsId
     left join v_1058_cang  on  v_1056_cang.hsId=v_1058_cang.hsId
group by hsId, orderId;


create view v_1060_ying  as
select
v_1046_ying.hsId,
v_1046_ying.orderId,
ROUND((IFNULL(v_1046_ying.purchaseCargoAmountofMoney ,0.00)-IFNULL( v_1027.dsddFee,0.00)+IFNULL(v_1058_ying.tradeCompanyAddMoney,0.00))/1.17  ,2)as withoutTaxCost
from v_1046_ying
     left join v_1027  on  v_1027.hsId=v_1046_ying.hsId
     left join v_1058_ying  on  v_1046_ying.hsId=v_1058_ying.hsId
group by hsId, orderId;

--1061	应交增值税	vat	计算	毛利表	IF（【1059】不含税收入 <= 【1060】不含税成本？0 : （【1059】不含税收入 - 【1060】不含税成本）*0.17）
--1062	税金及附加	additionalTax	计算	毛利表	【1061】应交增值税 * 0.12
create view v_1061_cang  as
select
v_1059_cang.hsId,
v_1059_cang.orderId,
ROUND(IFNULL(
case when IFNULL(v_1059_cang.withoutTaxIncome ,0.00)<= IFNULL(v_1060_cang.withoutTaxCost ,0.00)then 0 else (IFNULL(v_1059_cang.withoutTaxIncome ,0.00)-IFNULL(v_1060_cang.withoutTaxCost,0.00))*0.17 end,
0.00) ,2)as vat,
ROUND(IFNULL(
case when IFNULL(v_1059_cang.withoutTaxIncome ,0.00)<= IFNULL(v_1060_cang.withoutTaxCost ,0.00)then 0 else (IFNULL(v_1059_cang.withoutTaxIncome ,0.00)-IFNULL(v_1060_cang.withoutTaxCost,0.00))*0.17*0.12 end,
0.00) ,2)as AdditionalTax
from v_1059_cang
     left join v_1060_cang  on  v_1060_cang.hsId = v_1059_cang.hsId
group by hsId, orderId;

create view v_1061_ying  as
select
v_1059_ying.hsId,
v_1059_ying.orderId,
ROUND(IFNULL(
case when IFNULL(v_1059_ying.withoutTaxIncome ,0.00)<= IFNULL(v_1060_ying.withoutTaxCost ,0.00)then 0 else (IFNULL(v_1059_ying.withoutTaxIncome,0.00) -IFNULL(v_1060_ying.withoutTaxCost,0.00))*0.17 end,
0.00),2) as vat,
ROUND(IFNULL(
case when IFNULL(v_1059_ying.withoutTaxIncome ,0.00)<= IFNULL(v_1060_ying.withoutTaxCost ,0.00)then 0 else (IFNULL(v_1059_ying.withoutTaxIncome ,0.00)-IFNULL(v_1060_ying.withoutTaxCost,0.00))*0.17*0.12 end,
0.00),2)as additionalTax
from v_1059_ying
     left join v_1060_ying  on  v_1060_ying.hsId = v_1059_ying.hsId
group by hsId, orderId;


--1063	印花税	stampDuty	计算	毛利表	（【1057】销售含税总额 + 【1056】采购含税总额 + 【1058】毛利贸易公司加价 - 【1027】代收代垫运费 * 2 ） * 0.0003
create view v_1063_cang  as
select
v_1057_cang.hsId,
v_1057_cang.orderId,
ROUND((IFNULL(v_1057_cang.saleIncludeTaxTotalAmount ,0.00)+IFNULL(v_1056_cang.purchaseIncludeTaxTotalAmount,0.00)+IFNULL(v_1058_cang.tradeCompanyAddMoney,0.00)- IFNULL(v_1027.dsddFee,0.00)*2)*0.0003 ,2) as stampDuty
from v_1057_cang
     left join v_1027  on  v_1027.hsId=v_1057_cang.hsId
     left join v_1056_cang on  v_1056_cang.hsId=v_1057_cang.hsId
     left join v_1058_cang  on  v_1058_cang.hsId=v_1057_cang.hsId
group by hsId, orderId;


create view v_1063_ying  as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
ROUND((IFNULL(v_1041_ying.saleCargoAmountofMoney ,0.00)+IFNULL(v_1046_ying.purchaseCargoAmountofMoney,0.00)+IFNULL(v_1058_ying.tradeCompanyAddMoney,0.00)- IFNULL(v_1027.dsddFee,0.00)*2)*0.0003 ,2) as stampDuty
from v_1041_ying
     left join v_1027  on  v_1027.hsId=v_1041_ying.hsId
     left join v_1046_ying on  v_1046_ying.hsId=v_1041_ying.hsId
     left join v_1058_ying  on  v_1058_ying.hsId=v_1041_ying.hsId
group by hsId, orderId;
--1064	经营毛利	opreationCrocsProfile	计算	毛利表	【1059】不含税收入 - 【1060】不含税成本 - 【1062】税金及附加 - 【1063】印花税 -
--（【1028】含税汽运费 + 【1029】含税水运费 + 【1030】含税火运费）／1.11 - 【1031】监管费 ／1.06 - 【1031】服务费 ／1.06 - 【1033】业务费

create view v_1064_cang as
select
v_1059_cang.hsId,
v_1059_cang.orderId,
ROUND(IFNULL(v_1059_cang.withoutTaxIncome,0.00)-IFNULL(v_1060_cang.withoutTaxCost,0.00)-IFNULL(v_1061_cang.AdditionalTax,0.00)-IFNULL(v_1063_cang.stampDuty
,0.00)-(IFNULL(v_1027.hsqyFee,0.00)+IFNULL(v_1027.hssyFee,0.00)+IFNULL(v_1027.hshyFee,0.00))/1.11-IFNULL(v_1027.superviseFee,0.00)/1.06-IFNULL(v_1027.serviceFee,0.00)/1.06-IFNULL(v_1027.businessFee,0.00),2) as opreationCrossProfile
from v_1059_cang
left join v_1060_cang on v_1059_cang.hsId=v_1060_cang.hsId
left join v_1061_cang  on v_1059_cang.hsId=v_1061_cang.hsId
left join v_1063_cang  on v_1059_cang.hsId=v_1063_cang.hsId
left join v_1027  on v_1059_cang.hsId=v_1027.hsId
group by hsId, orderId;


create view v_1064_ying as
select
v_1059_ying.hsId,
v_1059_ying.orderId,
ROUND(IFNULL(v_1059_ying.withoutTaxIncome,0.00)-IFNULL(v_1060_ying.withoutTaxCost,0.00)-IFNULL(v_1061_ying.additionalTax,0.00)-IFNULL(v_1063_ying.stampDuty
,0.00)-(IFNULL(v_1027.hsqyFee,0.00)+IFNULL(v_1027.hssyFee,0.00)+IFNULL(v_1027.hshyFee,0.00))/1.11-IFNULL(v_1027.superviseFee,0.00)/1.06-IFNULL(v_1027.serviceFee,0.00)/1.06-IFNULL(v_1027.businessFee ,0.00),2)as opreationCrossProfile
from v_1059_ying
     left join v_1060_ying on v_1059_ying.hsId=v_1060_ying.hsId
     left join v_1061_ying  on v_1059_ying.hsId=v_1061_ying.hsId
     left join v_1063_ying  on v_1059_ying.hsId=v_1063_ying.hsId
     left join v_1027  on v_1059_ying.hsId=v_1027.hsId
group by hsId, orderId;


--1065	单吨毛利	crossProfileATon	计算	毛利表	【1064】经营毛利 / 【1055】毛利结算量

create view v_1065_ying as
select
v_1064_ying.hsId,
v_1064_ying.orderId,
ROUND(IFNULL(v_1064_ying.opreationCrossProfile ,0.00)/ IFNULL(v_1055.settleGrossProfileNum ,0.00),2)as crossProfileATon
from v_1064_ying
     left join v_1055  on v_1064_ying.hsId=v_1055.hsId
group by hsId, orderId;

create view v_1065_cang as
select
v_1064_cang.hsId,
v_1064_cang.orderId,
ROUND(IFNULL(v_1064_cang.opreationCrossProfile,0.00) / IFNULL(v_3003.totalOutstorageNum ,0.00),2)as crossProfileATon
from v_1064_cang
     left join v_3003  on v_1064_cang.hsId=v_3003.hsId
group by hsId, orderId;


--1066	自有资金占压	ownerCapitalPressure	计算	占压表	【1048】自有资金付款金额-【1046】采购货款总额
create view v_1066_cang as
select
v_1048_cang.hsId,
v_1048_cang.orderId,
ROUND(IFNULL(v_1048_cang.ownerCapitalPaymentAmount,0.00)-IFNULL(v_1046_cang.purchaseCargoAmountofMoney ,0.00),2)as ownerCapitalPressure
from v_1048_cang
     left join v_1046_cang on v_1048_cang.hsId=v_1046_cang.hsId
group by hsId,orderId;


create view v_1066_ying as
select
v_1048_ying.hsId,
v_1048_ying.orderId,
ROUND(IFNULL(v_1048_ying.ownerCapitalPaymentAmount,0.00)-IFNULL(v_1046_ying.purchaseCargoAmountofMoney ,0.00),2)as ownerCapitalPressure
from v_1048_ying
     left join v_1046_ying on v_1048_ying.hsId=v_1046_ying.hsId
group by hsId,orderId;



--2010	下游已结算未回款金额	settledDownstreamHuikuanMoneny	计算	占压表	【1050】下游占压总额-【1043】买方未结算金额+【1054】预收款
create view v_2010_cang as
select
v_1050_cang.hsId,
v_1050_cang.orderId,
ROUND(IFNULL(v_1050_cang.downstreamCapitalPressure ,0.00)-IFNULL(v_1041_cang.unsettlerBuyerMoneyAmount ,0.00)+IFNULL(v_1054_cang.cangPrePayment  ,0.00),2)as settledDownstreamHuikuanMoneny
from v_1050_cang
     left join v_1041_cang on v_1041_cang.hsId=v_1050_cang.hsId
     left join v_1054_cang on v_1054_cang.hsId=v_1050_cang.hsId
group by hsId, orderId;

create view v_2010_ying as
select v_1050_ying.hsId,
       v_1050_ying.orderId,
      ROUND(IFNULL( v_1050_ying.downstreamCapitalPressure ,0.00)-IFNULL(v_1041_ying.unsettlerBuyerMoneyAmount ,0.00)+IFNULL(v_1054_ying.yingPrePayment  ,0.00),2)as settledDownstreamHuikuanMoneny
from v_1050_ying
     left join v_1041_ying on v_1041_ying.hsId=v_1050_ying.hsId
     left join v_1054_ying on v_1054_ying.hsId=v_1050_ying.hsId
group by hsId, orderId;

create  view base as
select
config.id as hsId,
config.orderId
from hs_same_order orders
     left join hs_same_order_config config on orders.id=config.orderId
group by config.id,orderId;


--仓押数据
create view hs_cangDatabaseAnalysis as
select
temp.hsId,
temp.orderId,
v_1001.totalPayTrafficFee,
v_1002.totalTradeGapFee,
v_1003.totalPaymentAmount,
v_1004.totalLoadMoney,
v_1006.totalUnrepaymentEstimateCost,
v_1007.totalRepaymentPrincipeAmount,
v_1007.totalServiceCharge,
v_1007.totalRepaymentInterest,
v_1009.totalUnpayFee,
v_1009.totalUnpayInterest,
v_1009.totalUnpayPrincipal,
v_1012.outCapitalAmout,
v_1013.totalHuikuanPaymentMoney,
v_1014.payCargoAmount,
v_1015.unpaymentMoney,
v_1016.unpaymentEstimateProfile,
v_1017.interestDays,
v_1018.actualUtilizationRate,
v_1019.rate,
v_1020.totalPaymentedRateMoney,
v_1021.contractRateProfile,
v_1022.tiexianRate,
v_1023.tiexianRateAmount,
v_1024.totalBuyerMoney,
v_1024.totalBuyerNums,
v_1024.totalBuyersettleGap,
v_1027.dsddFee,
v_1027.hshyFee,
v_1027.hsqyFee,
v_1027.hssyFee,
v_1027.businessFee,
v_1027.superviseFee,
v_1027.serviceFee,
v_1027.salesFeeAmount,
v_1037.totalCCSIntypeMoney,
v_1037.totalCSSIntypeNumber,
v_1035.tradingCompanyInTypeNum,
v_1035.tradingCompanyInTpeMoneyAmount,
v_1039.invoicedMoneyNum,
v_1039.invoicedMoneyAmount,
v_1041_cang.finalSettleAmount,
v_1041_cang.saleCargoAmountofMoney,
v_1041_cang.tradingCompanyAddMoney,
v_1041_cang.unsettlerBuyerMoneyAmount,
v_1041_cang.unsettlerBuyerNumber,
v_1045.ccsProfile,
v_1046_cang.purchaseCargoAmountofMoney,
v_1047.externalCapitalPaymentAmount,
v_1048_cang.ownerCapitalPaymentAmount,
v_1049_cang.upstreamCapitalPressure,
v_1050_cang.downstreamCapitalPressure,
v_1051_cang.cssUninTypeNum,
v_1052_cang.cssUninTypeMoney,
v_1052_cang.unInvoicedAmountofMoney,
v_1054_cang.cangPrePayment,
v_3003.totalOutstorageNum as settleGrossProfileNum,
v_1056_cang.purchaseIncludeTaxTotalAmount,
v_1057_cang.saleIncludeTaxTotalAmount,
v_1058_cang.tradeCompanyAddMoney,
v_1059_cang.withoutTaxIncome,
v_1060_cang.withoutTaxCost,
v_1061_cang.additionalTax,
v_1061_cang.vat,
v_1063_cang.stampDuty,
v_1064_cang.opreationCrossProfile,
v_1065_cang.crossProfileATon,
v_1066_cang.ownerCapitalPressure,
v_3001.totalInstorageNum,
v_3001.totalInstorageAmount,
v_3001.instorageUnitPrice,
v_3005.totalStockNum,
v_3003.totalOutstorageNum,
v_3003.totalOutstorageMoney,
v_3006.totalStockMoney
from base as temp
     left join v_1001   on v_1001.hsId=temp.hsId
     left join v_1002   on v_1002.hsId=temp.hsId
     left join v_1003   on v_1003.hsId=temp.hsId
     left join v_1004   on v_1004.hsId=temp.hsId
     left join v_1006   on v_1006.hsId=temp.hsId
     left join v_1007   on v_1007.hsId=temp.hsId
     left join v_1009   on v_1009.hsId=temp.hsId
     left join v_1012   on v_1012.hsId=temp.hsId
     left join v_1013   on v_1013.hsId=temp.hsId
     left join v_1014   on v_1014.hsId=temp.hsId
     left join v_1015   on v_1015.hsId=temp.hsId
     left join v_1016   on v_1016.hsId=temp.hsId
     left join v_1017   on v_1017.hsId=temp.hsId
     left join v_1018   on v_1018.hsId=temp.hsId
     left join v_1019   on v_1019.hsId=temp.hsId
     left join v_1020   on v_1020.hsId=temp.hsId
     left join v_1021   on v_1021.hsId=temp.hsId
     left join v_1022   on v_1022.hsId=temp.hsId
     left join v_1023   on v_1023.hsId=temp.hsId
     left join v_1024   on v_1024.hsId=temp.hsId
     left join v_1027   on v_1027.hsId=temp.hsId
     left join v_1035   on v_1035.hsId=temp.hsId
     left join v_1037   on v_1037.hsId=temp.hsId
     left join v_1039  on  v_1039.hsId=temp.hsId
     left join v_1041_cang  on  v_1041_cang.hsId=temp.hsId
     left join v_1045  on  v_1045.hsId=temp.hsId
     left join v_1046_cang  on  v_1046_cang.hsId=temp.hsId
     left join v_1047  on  v_1047.hsId=temp.hsId
     left join v_1048_cang  on  v_1048_cang.hsId=temp.hsId
     left join v_1049_cang  on  v_1049_cang.hsId=temp.hsId
     left join v_1050_cang  on  v_1050_cang.hsId=temp.hsId
     left join v_1051_cang  on  v_1051_cang.hsId=temp.hsId
     left join v_1052_cang  on  v_1052_cang.hsId=temp.hsId
     left join v_1054_cang  on  v_1054_cang.hsId=temp.hsId
     left join v_3003  on  v_3003.hsId=temp.hsId
     left join v_1056_cang  on  v_1056_cang.hsId=temp.hsId
     left join v_1057_cang  on  v_1057_cang.hsId=temp.hsId
     left join v_1058_cang  on  v_1058_cang.hsId=temp.hsId
     left join v_1059_cang  on  v_1059_cang.hsId=temp.hsId
     left join v_1060_cang  on  v_1060_cang.hsId=temp.hsId
     left join v_1061_cang  on  v_1061_cang.hsId=temp.hsId
     left join v_1063_cang  on  v_1063_cang.hsId=temp.hsId
     left join v_1064_cang  on  v_1064_cang.hsId=temp.hsId
     left join v_1065_cang  on  v_1065_cang.hsId=temp.hsId
     left join   v_1066_cang on  v_1066_cang.hsId=temp.hsId
     left join v_3001  on  v_3001.hsId=temp.hsId
     left join v_3005  on  v_3005.hsId=temp.hsId
     left join v_3006  on  v_3006.hsId=temp.hsId
group by temp.hsId,temp.orderId;



create view hs_yingDatabaseAnalysis as
select
temp.hsId,
temp.orderId,
v_1001.totalPayTrafficFee,
v_1002.totalTradeGapFee,
v_1003.totalPaymentAmount,
v_1004.totalLoadMoney,
v_1006.totalUnrepaymentEstimateCost,
v_1007.totalRepaymentPrincipeAmount,
v_1007.totalServiceCharge,
v_1007.totalRepaymentInterest,
v_1009.totalUnpayFee,
v_1009.totalUnpayInterest,
v_1009.totalUnpayPrincipal,
v_1012.outCapitalAmout,
v_1013.totalHuikuanPaymentMoney,
v_1014.payCargoAmount,
v_1015.unpaymentMoney,
v_1016.unpaymentEstimateProfile,
v_1017.interestDays,
v_1018.actualUtilizationRate,
v_1019.rate,
v_1020.totalPaymentedRateMoney,
v_1021.contractRateProfile,
v_1022.tiexianRate,
v_1023.tiexianRateAmount,
v_1024.totalBuyerMoney,
v_1024.totalBuyerNums,
v_1024.totalBuyersettleGap,
v_1027.dsddFee,
v_1027.hshyFee,
v_1027.hsqyFee,
v_1027.hssyFee,
v_1027.businessFee,
v_1027.superviseFee,
v_1027.serviceFee,
v_1027.salesFeeAmount,
v_1035.tradingCompanyInTypeNum,
v_1035.tradingCompanyInTpeMoneyAmount,
v_1037.totalCCSIntypeMoney,
v_1037.totalCSSIntypeNumber,
v_1039.invoicedMoneyNum,
v_1039.invoicedMoneyAmount,
v_1041_ying.finalSettleAmount,
v_1041_ying.saleCargoAmountofMoney,
v_1041_ying.tradingCompanyAddMoney,
v_1041_ying.unsettlerBuyerMoneyAmount,
v_1041_ying.unsettlerBuyerNumber,
v_1045.ccsProfile,
v_1046_ying.purchaseCargoAmountofMoney,
v_1047.externalCapitalPaymentAmount,
v_1048_ying.ownerCapitalPaymentAmount,
v_1049_ying.upstreamCapitalPressure,
v_1050_ying.downstreamCapitalPressure,
v_1051_ying.cssUninTypeNum,
v_1052_ying.cssUninTypeMoney,
v_1052_ying.unInvoicedAmountofMoney,
v_1054_ying.yingPrePayment,
v_1055.settleGrossProfileNum,
v_1046_ying.purchaseCargoAmountofMoney as purchaseIncludeTaxTotalAmount,
v_1041_ying.saleCargoAmountofMoney as saleIncludeTaxTotalAmount,
v_1058_ying.tradeCompanyAddMoney,
v_1059_ying.withoutTaxIncome,
v_1060_ying.withoutTaxCost,
v_1061_ying.vat,
v_1061_ying.additionalTax,
v_1063_ying.stampDuty,
v_1064_ying.opreationCrossProfile,
v_1065_ying.crossProfileATon,
v_1066_ying.ownerCapitalPressure,
v_2001.totalFayunNum,
v_2001.totalUnarriveNum,
v_2001.totalArriveNum,
v_2004.totalPayDownBail,
v_2004.totalRefundDownBail,
v_2004.totalRefundUpBail,
v_2004.totalUpstreamBail,
v_2008.balanceUpstreamBail,
v_2008.balanceDownStreamBail,
v_2010_ying.settledDownstreamHuikuanMoneny
from base as temp
     left join v_1001   on v_1001.hsId=temp.hsId
     left join v_1002   on v_1002.hsId=temp.hsId
     left join v_1003   on v_1003.hsId=temp.hsId
     left join v_1004   on v_1004.hsId=temp.hsId
     left join v_1006   on v_1006.hsId=temp.hsId
     left join v_1007   on v_1007.hsId=temp.hsId
     left join v_1009   on v_1009.hsId=temp.hsId
     left join v_1012   on v_1012.hsId=temp.hsId
     left join v_1013   on v_1013.hsId=temp.hsId
     left join v_1014   on v_1014.hsId=temp.hsId
     left join v_1015   on v_1015.hsId=temp.hsId
     left join v_1016   on v_1016.hsId=temp.hsId
     left join v_1017   on v_1017.hsId=temp.hsId
     left join v_1018   on v_1018.hsId=temp.hsId
     left join v_1019   on v_1019.hsId=temp.hsId
     left join v_1020   on v_1020.hsId=temp.hsId
     left join v_1021   on v_1021.hsId=temp.hsId
     left join v_1022   on v_1022.hsId=temp.hsId
     left join v_1023   on v_1023.hsId=temp.hsId
     left join v_1024   on v_1024.hsId=temp.hsId
     left join v_1027   on v_1027.hsId=temp.hsId
     left join v_1035   on v_1035.hsId=temp.hsId
     left join v_1037   on v_1037.hsId=temp.hsId
     left join v_1039   on  v_1039.hsId=temp.hsId
     left join v_1041_ying on  v_1041_ying.hsId=temp.hsId
     left join v_1045 on  v_1045.hsId=temp.hsId
     left join  v_1046_ying on  v_1046_ying.hsId=temp.hsId
     left join v_1047  on  v_1047.hsId=temp.hsId
     left join  v_1048_ying on  v_1048_ying.hsId=temp.hsId
     left join  v_1049_ying on  v_1049_ying.hsId=temp.hsId
     left join  v_1050_ying on  v_1050_ying.hsId=temp.hsId
     left join  v_1051_ying on  v_1051_ying.hsId=temp.hsId
     left join  v_1052_ying on  v_1052_ying.hsId=temp.hsId
     left join  v_1054_ying on  v_1054_ying.hsId=temp.hsId
     left join v_1055 on  v_1055.hsId=temp.hsId
     left join v_1058_ying on  v_1058_ying.hsId=temp.hsId
     left join v_1059_ying on  v_1059_ying.hsId=temp.hsId
     left join v_1060_ying on  v_1060_ying.hsId=temp.hsId
     left join v_1061_ying on  v_1061_ying.hsId=temp.hsId
     left join  v_1063_ying on  v_1063_ying.hsId=temp.hsId
     left join  v_1064_ying on  v_1064_ying.hsId=temp.hsId
     left join  v_1065_ying on  v_1065_ying.hsId=temp.hsId
     left join  v_1066_ying on  v_1066_ying.hsId=temp.hsId
     left join v_2001 on  v_2001.hsId=temp.hsId
     left join  v_2004 on  v_2004.hsId=temp.hsId
     left join  v_2008 on  v_2008.hsId=temp.hsId
     left join v_2010_ying on  v_2010_ying.hsId=temp.hsId
group by temp.hsId,temp.orderId;



















