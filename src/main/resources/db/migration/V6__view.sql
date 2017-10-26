use hsdb;
--运费相关 付款运费金额
create view v_1001 as
                   select
                   orderId,
                   hsId,
                   sum(payAmount) as totalPayTrafficFee
                   from hs_same_fukuan
                   where payUsage= 'FREIGNHT' and deleted=0
                   group by  orderId, hsId;


create view v_1002 as
select
orderId,
hsId,
sum(payAmount) as totalTradeGapFee
from hs_same_fukuan
where payUsage= 'TRADE_DEFICIT' and deleted=0
group by  orderId, hsId;


create view v_1003 as
select
orderId,
hsId,
sum(payAmount) as totalpaymentAmount
from hs_same_fukuan
where deleted=0
group by  orderId, hsId;


--借款合计

create view v_1004 as
select
orderId,
hsId,
sum(amount) as totalLoadMoney
from hs_same_jiekuan
where deleted=0
group by  orderId, hsId;

--借款预估成本
create view v_1005 as
select
orderId,
hsId,
amount*IFNULL(useInterest,0)*IFNULL(useDays,0) as LoadEstimateCost
from hs_same_jiekuan
where deleted=0
group by  orderId, hsId;


--查询未对应的借款
--create view temp_jiekuan as


--未还款预估成本 todo
create view v_1006 as
select
jiekuan.orderId,
jiekuan.hsId,
sum(amount*IFNULL(useInterest,0)*IFNULL(useDays,0))as LoadEstimateCost,
sum(amount) as totalUnrepaymentEstimateCost
from hs_same_jiekuan jiekuan
group by  jiekuan.orderId, jiekuan.hsId;


--还款相关 1007  1008  1011
--1007	还款本金合计	totalRepaymentPrincipeAmount	【还款】		汇总：还款本金
--1008	还款利息合计	totalrepaymentInterest	【还款】		汇总：还款利息
--1011	还款服务费合计	totalserviceCharge	【还款】		汇总：还款服务费
create view v_1007 as
select
huankuan.orderId,
huankuan.hsId,
sum(map.principal) as totalRepaymentPrincipeAmount,
sum(map.interest) as  totalrepaymentInterest,
sum(map.fee) as  totalrepaymentFee
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
sum(map.principal) as totalUnpayPrincipal,
sum(map.interest) as  totalUnpayInterest,
sum(map.fee) as  totalUnpayFee
from hs_same_huankuan huankuan
left join hs_same_huankuan_map map on huankuan.id= map.huankuanId
where huankuan.deleted=0 and promise=true
group by  orderId, hsId;

--  1012 外部资金使用成本   【1006】未还款预估成本 +【1008】还款利息合计 + 【1011】还款服务费合计


create view v_1012 as
select
v_1006.orderId,
v_1006.hsId,
v_1006.totalUnrepaymentEstimateCost-v_1007.totalrepaymentInterest-v_1007.totalrepaymentFee as outCapitalAmout
from v_1006
left join v_1007 on  v_1006.hsId=v_1007.hsId and v_1006.orderId=v_1007.orderId;


--1013 已回款金额  【1006】未还款预估成本 +【1008】还款利息合计 + 【1011】还款服务费合计
create view v_1013 as
select
orderId,
hsId,
sum(huikuanAmount) as totalpaymentMoney
from hs_same_huikuan
where deleted =0
group by  orderId, hsId;

--付货款金额
create view v_1014 as
select
v_1003.orderId,
v_1003.hsId,
v_1003.totalpaymentAmount-v_1001.totalPayTrafficFee-v_1002.totalTradeGapFee as payCargoAmount
from v_1003
left join v_1001 on v_1001.hsId =v_1003.hsId
left join v_1002 on v_1002.hsId=v_1003.hsId
group by  orderId, hsId;


--1005未回款金额

create view v_1015 as
select
v_1014.orderId,
v_1014.hsId,
case  when v_1014.payCargoAmount > v_1013.totalpaymentMoney
THEN  v_1014.payCargoAmount - v_1013.totalpaymentMoney
ELSE 0 end  as unpaymentMoney

from v_1014
     left JOIN v_1013  on v_1014.hsId = v_1013.hsId
group by orderId,hsId;

--1016 未回款金额预估收益

create view v_1016 as
select
v_1015.orderId,
v_1015.hsId,
v_1015.unpaymentMoney * config.contractBaseInterest * config.expectHKDays / 360 as unpaymentEstimateProfile
from v_1015
     left JOIN hs_same_order_config config  on config.id = v_1015.hsId
group by orderId,hsId;


--1017 计息天数  每条回款-付款记录：计息天数 = 回款日期 - 付款日期 - 【买方结算】折扣天数

create view v_1017 as
select
huikuan.orderId,
huikuan.hsId,
datediff(huikuan.huikuanDate,fukuan.payDate) -IFNULL(seller.discountDays,0) as interestDays
from hs_same_huikuan huikuan
     left join hs_same_huikuan_map map on map.huikuanId =huikuan.id
     left join hs_same_fukuan  fukuan on fukuan.id=map.fukuanId
     left join hs_same_settle_seller seller on huikuan.orderId=seller.orderId
group by orderId,hsId;

--1018 实际使用率
create view v_1018 as
select
seller.orderId,
seller.hsId,
config.contractBaseInterest- IFNULL(seller.discountInterest,0) as actualUtilizationRate
from hs_same_order_config config
     left join hs_same_settle_seller seller on config.id=seller.hsId
group by orderId,hsId;

--1019 应计利息
create view v_1019 as
select
huikuan.orderId,
huikuan.hsId,
CASE WHEN v_1017.interestDays>0  AND v_1017.interestDays<60
THEN map.amount*v_1017.interestDays*v_1018.actualUtilizationRate/360
WHEN v_1017.interestDays>=60  AND v_1017.interestDays<90
THEN map.amount*((v_1017.interestDays-60)*(v_1018.actualUtilizationRate+0.05)+60*v_1018.actualUtilizationRate)/360
WHEN v_1017.interestDays>=90
THEN map.amount*((v_1017.interestDays-90)*(v_1018.actualUtilizationRate+0.05)+30*(v_1018.actualUtilizationRate+0.05)+60*v_1018.actualUtilizationRate)/360
ELSE 0 END as rate

from hs_same_huikuan huikuan
     left join hs_same_huikuan_map map on map.huikuanId =huikuan.id
     left join hs_same_fukuan  fukuan on fukuan.id=map.fukuanId
     left join v_1017 on huikuan.hsId=v_1017.hsId
     left join v_1018 on huikuan.hsId=v_1018.hsId
group by orderId,hsId;


--1020 已回款应计利息合计
create view v_1020 as
select
orderId,
hsId,
sum(rate) as totalPaymentedRateMoney
from v_1019
group by orderId,hsId;


--1021
create view v_1021 as
select
v_1016.orderId,
v_1016.hsId,
IFNULL(v_1016.unpaymentEstimateProfile+v_1020.totalPaymentedRateMoney-IFNULL(seller.discountAmount,0),0) as contractRateProfile
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
THEN TIMEDIFF(huikuanBankPaperExpire,huikuanBankPaperDate)/24 *huikuanAmount * IFNULL(huikuanBankDiscountRate,0)*1.17/360
ELSE 0 END as tiexianRate
from hs_same_huikuan
where deleted= 0;


--1023 贴现息合计
create  view v_1023 as
select
orderId,
hsId,
sum(tiexianRate) as tiexianRateAmount
from v_1022
group by orderId,hsId;

--1024 买方结算   1024  1025 1026
create view v_1024 as
select
orderId,
hsId,
sum(amount) as totalBuyerNums,
sum(money) as totalBuyerMoney,
sum(settleGap) as totalBuyersettleGap
from hs_same_settle_buyer
where deleted=0
group by orderId,hsId;

--1027 费用相关 1027->1034

--1027	代收代垫运费	DSDDFee	【费用】	毛利表	汇总：费用科目 = “代收代垫运费”的费用金额
--1028	含税汽运费	HSQYFee	【费用】	毛利表	汇总：费用科目 = “含税汽运费”的费用金额
--1029	含税水运费	HSSYFee	【费用】	毛利表	汇总：费用科目 = “含税水运费”的费用金额
--1030	含税火运费	HSHYFee	【费用】	毛利表	汇总：费用科目 = “含税火运费”的费用金额
--1031	服务费	serviceFee	【费用】	毛利表	汇总：费用科目 = “监管费”的费用金额
--1032	管理费	superviseFee	【费用】	毛利表	汇总：费用科目 = “管理费”的费用金额
--1033	业务费	businessFee	【费用】	毛利表	汇总：费用科目 = “业务费”的费用金额
--1034	销售费用总额	salesFeeAmount	【1028】含税汽运费 + 【1029】含税水运费 + 【1030】含税火运费 + 【1031】监管费 + 【1032】管理费 + 【1033】业务费
create view v_1027 as
select
orderId,
hsId,
sum(case when name='HELP_RECIVE_PAY_FEE' then amount else 0 end) as DSDDFee,
sum(case when name='TAX_MOTRO_FREIGHT' then amount else 0 end) as HSQYFee,
sum(case when name='TAX_SHIP_FREIGHT' then amount else 0 end) as HSSYFee,
sum(case when name='TAX_RAIL_FREIGHT' then amount else 0 end) as HSHYFee,
sum(case when name='SERVICE_FEE' then amount else 0 end) as serviceFee,
sum(case when name='SUPERVISE_FEE' then amount else 0 end) as superviseFee,
sum(case when name='BUSINESS_FEE' then amount else 0 end) as businessFee,
sum(case when name !='HELP_RECIVE_PAY_FEE' then amount else 0 end) as salesFeeAmount
from hs_same_fee
where  deleted=0
group by orderId,hsId;

--发票相关
--1035 贸易公司已收到的进项数量 金额 --1035  1036

--1037 ccs一手到进项数量
create view v_1037_child1 as
select
invoiceId ,
sum(priceAndTax) as totalPriceTax,
sum(cargoAmount) as totalAmount
from hs_same_invoice_detail
where deleted=0
group by invoiceId ;

create view v_1037_child2 as
select *
from hs_same_invoice invoice
left join v_1037_child1
on invoice.id=v_1037_child1.invoiceId
where invoice.invoiceDirection='INCOME'and deleted=0;


--1037Css已收到进项数量   1038Css已收到进项金额
create view v_1037 as
select
hsId,
orderId,
sum(totalAmount) as totalCSSInTypeNumber,
sum(totalPriceTax) as totalCCSInTypeMoney
from
v_1037_child2 as aa
left JOIN hs_same_order orderSame on aa.orderId =orderSame.id
where aa.openCompanyId =orderSame.mainAccounting and aa.deleted =0
group by hsId,orderId;


--1039占压表已开票金额

create view v_1039 as
select
hsId,
orderId,
sum(totalAmount) as InvoicedMoneyNum,
sum(totalPriceTax) as InvoicedMoneyAmount
from
v_1037_child2 as aa
left JOIN hs_same_order orderSame on aa.orderId =orderSame.id
where aa.openCompanyId =orderSame.upstreamId and aa.deleted =0
group by hsId,orderId;



--v_2001 已到场数量  未到场数量  发运总数量
create view  v_2001 as select
                       orderId,
                       hsId,
                       case when  arriveStatus = 'ARRIVE' then sum(fyamount) else 0 end as  totalUnarriveNum,
                       case when  arriveStatus = 'UNARRIVE' then sum(fyamount) else 0 end as  totalarriveNum,
                       sum(hsdb.hs_ying_fayun.fyamount) as totalFayunNum
                       from hsdb.hs_ying_fayun
                       where deleted = 0
                       group by orderId, hsId;

--2004  保证金相关
--2004收上游保证金	totalUpstream	【保证金】		汇总： 保证金类型 = “收上游保证金”的保证金金额
--2005退上游保证金	totalRefundUpBail	【保证金】		汇总： 保证金类型 = “退上游保证金”的保证金金额
--2006付下游保证金	totalPayDownBail	【保证金】		汇总： 保证金类型 = “付下游保证金”的保证金金额
--2007下游退保证金	totalRefundDownBail	【保证金】		汇总： 保证金类型 = “下游退保证金”的保证金金额
create view v_2004 as
select
hsId,
orderId,
sum(case when bailType='RECV_UP' then bailAmount else 0 end) as totalUpstream,
sum(case when bailType='BACK_UP' then bailAmount else 0 end) as totalRefundUpBail,
sum(case when bailType='PAY_DOWN' then bailAmount else 0 end) as totalPayDownBail,
sum(case when bailType='BACK_DOWN' then bailAmount else 0 end) as totalRefundDownBail
from hs_ying_bail
where  deleted=0
group by hsId,OrderId;

--2008	上游保证金余额	balanceUpstreamBail	计算		【2004】收上游保证金 - 【2005】退上游保证金
--2009	下游保证金余额	balanceDownStreamBail	计算		【2006】付下游保证金 - 【2007】下游退保证金
create view v_2008 as
select
hsId,
orderId,
totalUpstream-totalRefundUpBail as balanceUpstreamBail,
totalPayDownBail-totalRefundDownBail as balanceDownStreamBail
from v_2004
group by hsId,OrderId;





--出库入库相关
--3001	入库总数量	totalInstorageNum	【入库】	备查账	汇总：入库数量
--3002	入库总金额	totalInstorageAmount	【入库】		汇总：入库金额
--3004	入库单价	InstorageUnitPrice	计算		【3002】入库总金额 ／ 【3001】入库总数量
create view v_3001  as
select
hsId,
orderId,
sum(rukuAmount) as totalInstorageNum,
sum(rukuAmount * rukuPrice) as totalInstorageAmount,
sum(rukuAmount * rukuPrice)/sum(rukuAmount) as InstorageUnitPrice
from hs_cang_ruku
group by hsId, orderId;

--3003	已出库数量	totaloutstorageNum	【出库】	备查账	汇总：出库数量
--3005	库存数量	totalStockNum	计算	备查账/占压	【3001】入库总数量 - 【3003】已出库数量
create view v_3003  as
select
v_3001.hsId,
v_3001.orderId,
sum(chukuAmount) as totaloutstorageNum,
v_3001.totalInstorageNum-sum(chukuAmount) as totalStockNum
from hs_cang_chuku chuku
     left join v_3001 on v_3001.hsId=v_3001.hsId
group by hsId, orderId;

--3006	库存金额	totalStockMoney	计算	占压	【3004】入库单价 * 【3005】库存数量
create view v_3006  as
select
v_3001.hsId,
v_3001.orderId,
v_3001.InstorageUnitPrice *v_3003.totalStockNum as totalStockMoney
from v_3003
     left join v_3001 on v_3001.hsId=v_3003.hsId
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
v_3001.totalInstorageNum as finalSettleAmount,
v_3001.totalInstorageNum-v_1024.totalBuyerNums as  unsettlerBuyerNumber,
v_3001.totalInstorageNum * config.tradeAddPrice as tradingCompanyAddMoney,
(v_3001.totalInstorageNum-v_1024.totalBuyerNums) * config.weightedPrice as unsettlerBuyerMoneyAmount,
(v_3001.totalInstorageNum-v_1024.totalBuyerNums) * config.weightedPrice +v_1024.totalBuyerMoney as saleCargoAmountofMoney
from v_3001
     left join v_1024 on v_3001.hsId=v_1024.hsId
     left join hs_same_order_config config on config.id=v_3001.hsId
group by hsId, orderId;

--应收
create view v_1041_ying as
select
v_2001.hsId,
v_2001.orderId,
v_2001.totalFayunNum-v_1024.totalBuyersettleGap as finalSettleAmount,
v_2001.totalFayunNum-v_1024.totalBuyersettleGap-v_1024.totalBuyerNums as  unsettlerBuyerNumber,
(v_2001.totalFayunNum-v_1024.totalBuyersettleGap) * config.tradeAddPrice as tradingCompanyAddMoney,
(v_2001.totalFayunNum-v_1024.totalBuyersettleGap-v_1024.totalBuyerNums) * config.weightedPrice as unsettlerBuyerMoneyAmount,
(v_2001.totalFayunNum-v_1024.totalBuyersettleGap-v_1024.totalBuyerNums) * config.weightedPrice +v_1024.totalBuyerMoney as saleCargoAmountofMoney
from v_2001
     left join v_1024 on v_2001.hsId=v_1024.hsId
     left join hs_same_order_config config on config.id=v_2001.hsId
group by hsId, orderId;




--1045 瑞茂通总收益  【1021】合同利息收益 - 【1012】外部资金使用成本 + 【1023】贴现息合计
create view v_1045 as
select
v_1021.hsId,
v_1021.orderId,
v_1021.contractRateProfile-v_1012.outCapitalAmout+v_1023.tiexianRateAmount as CCSProfile
from v_1021
left join v_1012 on v_1021.hsId=v_1012.hsId
left join v_1023 on v_1021.hsId=v_1023.hsId
group by hsId, orderId;


--1046 采购货款总额 【1044】销售货款总额 - 【1045】瑞茂通总收益
create view v_1046_ying as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
v_1041_ying.saleCargoAmountofMoney-v_1045.CCSProfile as purchaseCargoAmountofMoney
from v_1041_ying
     left join v_1045 on v_1041_ying.hsId=v_1045.hsId
group by hsId, orderId;


create view v_1046_cang as
select
v_1041_cang.hsId,
v_1041_cang.orderId,
v_1041_cang.saleCargoAmountofMoney-v_1045.CCSProfile as purchaseCargoAmountofMoney
from v_1041_cang
     left join v_1045 on v_1041_cang.hsId=v_1045.hsId
group by hsId, orderId;

--1047 外部资金使用率  【1004】借款金额合计 - 【1007】还款本金合计  + 【1009】登记未还款本金
create view v_1047 as
select
v_1004.hsId,
v_1004.orderId,
v_1004.totalLoadMoney-v_1007.totalRepaymentPrincipeAmount+v_1009.totalUnpayPrincipal as externalCapitalPaymentAmount
from v_1004
     left join v_1007 on v_1004.hsId=v_1007.hsId
     left join v_1009 on v_1009.hsId=v_1007.hsId
group by hsId, orderId;

--1048 ownerCapitalPaymentAmount 自由资金付款金额【1003】付款金额合计 - 【1047】外部资金付款金额 + 【1008】还款利息合计  -【1010】登记未还款利息 - 【2008】上游保证金余额
create view v_1048_ying as
select
v_1003.hsId,
v_1003.orderId,
v_1003.totalpaymentAmount-v_1047.externalCapitalPaymentAmount+v_1007.totalrepaymentInterest+v_1009.totalUnpayInterest-v_2008.balanceUpstreamBail as ownerCapitalPaymentAmount
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
v_1003.totalpaymentAmount-v_1047.externalCapitalPaymentAmount as ownerCapitalPaymentAmount
from v_1003
     left join v_1047 on v_1003.hsId=v_1047.hsId
group by hsId, orderId;

--1049  上游资金占压	upstreamCapitalPressure	计算	备查账／占压表	【1047】外部资金付款金额 + 【1048】自有资金付款金额 - 【1046】采购货款总额
create view v_1049_ying as
select
v_1047.hsId,
v_1047.orderId,
v_1047.externalCapitalPaymentAmount+v_1048_ying.ownerCapitalPaymentAmount-v_1046_ying.purchaseCargoAmountofMoney as upstreamCapitalPressure
from v_1047
     left join v_1048_ying on v_1048_ying.hsId=v_1047.hsId
     left join v_1046_ying on v_1046_ying.hsId=v_1047.hsId
group by hsId, orderId;

create view v_1049_cang as
select
v_1047.hsId,
v_1047.orderId,
v_1047.externalCapitalPaymentAmount+v_1048_cang.ownerCapitalPaymentAmount-v_1046_cang.purchaseCargoAmountofMoney as upstreamCapitalPressure
from v_1047
     left join v_1048_cang on v_1048_cang.hsId=v_1047.hsId
     left join v_1046_cang on v_1046_cang.hsId=v_1047.hsId
group by hsId, orderId;
--1050 下游资金占压	downstreamCapitalPressure	计算	备查账／占压表	【1044】销售货款总额 - 【1013】已回款金额 +【2009】下游保证金余额

create view v_1050_ying as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
v_1041_ying.saleCargoAmountofMoney-v_1013.totalpaymentMoney+v_2008.balanceDownStreamBail as downstreamCapitalPressure
from v_1041_ying
     left join v_1013 on v_1041_ying.hsId=v_1013.hsId
     left join v_2008 on v_1041_ying.hsId=v_2008.hsId
group by hsId, orderId;

--【1044】销售货款总额 - 【1013】已回款金额
create view v_1050_cang as
select
v_1041_cang.hsId,
v_1041_cang.orderId,
v_1041_cang.saleCargoAmountofMoney-v_1013.totalpaymentMoney as downstreamCapitalPressure
from v_1041_cang
     left join v_1013 on v_1041_cang.hsId=v_1013.hsId
     left join v_2008 on v_1041_cang.hsId=v_2008.hsId
group by hsId, orderId;


--1051 CCS未收到进项数量	CCSUnInTypeNum	计算	备查账	【1040】核算结算量 - 【1037】CCS已收进项数量

create view v_1051_ying as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
v_1041_ying.finalSettleAmount-v_1037.totalCSSInTypeNumber as CCSUnInTypeNum
from v_1041_ying
     left join v_1037 on v_1037.hsId=v_1041_ying.hsId
group by hsId, orderId;

create view v_1051_cang as
select
v_1041_cang.hsId,
v_1041_cang.orderId,
v_1041_cang.finalSettleAmount-v_1037.totalCSSInTypeNumber as CCSUnInTypeNum
from v_1041_cang
     left join v_1037 on v_1037.hsId=v_1041_cang.hsId
group by hsId, orderId;


--1052 CCS未收到进项金额	CCSUnInTypeMoney	     	【1046】采购货款总额 + 【1041】贸易公司加价 - 【1038】CCS已收进项金额 - 【1027】代收代垫运费
--1053	占压表未开票金额	unInvoicedAmountofMoney		【1046】采购货款总额 + 【1041】贸易公司加价 - 【1039】占压表已开票金额 - 【1027】代收代垫运费
create view v_1052_ying  as
select
v_1046_ying.hsId,
v_1046_ying.orderId,
v_1046_ying.purchaseCargoAmountofMoney+v_1041_ying.tradingCompanyAddMoney-v_1037.totalCCSInTypeMoney-v_1027.DSDDFee as CCSUnInTypeMoney,
v_1046_ying.purchaseCargoAmountofMoney+v_1041_ying.tradingCompanyAddMoney-v_1039.InvoicedMoneyAmount-v_1027.DSDDFee as unInvoicedAmountofMoney
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
v_1046_cang.purchaseCargoAmountofMoney+v_1041_cang.tradingCompanyAddMoney-v_1037.totalCCSInTypeMoney-v_1027.DSDDFee as CCSUnInTypeMoney,
v_1046_cang.purchaseCargoAmountofMoney+v_1041_cang.tradingCompanyAddMoney-v_1039.InvoicedMoneyAmount-v_1027.DSDDFee as unInvoicedAmountofMoney
from v_1046_cang
     left join v_1041_cang on v_1046_cang.hsId=v_1041_cang.hsId
     left join v_1037 on v_1037.hsId=v_1046_cang.hsId
     left join v_1027 on v_1027.hsId=v_1046_cang.hsId
     left join v_1039 on v_1039.hsId=v_1046_cang.hsId
group by hsId, orderId;

--1054	预收款	yingPrePayment	计算	占压表	IF（【1014】付货款金额 <= 【1013】已回款金额）？【1014】付货款金额 - 【1013】已回款金额 + 【2009】下游保证金余额  ：0 +【2009】下游保证金余额
--1054	预收款	cangPrePayment	计算	占压表	IF（【1014】付货款金额 <= 【1013】已回款金额？【1014】付货款金额 - 【1013】已回款金额 ：0 ）
create view v_1054  as
select
v_1014.hsId,
v_1014.orderId,
case when v_1014.payCargoAmount <= v_1013.totalpaymentMoney then v_1014.payCargoAmount- v_1013.totalpaymentMoney +v_2008.balanceDownStreamBail else v_2008.balanceDownStreamBail end as yingPrePayment,
case when v_1014.payCargoAmount <= v_1013.totalpaymentMoney then v_1014.payCargoAmount- v_1013.totalpaymentMoney +v_2008.balanceDownStreamBail else v_2008.balanceDownStreamBail end as cangPrePayment
from v_1014
     left join v_1013 on v_1013.hsId=v_1014.hsId
     left join v_2008 on v_2008.hsId=v_1014.hsId
group by hsId, orderId;





--1055 毛利结算量	settleGrossProfileNum	计算	毛利表	【1040】核算用结算量
--仓押毛利结算量 【3003】已出库数量
create view v_1055 as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
v_1041_ying.finalSettleAmount as settleGrossProfileNum
from v_1041_ying
group by hsId, orderId;

--1056 yingshou采购含税总额	purchaseIncludeTaxTotalAmount	计算	毛利表	【1046】采购货款总额
-- 仓押采购含税总额   purchaseIncludeTaxTotalAmount【3004】入库单价 * 【1055】毛利结算量
create view v_1056_cang  as
select
v_3001.hsId,
v_3001.orderId,
v_3001.InstorageUnitPrice * v_3003.totaloutstorageNum  as purchaseIncludeTaxTotalAmount
from v_3001
left join v_3003 on  v_3001.hsId=v_3003.hsId
group by hsId, orderId;

--1057	仓押销售含税总额	saleIncludeTaxTotalAmount	【1056】采购含税总额 + 【1045】瑞茂通总收益
--1057  应收 【1044】销售货款总额
create view v_1057_cang  as
select
v_1056_cang.hsId,
v_1056_cang.orderId,
v_1056_cang.purchaseIncludeTaxTotalAmount * v_1045.CCSProfile  as saleIncludeTaxTotalAmount
from v_1056_cang
     left join v_1045 on  v_1056_cang.hsId=v_1045.hsId
group by hsId, orderId;

--1058	毛利贸易公司加价	TradeCompanyAddMoney	计算	毛利表	【1055】毛利结算量 * 【核算月配置】贸易公司加价
create view v_1058_ying  as
select
v_1055.hsId,
v_1055.orderId,
v_1055.settleGrossProfileNum * config.tradeAddPrice  as TradeCompanyAddMoney
from v_1055
     left join hs_same_order_config config on  v_1055.hsId=config.id
group by hsId, orderId;

create view v_1058_cang  as
select
v_3003.hsId,
v_3003.orderId,
v_3003.totaloutstorageNum * config.tradeAddPrice  as TradeCompanyAddMoney
from v_3003
     left join hs_same_order_config config on  v_3003.hsId=config.id
group by hsId, orderId;
--1059	不含税收入	withoutTaxIncome	计算	毛利表	（【1057】销售含税总额 - 【1027】代收代垫运费 ）／ 1.17
create view v_1059_ying  as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
(v_1041_ying.saleCargoAmountofMoney - v_1027.DSDDFee)/1.17  as withoutTaxIncome
from v_1041_ying
     left join v_1027  on  v_1027.hsId=v_1041_ying.hsId
group by hsId, orderId;

create view v_1059_cang  as
select
v_1057_cang.hsId,
v_1057_cang.orderId,
(v_1057_cang.saleIncludeTaxTotalAmount - v_1027.DSDDFee)/1.17  as withoutTaxIncome
from v_1057_cang
     left join v_1027  on  v_1027.hsId=v_1057_cang.hsId
group by hsId, orderId;

--1060	不含税成本	withoutTaxCost	计算	毛利表	（【1056】采购含税总额 - 【1027】代收代垫运费 + 【1058】毛利贸易公司加价）／1.17
create view v_1060_cang  as
select
v_1056_cang.hsId,
v_1056_cang.orderId,
(v_1056_cang.purchaseIncludeTaxTotalAmount - v_1027.DSDDFee+v_1058_cang.TradeCompanyAddMoney)/1.17  as withoutTaxCost
from v_1056_cang
     left join v_1027  on  v_1027.hsId=v_1056_cang.hsId
     left join v_1058_cang  on  v_1056_cang.hsId=v_1058_cang.hsId
group by hsId, orderId;


create view v_1060_ying  as
select
v_1046_ying.hsId,
v_1046_ying.orderId,
(v_1046_ying.purchaseCargoAmountofMoney - v_1027.DSDDFee+v_1058_ying.TradeCompanyAddMoney)/1.17  as withoutTaxCost
from v_1046_ying
     left join v_1027  on  v_1027.hsId=v_1046_ying.hsId
     left join v_1058_ying  on  v_1046_ying.hsId=v_1058_ying.hsId
group by hsId, orderId;

--1061	应交增值税	VAT	计算	毛利表	IF（【1059】不含税收入 <= 【1060】不含税成本？0 : （【1059】不含税收入 - 【1060】不含税成本）*0.17）
--1062	税金及附加	additionalTax	计算	毛利表	【1061】应交增值税 * 0.12
create view v_1061_cang  as
select
v_1059_cang.hsId,
v_1059_cang.orderId,
IFNULL(
case when v_1059_cang.withoutTaxIncome <= v_1060_cang.withoutTaxCost then 0 else (v_1059_cang.withoutTaxIncome -v_1060_cang.withoutTaxCost)*0.17 end,
0) as cang_VAT,
IFNULL(
case when v_1059_cang.withoutTaxIncome <= v_1060_cang.withoutTaxCost then 0 else (v_1059_cang.withoutTaxIncome -v_1060_cang.withoutTaxCost)*0.17*0.12 end,
0) as cangAdditionalTax
from v_1059_cang
     left join v_1060_cang  on  v_1060_cang.hsId = v_1059_cang.hsId
group by hsId, orderId;

create view v_1061_ying  as
select
v_1059_ying.hsId,
v_1059_ying.orderId,
IFNULL(
case when v_1059_ying.withoutTaxIncome <= v_1060_ying.withoutTaxCost then 0 else (v_1059_ying.withoutTaxIncome -v_1060_ying.withoutTaxCost)*0.17 end,
0) as ying_VAT,
IFNULL(
case when v_1059_ying.withoutTaxIncome <= v_1060_ying.withoutTaxCost then 0 else (v_1059_ying.withoutTaxIncome -v_1060_ying.withoutTaxCost)*0.17 end,
0) as yingAdditionalTax
from v_1059_ying
     left join v_1060_ying  on  v_1060_ying.hsId = v_1059_ying.hsId
group by hsId, orderId;


--1063	印花税	stampDuty	计算	毛利表	（【1057】销售含税总额 + 【1056】采购含税总额 + 【1058】毛利贸易公司加价 - 【1027】代收代垫运费 * 2 ） * 0.0003
create view v_1063_cang  as
select
v_1057_cang.hsId,
v_1057_cang.orderId,
(v_1057_cang.saleIncludeTaxTotalAmount +v_1056_cang.purchaseIncludeTaxTotalAmount+v_1058_cang.TradeCompanyAddMoney- v_1027.DSDDFee*2)/1.17  as stampDuty
from v_1057_cang
     left join v_1027  on  v_1027.hsId=v_1057_cang.hsId
     left join v_1056_cang on  v_1056_cang.hsId=v_1057_cang.hsId
     left join v_1058_cang  on  v_1058_cang.hsId=v_1057_cang.hsId
group by hsId, orderId;


create view v_1063_ying  as
select
v_1041_ying.hsId,
v_1041_ying.orderId,
(v_1041_ying.saleCargoAmountofMoney +v_1046_ying.purchaseCargoAmountofMoney+v_1058_ying.TradeCompanyAddMoney- v_1027.DSDDFee*2)/1.17  as stampDuty
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
v_1059_cang.withoutTaxIncome-v_1060_cang.withoutTaxCost-v_1061_cang.cangAdditionalTax-v_1063_cang.stampDuty
-(v_1027.HSQYFee+v_1027.HSSYFee+v_1027.HSHYFee)/1.11-v_1027.superviseFee/1.06-v_1027.serviceFee/1.06-v_1027.businessFee as opreationCrossProfile
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
v_1059_ying.withoutTaxIncome-v_1060_ying.withoutTaxCost-v_1061_ying.yingAdditionalTax-v_1063_ying.stampDuty
-(v_1027.HSQYFee+v_1027.HSSYFee+v_1027.HSHYFee)/1.11-v_1027.superviseFee/1.06-v_1027.serviceFee/1.06-v_1027.businessFee as opreationCrossProfile
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
v_1064_ying.opreationCrossProfile / v_1055.settleGrossProfileNum as crossProfileATon
from v_1064_ying
     left join v_1055  on v_1064_ying.hsId=v_1055.hsId
group by hsId, orderId;

create view v_1065_cang as
select
v_1064_cang.hsId,
v_1064_cang.orderId,
v_1064_cang.opreationCrossProfile / v_3003.totaloutstorageNum as crossProfileATon
from v_1064_cang
     left join v_3003  on v_1064_cang.hsId=v_3003.hsId
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
v_1003.totalpaymentAmount,
v_1004.totalLoadMoney,
v_1005.LoadEstimateCost,
v_1006.totalUnrepaymentEstimateCost,
v_1007.totalRepaymentPrincipeAmount,
v_1007.totalrepaymentFee,
v_1007.totalrepaymentInterest,
v_1009.totalUnpayFee,
v_1009.totalUnpayInterest,
v_1009.totalUnpayPrincipal,
v_1012.outCapitalAmout,
v_1013.totalpaymentMoney,
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
v_1027.DSDDFee,
v_1027.HSHYFee,
v_1027.HSQYFee,
v_1027.HSSYFee,
v_1027.businessFee,
v_1027.superviseFee,
v_1027.serviceFee,
v_1027.salesFeeAmount,
v_1037.totalCCSInTypeMoney,
v_1037.totalCSSInTypeNumber,
v_1039.InvoicedMoneyNum,
v_1039.InvoicedMoneyAmount,
v_1041_cang.finalSettleAmount,
v_1041_cang.saleCargoAmountofMoney,
v_1041_cang.tradingCompanyAddMoney,
v_1041_cang.unsettlerBuyerMoneyAmount,
v_1041_cang.unsettlerBuyerNumber,
v_1045.CCSProfile,
v_1046_cang.purchaseCargoAmountofMoney,
v_1047.externalCapitalPaymentAmount,
v_1048_cang.ownerCapitalPaymentAmount,
v_1049_cang.upstreamCapitalPressure,
v_1050_cang.downstreamCapitalPressure,
v_1051_cang.CCSUnInTypeNum,
v_1052_cang.CCSUnInTypeMoney,
v_1052_cang.unInvoicedAmountofMoney,
v_1054.cangPrePayment,
v_3003.totaloutstorageNum as settleGrossProfileNum,
v_1056_cang.purchaseIncludeTaxTotalAmount,
v_1057_cang.saleIncludeTaxTotalAmount,
v_1058_cang.TradeCompanyAddMoney,
v_1059_cang.withoutTaxIncome,
v_1060_cang.withoutTaxCost,
v_1061_cang.cangAdditionalTax,
v_1061_cang.cang_VAT,
v_1063_cang.stampDuty,
v_1064_cang.opreationCrossProfile,
v_1065_cang.crossProfileATon,
v_3001.totalInstorageNum,
v_3001.totalInstorageAmount,
v_3001.InstorageUnitPrice,
v_3003.totalStockNum,
v_3003.totaloutstorageNum,
v_3006.totalStockMoney
from base as temp
     left join v_1001   on v_1001.hsId=temp.hsId
     left join v_1002   on v_1002.hsId=temp.hsId
     left join v_1003   on v_1003.hsId=temp.hsId
     left join v_1004   on v_1004.hsId=temp.hsId
     left join v_1005   on v_1005.hsId=temp.hsId
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
     left join v_1054  on  v_1054.hsId=temp.hsId
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
     left join v_3001  on  v_3001.hsId=temp.hsId
     left join v_3006  on  v_3006.hsId=temp.hsId
group by temp.hsId,temp.orderId;



create view yingDatabaseAnalysis as
select
temp.hsId,
temp.orderId,
v_1001.totalPayTrafficFee,
v_1002.totalTradeGapFee,
v_1003.totalpaymentAmount,
v_1004.totalLoadMoney,
v_1005.LoadEstimateCost,
v_1006.totalUnrepaymentEstimateCost,
v_1007.totalRepaymentPrincipeAmount,
v_1007.totalrepaymentFee,
v_1007.totalrepaymentInterest,
v_1009.totalUnpayFee,
v_1009.totalUnpayInterest,
v_1009.totalUnpayPrincipal,
v_1012.outCapitalAmout,
v_1013.totalpaymentMoney,
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
v_1027.DSDDFee,
v_1027.HSHYFee,
v_1027.HSQYFee,
v_1027.HSSYFee,
v_1027.businessFee,
v_1027.superviseFee,
v_1027.serviceFee,
v_1027.salesFeeAmount,
v_1037.totalCCSInTypeMoney,
v_1037.totalCSSInTypeNumber,
v_1039.InvoicedMoneyNum,
v_1039.InvoicedMoneyAmount,
v_1041_ying.finalSettleAmount,
v_1041_ying.saleCargoAmountofMoney,
v_1041_ying.tradingCompanyAddMoney,
v_1041_ying.unsettlerBuyerMoneyAmount,
v_1041_ying.unsettlerBuyerNumber,
v_1045.CCSProfile,
v_1046_ying.purchaseCargoAmountofMoney,
v_1047.externalCapitalPaymentAmount,
v_1048_ying.ownerCapitalPaymentAmount,
v_1049_ying.upstreamCapitalPressure,
v_1050_ying.downstreamCapitalPressure,
v_1051_ying.CCSUnInTypeNum,
v_1052_ying.CCSUnInTypeMoney,
v_1052_ying.unInvoicedAmountofMoney,
v_1054.yingPrePayment,
v_1055.settleGrossProfileNum,
v_1046_ying.purchaseCargoAmountofMoney as purchaseIncludeTaxTotalAmount,
v_1041_ying.saleCargoAmountofMoney as saleIncludeTaxTotalAmount,
v_1058_ying.TradeCompanyAddMoney,
v_1059_ying.withoutTaxIncome,
v_1060_ying.withoutTaxCost,
v_1061_ying.ying_VAT,
v_1061_ying.yingAdditionalTax,
v_1063_ying.stampDuty,
v_1064_ying.opreationCrossProfile,
v_1065_ying.crossProfileATon,
v_2001.totalFayunNum,
v_2001.totalUnarriveNum,
v_2001.totalarriveNum,
v_2004.totalPayDownBail,
v_2004.totalRefundDownBail,
v_2004.totalRefundUpBail,
v_2004.totalUpstream,
v_2008.balanceUpstreamBail,
v_2008.balanceDownStreamBail
from base as temp
     left join v_1001   on v_1001.hsId=temp.hsId
     left join v_1002   on v_1002.hsId=temp.hsId
     left join v_1003   on v_1003.hsId=temp.hsId
     left join v_1004   on v_1004.hsId=temp.hsId
     left join v_1005   on v_1005.hsId=temp.hsId
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
     left join  v_1054 on  v_1054.hsId=temp.hsId
     left join v_1055 on  v_1055.hsId=temp.hsId
     left join v_1058_ying on  v_1058_ying.hsId=temp.hsId
     left join v_1059_ying on  v_1059_ying.hsId=temp.hsId
     left join v_1060_ying on  v_1060_ying.hsId=temp.hsId
     left join v_1061_ying on  v_1061_ying.hsId=temp.hsId
     left join  v_1063_ying on  v_1063_ying.hsId=temp.hsId
     left join  v_1064_ying on  v_1064_ying.hsId=temp.hsId
     left join  v_1065_ying on  v_1065_ying.hsId=temp.hsId
     left join v_2001 on  v_2001.hsId=temp.hsId
     left join  v_2004 on  v_2004.hsId=temp.hsId
     left join  v_2008 on  v_2008.hsId=temp.hsId
group by temp.hsId,temp.orderId;




















----settle_buyyer 下游结算
--create view settle_buyer_v1 as  select
--                  orderId,
--                  hsId,
--                  sum(amount) as amount,
--                  sum(money) as money,
--                  sum(settleGap) as settleGap
--               from hsdb.hs_same_settle_buyer
--               where deleted = 0
--               group by orderId, hsId;
--
--create view settle_buyer as select
--         a.orderId,
--         a.hsId,
--         a.amount as amount,
--         a.money as moneny,
--         a.settleGap as settleGap,
--         a.amount * config.tradeAddPrice as tradeAddMoney
--        from settle_buyer_v1 a
--      left join hsdb.hs_same_order_config config on a.hsId = config.id
--      group by a.orderId, a.orderId;
--
--
----104 核算结算量
--create view  v_104 as select
--   v_103.orderId,
--   v_103.hsId,
--   v_103.fYAmount - settle_buyer.settleGap as hsAmount
--   from  v_103
--   left join settle_buyer on v_103.hsId=settle_buyer.hsId and settle_buyer.orderId=v_103.orderId
--   group by orderId, hsId;
--
--
-- --105 贸易公司加价  核算结算量* 贸易公司加价
--create view v_105 as select
--         v_104.orderId,
--         v_104.hsId,
--         hsAmount * config.tradeAddPrice as  tradeAddMoney
--         from  v_104
--               left join hsdb.hs_same_order_config config on v_104.hsId = config.id
--         group by orderId, hsId;
--
----106 汇总 运费为代收代垫运费的金额
--
--create view  v_106 as select
--       orderId,
--       hsId,
--       sum(amount) as amount
--       from hs_same_fee
--       where name='HELP_RECIVE_PAY_FEE'
--       group by orderId, hsId;
--
----107 汇总 运费为代收代垫运费的金额
--
--create view  v_107 as select
--                      hs_same_fee.orderId,
--                      hs_same_fee.hsId,
--                      sum(hs_same_fee.amount) - v_106.amount as amount
--                      from hs_same_fee
--                      left  join  v_106  on v_106.hsId=hs_same_fee.hsId and v_106.orderId=hs_same_fee.orderId
--                      group by orderId, hsId;
--
----108 下游已结算数量
----109 下游已结算数量
----settle_buyer 108 amount 109 money
--
----110 下游未结算量
--create view  v_110 as select
--                      v_104.orderId,
--                      v_104.hsId,
--                      v_104.hsAmount - settle_buyer.amount as amount
--                      from v_104
--                       left  join  settle_buyer  on v_104.hsId=settle_buyer.hsId and v_104.orderId=settle_buyer.orderId
--                      group by orderId, hsId;
--
----create view  v_110 as select
----                      hs_same_fee.orderId,
----                      hs_same_fee.hsId,
----
----                      from
----                       left  join  v_106  on v_106.hsId=hs_same_fee.hsId and v_106.orderId=hs_same_fee.orderId
----                      group by orderId, hsId;
--
----
----create view v_all as select
----  order.id,
----  order.businessType,
----  v_101.arriveAmount
----from hs_same_order order
----left join v_101 on order.id = v_101.orderId
----left join v_102 on order.id = v_102.orderId
--
--
----select a.*, main.name from (
----
----select m_101.orderId, m_101.hsId, m_101.i101, m_102.i102 from m_101  left join m_102 on m_101.orderId = m_102.orderId and m_101.hsId = m_102.hsId
----union
----select m_102.orderId, m_102.hsId, m_101.i101, m_102.i102 from m_102  left join m_101 on m_101.orderId = m_102.orderId and m_101.hsId = m_102.hsId
----
----) a left join main on a.orderId = main.id;
--
--
