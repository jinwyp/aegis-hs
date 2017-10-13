use hsdb;

-- 应收订单 - 发运
create table hs_ying_fayun (
  id bigint(20)                     not null auto_increment,
  orderId bigint(20)                not null comment '订单id, 业务线id',
  hsId bigint(20)                   not null comment '核算月id',
  fyDate datetime                   not null comment '发运日期',
  fyAmount  decimal(10, 2)          not null comment '发运吨数',
  arriveStatus varchar(32)                   comment '到场状态',
  upstreamTrafficMode varchar(32)   not null comment '上游运输方式',
  upstreamCars int                           comment '上游汽运情况下的车数',
  upstreamJHH varchar(64)                    comment '上游火运情况下的计划号',
  upstreamShip varchar(64)                   comment '上游船运情况下的船号',
  downstreamTrafficMode varchar(32) not null comment '下游运输方式',
  downstreamCars int                         comment '下游汽运情况下的车数',
  downstreamJHH varchar(64)                  comment '下游火运情况下的计划号',
  downstreamShip varchar(64)                 comment '下游船运情况下的船号',
  deleted tinyint(1)                not null default 0 comment '逻辑删除',
  tsc timestamp                     not null default current_timestamp,
  tsu timestamp                     not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_ying_fayun add foreign key(orderId) references hs_same_order(id);
alter table hs_ying_fayun add foreign key(hsId)    references hs_same_order_config(id);

--  发运总吨数， 发运到场吨数， 发运未到场吨数
-- create view hs_ying_ledger_fy AS
--   (
--     SELECT
--       orderId,
--       hsId,
--       sum(fyAmount) as fyAmount,
--       sum(CASE WHEN arriveStatus = 'ARRIVE' THEN fyAmount ELSE 0 END) as arrivedAmount,
--       sum(CASE WHEN arriveStatus = 'UNARRIVE' THEN fyAmount ELSE 0 END) as unarrivedAmount
--     FROM hs_ying_fayun
--     GROUP BY orderId, hsId
--   );
--
-- -- 下游结算总吨数， 下游结算总金额,
-- create view hs_ying_ledger_down AS
--   (
--       select
--         orderId,
--         hsId,
--         sum(huikuanTotal) as huikuanTotal,
--         sum(money) as money,
--         (sum(huikuanTotal) - sum(settleGap)) * t2.tradeAddPrice + sum(t1.money) as salesAll
--       from hs_ying_settle_downstream t1
--     LEFT JOIN hs_ying_order_config t2 on  t1.orderId = t2.orderId and t1.hsId = t2.id
--     GROUP BY orderId, hsId
--   );
--
--
-- create view hs_ying_ledger_up AS
--   (
--     select
--       orderId,
--       hsId,
--       sum(huikuanTotal) as huikuanTotal,
--       sum(money) as money,
--       sum(discountDays) as discountDays
--    from hs_ying_settle_upstream
--    group by orderId, hsId
--  );





