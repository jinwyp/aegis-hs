use hsdb;

-- 应收订单表
create table hs_ying_order (
   id bigint(20)                    not null auto_increment,
   deptId bigint(20)                not null comment '所属事业部',
   teamId bigint(20)                not null comment '所属团队',
   creatorId bigint(20)             not null comment '创建人id',
   ownerId bigint(20)               not null comment '当前所有者id',
   mainAccounting bigint            not null comment '主账务公司 - 与下有接触的',
   line varchar(256)                not null comment '业务线名称: 由参与方公司简称组成',
   cargoType varchar(32)            not null comment '货物种类: 煤炭、钢材',
   upstreamId  bigint(20)           not null comment '上游id',
   upstreamSettleMode varchar(20)   not null comment '上游结算方式',
   downstreamId  bigint(20)         not null comment '下游id',
   downstreamSettleMode varchar(20) not null comment '下游结算方式',
   status varchar(32)               not null comment '订单状态: 完结, 未完结',
   deleted tinyint(1)               not null default 0 comment '是否删除',
   tsc timestamp                    not null default current_timestamp,
   tsu timestamp,
   primary key (id)
) engine=InnoDB default charset=utf8;


-- 业务订单-其他参与方
create table hs_ying_order_party (
   id bigint(20)         not null auto_increment,
   orderId bigint(20)    not null comment '订单id, 业务线id',

   custType varchar(32)           comment '客户类型: 上游, 贸易商, 下游, 资金方, 运输公司, 账务公司',
   customerId bigint(20) not null comment '业务线(订单)关联的其他账务公司',
   tsc timestamp         not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;

-- 业务线(订单)核算月全局配置
create table hs_ying_order_config (
   id bigint(20)                       not null auto_increment,
   orderId bigint(20)                  not null comment '订单id, 业务线id',
   hsMonth char(6)                     not null comment '核算月',
   maxPrepayRate decimal(10, 2)        not null comment '最高预付款比例',
   unInvoicedRate decimal(10, 2)       not null comment '未开票款付款比例',
   contractBaseInterest decimal(10, 2) not null comment '合同基准利率',
   expectHKDays int                    not null comment '预计回款天数',
   tradeAddPrice decimal(10, 2)        not null comment '贸易公司加价: 单位: 元/吨',
   weightedPrice  decimal(10, 2)              not null comment '加权单价',
   tsc timestamp                       not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 发运
create table hs_ying_fayun (
  id bigint(20)                   not null auto_increment,
  orderId bigint(20)              not null comment '订单id, 业务线id',
  hsId bigint(20)                 not null comment '核算月id',
  fyDate datetime                 not null comment '发运日期',
  fyAmount  decimal(10, 2)        not null comment '发运吨数',
  arriveStatus varchar(32)                 comment '到场状态',

  upstreamTrafficMode varchar(32) not null comment '上游运输方式',
  upstreamCars int                         comment '上游汽运情况下的车数',
  upstreamJHH varchar(64)                  comment '上游火运情况下的计划号',
  upstreamShip varchar(64)                 comment '上游船运情况下的船号',

  downstreamTrafficMode varchar(32) not null  comment '下游运输方式',
  downstreamCars int                       comment '下游汽运情况下的车数',
  downstreamJHH varchar(64)                comment '下游火运情况下的计划号',
  downstreamShip varchar(64)               comment '下游船运情况下的船号',

  tsc timestamp                   not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 付款:(主账务公司付给上游)
create table hs_ying_fukuan (
  id bigint(20)                  not null auto_increment,
  orderId bigint(20)             not null comment '订单id, 业务线id',
  hsId bigint(20)                not null comment '核算月id',

  payDate datetime               not null comment '付款日期yyyy-mm-dd',
  receiveCompanyId bigint(20)    not null comment '收款单位id',
  payUsage varchar(32)           not null comment '付款用途: 货款, 贸易差价, 尾款, 运费, 保证金',
  payAmount decimal(10, 2)       not null comment '付款金额',
  payMode varchar(32)            not null comment '付款方式: 电汇, 银行承兑, 商业承兑, 现金',
  capitalId bigint(20)           not null comment '资金方id',
  useInterest decimal(10, 4)              comment '当资金方为非自由资金时: 外部资金使用利率',
  useDays int                             comment '当资金方为非自由资金时: 外部资金使用利率',
  tsc timestamp                  not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 回款
create table hs_ying_huikuan (
   id bigint(20)                    not null auto_increment,
   orderId bigint(20)               not null comment '订单id, 业务线id',
   hsId bigint(20)                  not null comment '核算月id',

   huikuanCompanyId bigint(20)           not null comment '回款公司-谁回的款',
   huikuanDate datetime                  not null comment '回款日期',
   huikuanAmount decimal(10,2)           not null comment '回款总额',
   huikuanUsage varchar(32)              not null comment '回款用途: 货款, 保证金',
   huikuanMode varchar(32)               not null comment '回款方式: 电汇, 银行承兑, 商业承兑, 现金',

   huikuanBankPaper tinyint(1)                        comment '是否收到票据, 如果回款方式是银行承兑, 此字段有效',
   huikuanBankPaperDate datetime                      comment '收到票据原件日期, 如果收到票据',
   huikuanBankDiscount tinyint(1)                     comment '是否贴息, 如果回款方式是银行承兑, 此字段有效',
   huikuanBankDiscountRate decimal(10, 2)             comment '如果回款方式是银行承兑, 贴息率',
   huikuanBankPaperExpire datetime                    comment '票据到期日',

   huikuanBusinessPaper tinyint(1)                    comment '是否收到票据, 如果回款方式是商业承兑, 此字段有效',
   huikuanBusinessPaperDate datetime                  comment '收到票据原件日期, 如果收到票据',
   huikuanBusinessDiscount tinyint(1)                 comment '是否贴息, 如果回款方式是商业承兑, 此字段有效',
   huikuanBusinessDiscountRate decimal(10, 2)         comment '如果回款方式是商业承兑, 贴息率',
   huikuanBusinessPaperExpire datetime                comment '票据到期日 如果回款方式是商业承兑 ,此字段有效',

   tsc timestamp                    not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 回款-付款mapping
create table hs_ying_huikuan_map (
   id bigint(20)         not null auto_increment,
   orderId bigint(20)    not null,
   huikuanId bigint(20)  not null comment '回款id',
   fukuanId bigint(20)   not null comment '付款id',
   amount decimal(10,2)  not null comment '回款-付款 对应金额',
   tsc timestamp         not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 还款
create table hs_ying_huankuan (
   id bigint(20)           not null auto_increment,
   orderId bigint(20)      not null comment '订单id, 业务线id',
   hsId bigint(20)         not null comment '核算月id',

   skCompanyId bigint(20)  not null comment '收款单位(资金方), 只有外部资金的情况, 才存在还款',
   huankuankDate datetime  not null comment '还款日期',
   huankuanAmount decimal(10,2)  not null comment '还款总额',
   huankuanInterest decimal(10, 2) not null comment '还款利息',
   huankuanFee decimal(10, 2) not null comment '还款服务费',
   tsc timestamp           not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 还款-付款mapping
create table hs_ying_huankuan_map (
  id bigint(20)            not null auto_increment,

  orderId bigint(20)       not null  comment '还款所属的业务线',

  huankuanId bigint(20)    not null comment '还款id',
  fukuanId  bigint(20)     not null comment '还款对应的付款id',
  principal decimal(10, 2) not null comment '本金',
  amount decimal(10, 2)    not null comment '本金+利息',
  tsc timestamp            not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 上游结算
create table hs_ying_settle_upstream (
   id bigint(20)             not null auto_increment,
   orderId bigint(20)        not null comment '订单id, 业务线id',
   hsId bigint(20)           not null comment '核算月id',

   settleDate date           not null comment '结算日期',
   amount decimal(10, 2)     not null comment '结算数量(吨)',
   money decimal(10, 2)      not null comment '结算金额',

   discountType varchar(32)  not null comment '折扣类型: 利率折扣, 金额折扣, 无折扣',
   discountInterest decimal(10, 4)    comment '利率折扣',
   discountDays int                   comment '利率折扣天数',
   discountAmount decimal(10, 2)      comment '金额折扣',

   tsc timestamp             not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 下游结算
create table hs_ying_settle_downstream (
   id bigint(20)             not null auto_increment,
   orderId bigint(20)        not null comment '订单id, 业务线id',
   hsId bigint(20)           not null comment '核算月id',

   settleDate datetime       not null comment '结算日期',
   amount decimal(10, 2)     not null comment '结算数量(吨)',
   money decimal(10, 2)      not null comment '结算金额',

   settleGap decimal(10, 2)  not null comment '结算扣吨',

   tsc timestamp             not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 下游结算-发运map
create table hs_ying_settle_downstream_map (
   id bigint(20)             not null auto_increment,
  orderId bigint(20)         not null ,

   settleId bigint(20)       not null comment '下游结算id',
   fyId bigint(20)           not null comment '对应发运id',
   deduction decimal(10,2)   not null comment '抵扣发运吨数',
   tsc timestamp             not null default current_timestamp,

   primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 运输方结算
create table hs_ying_settle_traffic (
  id bigint(20)              not null auto_increment,
  orderId bigint(20)         not null comment '订单id, 业务线id',
  hsId bigint(20)            not null comment '核算月id',

  settleDate date             not null comment '结算日期',
  amount decimal(10, 2)       not null comment '结算数量(吨)',
  money decimal(10, 2)        not null comment '结算金额',
  trafficCompanyId bigint(20) not null comment '与哪个运输方结算',
  tsc timestamp               not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 费用
create table hs_ying_fee (
  id bigint(20)              not null auto_increment,
  orderId bigint(20)         not null comment '订单id, 业务线id',
  hsId bigint(20)            not null comment '核算月id',

  name varchar(64)           not null comment '费用科目',
  amount decimal(10, 2)      not null comment '金额',
  tsc timestamp              not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 发票
create table hs_ying_invoice (
  id bigint(20)                 not null auto_increment,
  orderId bigint(20)            not null comment '订单id, 业务线id',
  hsId bigint(20)               not null comment '核算月id',

  invoiceDirection varchar(32)  not null comment '进项 or 销项',
  invoiceType varchar(32)       not null comment '货款发票 or 运输发票',
  openDate  datetime            not null comment '开票日期',
  openCompanyId  bigint(20)     not null comment '开票单位',
  receiverId  bigint(20)        not null comment '收票单位',
  tsc timestamp                 not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

-- 应收订单 - 发票明细
create table hs_ying_invoice_detail (
  id bigint(20)              not null auto_increment,
  invoiceId bigint(20)       not null comment '发票记录id',
  invoiceNumber varchar(128) not null comment '发票号',
  cargoAmount decimal(10,2)  not null comment '发票对应的货物数量(吨)',
  taxRate decimal(10,2)      not null comment '税率',
  priceAndTax decimal(10,2)  not null comment '价税合计',
  tsc timestamp              not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;;

-- 订单转移
create table hs_ying_transfer (
  id bigint(20)         not null auto_increment,
  orderId bigint(20)    not null,
  fromUserId bigint(20) not null,
  toUserId bigint(20)   not null,
  tsc timestamp         not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

-- 修改记录
create table hs_ying_log (
  id bigint(20)           not null auto_increment,
  orderId bigint(20)      not null comment '订单编号',
  hsId bigint(20)         not null comment '核算月id',
  entityId bigint(20)     not null comment '实体id',
  entityType varchar(32)  not null comment '实体类型',
  memo varchar(128)       not null comment '修改日志',
  tsc timestamp           not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

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
--         sum(amount) as amount,
--         sum(money) as money,
--         (sum(amount) - sum(settleGap)) * t2.tradeAddPrice + sum(t1.money) as salesAll
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
--       sum(amount) as amount,
--       sum(money) as money,
--       sum(discountDays) as discountDays
--    from hs_ying_settle_upstream
--    group by orderId, hsId
--  );





