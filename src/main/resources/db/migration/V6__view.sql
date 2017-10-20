use hsdb;


--101已到场数量
create view  v_101 as select
                      orderId,
                      hsId,
                      sum(hsdb.hs_ying_fayun.fyamount) as arriveAmount
                      from hsdb.hs_ying_fayun
                      where deleted = 0 and arriveStatus = 'ARRIVE'
                      group by orderId, hsId;
--102未到场到场数量
create view v_102 as select
                    orderId,
                    hsId,
                    sum(hsdb.hs_ying_fayun.fyamount)as unArriveAmount
                    from hsdb.hs_ying_fayun
                    where deleted = 0 and arriveStatus = 'UNARRIVE'
                    group by orderId, hsId;
--103
create view v_103 as select
                     v_101.orderId,
                     v_101.hsId,
                     v_101.arriveAmount + v_102.unArriveAmount as fYAmount
                     from v_101
                          left join v_102 on v_101.hsId=v_102.hsId and v_101.orderId=v_102.orderId
                     group by orderId, hsId;

--settle_buyyer 下游结算
create view settle_buyer_v1 as  select
                  orderId,
                  hsId,
                  sum(amount) as amount,
                  sum(money) as money,
                  sum(settleGap) as settleGap
               from hsdb.hs_same_settle_buyer
               where deleted = 0
               group by orderId, hsId;

create view settle_buyer as select
         a.orderId,
         a.hsId,
         a.amount as amount,
         a.money as moneny,
         a.settleGap as settleGap,
         a.amount * config.tradeAddPrice as tradeAddMoney
        from settle_buyer_v1 a
      left join hsdb.hs_same_order_config config on a.hsId = config.id
      group by a.orderId, a.orderId;


--104 核算结算量
create view  v_104 as select
   v_103.orderId,
   v_103.hsId,
   v_103.fYAmount - settle_buyer.settleGap as hsAmount
   from  v_103
   left join settle_buyer on v_103.hsId=settle_buyer.hsId and settle_buyer.orderId=v_103.orderId
   group by orderId, hsId;


 --105 贸易公司加价  核算结算量* 贸易公司加价
create view v_105 as select
         v_104.orderId,
         v_104.hsId,
         hsAmount * config.tradeAddPrice as  tradeAddMoney
         from  v_104
               left join hsdb.hs_same_order_config config on v_104.hsId = config.id
         group by orderId, hsId;

--106 汇总 运费为代收代垫运费的金额

create view  v_106 as select
       orderId,
       hsId,
       sum(amount) as amount
       from hs_same_fee
       where name='HELP_RECIVE_PAY_FEE'
       group by orderId, hsId;

--107 汇总 运费为代收代垫运费的金额

create view  v_107 as select
                      hs_same_fee.orderId,
                      hs_same_fee.hsId,
                      sum(hs_same_fee.amount) - v_106.amount as amount
                      from hs_same_fee
                      left  join  v_106  on v_106.hsId=hs_same_fee.hsId and v_106.orderId=hs_same_fee.orderId
                      group by orderId, hsId;

--108 下游已结算数量
--109 下游已结算数量
--settle_buyer 108 amount 109 money

--110 下游未结算量
create view  v_110 as select
                      v_104.orderId,
                      v_104.hsId,
                      v_104.hsAmount - settle_buyer.amount as amount
                      from v_104
                       left  join  settle_buyer  on v_104.hsId=settle_buyer.hsId and v_104.orderId=settle_buyer.orderId
                      group by orderId, hsId;

--create view  v_110 as select
--                      hs_same_fee.orderId,
--                      hs_same_fee.hsId,
--
--                      from
--                       left  join  v_106  on v_106.hsId=hs_same_fee.hsId and v_106.orderId=hs_same_fee.orderId
--                      group by orderId, hsId;

--
--create view v_all as select
--  order.id,
--  order.businessType,
--  v_101.arriveAmount
--from hs_same_order order
--left join v_101 on order.id = v_101.orderId
--left join v_102 on order.id = v_102.orderId


--select a.*, main.name from (
--
--select m_101.orderId, m_101.hsId, m_101.i101, m_102.i102 from m_101  left join m_102 on m_101.orderId = m_102.orderId and m_101.hsId = m_102.hsId
--union
--select m_102.orderId, m_102.hsId, m_101.i101, m_102.i102 from m_102  left join m_101 on m_101.orderId = m_102.orderId and m_101.hsId = m_102.hsId
--
--) a left join main on a.orderId = main.id;


