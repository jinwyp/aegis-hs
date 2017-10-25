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
sum(amount) as totalpaymentAmount
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
sum(amount*IFNULL(useInterest,0)*IFNULL(useDays,0))as LoadEstimateCost
from hs_same_jiekuan jiekuan
group by  jiekuan.orderId, jiekuan.hsId;


--还款相关 1007  1008  1011
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

--  1012 外部资金使用成本 【1006】未还款预估成本 +【1008】还款利息合计 + 【1011】还款服务费合计

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
left join v_1002 on V_1002.hsId=v_1003.hsId
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
TIMEDIFF(huikuanBankPaperExpire,huikuanBankPaperDate),
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
create view v_1027 as
select
sum(case when name='HELP_RECIVE_PAY_FEE' then amount else 0 end) as DSDDFee,
sum(case when name='TAX_MOTRO_FREIGHT' then amount else 0 end) as HSQYFee,
sum(case when name='TAX_SHIP_FREIGHT' then amount else 0 end) as HSSYFee,
sum(case when name='TAX_RAIL_FREIGHT' then amount else 0 end) as HSHYFee,
sum(case when name='SERVICE_FEE' then amount else 0 end) as serviceFee,
sum(case when name='SUPERVISE_FEE' then amount else 0 end) as superviseFee,
sum(case when name='BUSINESS_FEE' then amount else 0 end) as businessFee,
sum(case when name !='HELP_RECIVE_PAY_FEE' then amount else 0 end) as salesFeeAmount
from hs_same_fee
where  deleted=0;














--
----101已到场数量
--create view  v_101 as select
--                      orderId,
--                      hsId,
--                      sum(hsdb.hs_ying_fayun.fyamount) as arriveAmount
--                      from hsdb.hs_ying_fayun
--                      where deleted = 0 and arriveStatus = 'ARRIVE'
--                      group by orderId, hsId;
----102未到场到场数量
--create view v_102 as select
--                    orderId,
--                    hsId,
--                    sum(hsdb.hs_ying_fayun.fyamount)as unArriveAmount
--                    from hsdb.hs_ying_fayun
--                    where deleted = 0 and arriveStatus = 'UNARRIVE'
--                    group by orderId, hsId;
----103
--create view v_103 as select
--                     v_101.orderId,
--                     v_101.hsId,
--                     v_101.arriveAmount + v_102.unArriveAmount as fYAmount
--                     from v_101
--                          left join v_102 on v_101.hsId=v_102.hsId and v_101.orderId=v_102.orderId
--                     group by orderId, hsId;
--
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
