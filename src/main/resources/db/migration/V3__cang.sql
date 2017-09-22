use hsdb;

-- 苍押订单表
create table hs_cang_order (
   id bigint(20)                    not null auto_increment,
   deptId bigint(20)                not null comment '所属事业部',
   teamId bigint(20)                not null comment '所属团队',
   creatorId bigint(20)             not null comment '创建人id',
   ownerId bigint(20)               not null comment '当前所有者id',
   mainAccounting bigint            not null comment '主账务公司 - 与下有接触的',
   line varchar(256)                not null comment '业务线名称: 由参与方公司简称组成',
   cargoType varchar(32)            not null comment '货物种类d',
   upstreamId  bigint(20)           not null comment '上游id',
   upstreamSettleMode varchar(20)   not null comment '上游结算方式',
   downstreamId  bigint(20)         not null comment '下游id',
   downstreamSettleMode varchar(20) not null comment '下游结算方式',
   status varchar(32)               not null comment '订单状态: 完结, 未完结',
   tsc timestamp                    not null default current_timestamp,
   primary key (id)
) engine=InnoDB default charset=utf8;


-- 业务订单-其他参与方
create table hs_cang_order_party (
   id bigint(20)         not null auto_increment,
   orderId bigint(20)    not null comment '订单id, 业务线id',

   custType varchar(32)           comment '客户类型: 上游, 贸易商, 下游, 资金方, 运输公司, 账务公司',
   customerId bigint(20) not null comment '业务线(订单)关联的其他账务公司',
   tsc timestamp         not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;


-- 业务线(订单)核算月全局配置
create table hs_cang_order_config (
   id bigint(20)                       not null auto_increment,
   orderId bigint(20)                  not null comment '订单id, 业务线id',
   hsMonth char(6)                     not null comment '核算月',
   maxPrepayRate decimal(10, 2)        not null comment '最高预付款比例',
   unInvoicedRate decimal(10, 2)       not null comment '未开票款付款比例',
   contractBaseInterest decimal(10, 2) not null comment '合同基准利率',
   expectHKDays int                    not null comment '预计回款天数',
   tradeAddPrice decimal(10, 2)        not null comment '贸易公司加价: 单位: 元/吨',
   tsc timestamp                       not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;


-- 苍押订单 - 入库
create table hs_cang_ruku (
  id bigint(20)                   not null auto_increment,
  orderId bigint(20)              not null comment '订单id, 业务线id',
  hsId bigint(20)                 not null comment '核算月id',

  rukuDate datetime               not null comment '入库日期',
  rukuStatus int              not null comment '入库状态, 在途,已入库',
  rukuAmount decimal(10,2)        not null comment '入库吨数',
  rukuPrice decimal(10,2)         not null comment '入库单价: 元/吨',
  locality varchar(128)           not null comment '场库',

  trafficMode varchar(32) not null comment '上游运输方式',
  cars int                         comment '上游汽运情况下的车数',
  jhh varchar(64)                  comment '上游火运情况下的计划号',
  ship varchar(64)                 comment '上游船运情况下的船号',

  tsc timestamp                   not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;


create table hs_cang_chuku (
  id bigint(20)                  not null auto_increment,
  orderId bigint(20)             not null comment '订单id, 业务线id',
  hsId bigint(20)                not null comment '核算月id',

  chukuDate datetime             not null comment '出库日期',
  locality varchar(128)          not null comment '出库场地',
  chukuAmount decimal(10, 2)     not null comment '出库吨数',
  chukuPrice decimal(10, 2)      not null comment '出库单价',

  tsc timestamp                   not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;


-- 苍押订单 - 付款
create table hs_cang_fukuan (
  id bigint(20)                  not null auto_increment,
  orderId bigint(20)             not null comment '订单id, 业务线id',
  hsId bigint(20)                not null comment '核算月id',

  payDate datetime               not null comment '付款日期yyyy-mm-dd',
  recieveCompanyId bigint(20)    not null comment '收款单位id',
  payFor varchar(32)             not null comment '付款用途: 货款,贸易差价, 尾款, 运费, 保证金',
  payAmount decimal(10, 2)       not null comment '付款金额',
  payMode varchar(32)            not null comment '付款方式: 电汇, 银行承兑, 商业承兑, 现金',
  capitalId bigint(20)           not null comment '资金方id',
  useInterest decimal(10, 4)              comment '当资金方为非自由资金时: 外部资金使用利率',
  useDays int                             comment '当资金方为非自由资金时: 外部资金使用利率',
  tsc timestamp                  not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;


-- 苍押订单 - 回款
create table hs_cang_huikuan (
   id bigint(20)                    not null auto_increment,
   orderId bigint(20)               not null comment '订单id, 业务线id',
   hsId bigint(20)                  not null comment '核算月id',

   huikuanCompanyId bigint(20)           not null comment '回款公司-谁回的款',
   huikuanDate datetime                  not null comment '回款日期',
   huikuanAmount decimal(10,2)           not null comment '回款总额',
   huikuanUsage varchar(32)              not null comment '回款用途: 货款, 保证金',
   huikuanMode varchar(32)               not null comment '回款方式: 电汇, 银行承兑, 商业承兑, 现金',

   huikuanPaper tinyint(1)                        comment '是否收到票据, 如果回款方式是银行承兑, 此字段有效',
   huikuanPaperDate datetime                      comment '收到票据原件日期, 如果收到票据',
   huikuanDiscount tinyint(1)                     comment '是否贴息, 如果回款方式是银行承兑, 此字段有效',
   huikuanDiscountRate decimal(10, 2)             comment '如果回款方式是银行承兑, 贴息率',
   huikuanPaperExpire datetime                    comment '票据到期日',

   tsc timestamp                    not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;


-- 苍押订单 - 回款-付款mapping
create table hs_cang_huikuan_map (
   id bigint(20)         not null auto_increment,
   huikuanId bigint(20)  not null comment '回款id',
   fukuanId bigint(20)   not null comment '付款id',
   amount decimal(10,2)  not null comment '回款-付款 对应金额',
   tsc timestamp         not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;

-- 苍押订单 - 还款
create table hs_cang_huankuan (
   id bigint(20)           not null auto_increment,
   orderId bigint(20)      not null comment '订单id, 业务线id',
   hsId bigint(20)         not null comment '核算月id',

   skCompanyId bigint(20)  not null comment '收款单位(资金方), 只有外部资金的情况, 才存在还款',
   huankuankDate datetime  not null comment '还款日期',
   huankuanAmount decimal(10,2)  not null comment '还款总额',
   tsc timestamp           not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;


-- 苍押订单 - 还款-付款mapping
create table hs_cang_huankuan_map (
  id bigint(20)            not null auto_increment,

  huankuanId bigint(20)    not null comment '还款id',
  fukuanId  bigint(20)     not null comment '还款对应的付款id',
  principal decimal(10, 2) not null comment '本金',
  amount decimal(10, 2)    not null comment '本金+利息',

  tsc timestamp            not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;


-- 苍押订单 - 上游结算
create table hs_cang_settle_upstream (
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

-- 苍押订单 - 下游结算
create table hs_cang_settle_downstream (
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


-- 苍押订单 - 上游结算-入库-map
create table hs_cang_settle_upstream_map (
   id bigint(20)             not null auto_increment,
   settleId bigint(20)       not null comment '上游结算id',
   rukuId bigint(20)         not null comment '对应入库id',
   deduction decimal(10,2)   not null comment '抵扣入库吨数',
   tsc timestamp             not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;

-- 苍押订单 - 运输方结算
create table hs_cang_settle_traffic (
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


-- 苍押订单 - 费用
create table hs_cang_fee (
  id bigint(20)              not null auto_increment,
  orderId bigint(20)         not null comment '订单id, 业务线id',
  hsId bigint(20)            not null comment '核算月id',

  name varchar(64)           not null comment '费用科目',
  amount decimal(10, 2)      not null comment '金额',
  tsc timestamp              not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;


-- 苍押订单 - 发票
create table hs_cang_invoice (
  id bigint(20)                 not null auto_increment,
  orderId bigint(20)            not null comment '订单id, 业务线id',
  hsId bigint(20)               not null comment '核算月id',

  invoiceDirection varchar(32)  not null comment '进项 or 销项',
  invoiceType varchar(32)       not null comment '货款发票 or 运输发票',
  openDate  datetime            not null comment '开票日期',
  openCompanyId  bigint(20)     not null comment '开票单位',
  recieverId  bigint(20)        not null comment '收票单位',
  tsc timestamp                 not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

-- 苍押订单 - 发票明细
create table hs_cang_invoice_detail (
  id bigint(20)              not null auto_increment,
  invoiceId bigint(20)       not null comment '发票记录id',
  invoiceNumber varchar(128) not null comment '发票号',
  cargoAmount decimal(10,2)  not null comment '发票对应的货物数量(吨)',
  taxRate decimal(10,2)      not null comment '税率',
  priceAndTax decimal(10,2)  not null comment '价税合计',
  tsc timestamp              not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;;

-- 苍押订单-转移记录
create table hs_cang_transfer (
  id bigint(20)         not null auto_increment,
  orderId bigint(20)    not null,
  fromUserId bigint(20) not null,
  toUserId bigint(20)   not null,
  tsc timestamp         not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;


-- 修改记录
create table hs_cang_log (
  id bigint(20)           not null auto_increment,
  orderId bigint(20)      not null comment '订单编号',
  hsId bigint(20)         not null comment '核算月id',
  entityId bigint(20)     not null comment '实体id',
  entityType varchar(32)  not null comment '实体类型',
  memo varchar(128)       not null comment '修改日志',
  tsc timestamp           not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

