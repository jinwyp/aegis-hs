-- 应收订单表
create table hs_yin_order (
   id bigint(20)                    not null auto_increment,
   deptId bigint(20)                not null comment '所属事业部',
   teamId bigint(20)                not null comment '所属团队',
   mainAccounting bigint            not null comment '主账务公司 - 与下有接触的',
   line varchar(256)                not null comment '业务线名称: 由参与方公司简称组成',
   cargoType varchar(32)            not null comment '货物种类d', 
   upstreamId  bigint(20)           not null comment '上游id',
   upstreamSettleMode varchar(20)   not null comment '上游结算方式',
   downstreamId  bigint(20)         not null comment '下游id',
   downstreamSettleMode varchar(20) not null comment '下游结算方式',
   tsc timestamp                    not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 业务订单-其他参与方
create table hs_yin_order_party (
   id bigint(20)       not null auto_increment,
   orderId bigint(20)  not null comment '订单id, 业务线id',
   custType varchar(32)         comment '客户类型: 上游, 贸易商, 下游, 资金方, 运输公司, 账务公司',
   customerId          not null comment '业务线(订单)关联的其他账务公司',
   tsc timestamp       not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 业务线(订单)核算月全局配置
create table hs_yin_order_config (
   id bigint(20)                       not null auto_increment,
   orderId bigint(20)                  not null comment '订单id, 业务线id',
   hsMonth char(6)                     not null comment '核算月',
   maxPrepayRate decimal(10, 2)        not null comment '最高预付款比例',
   unInvoicedRate decimal(10, 2)       not null comment '未开票款付款比例',
   contractBaseInterest decimal(10, 2) not null comment '合同基准利率',
   expectHKDays int                    not null comment '预计回款天数',
   wPrice  decimal(10, 2)              not null comment '加权单价',
   tsc timestamp                       not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 发运
create table hs_yin_fayun (
  id bigint(20) not null auto_increment,
  orderId bigint(20)              not null comment '订单id, 业务线id',
  hsMonth char(6)                 not null comment '核算月份 yyyymm',
  fyDate datetime                 not null comment '发运日期', 
  fyAmount  decimal(10, 2)        not null comment '发运吨数',     -- 
  arriveStatus varchar(32)                 comment '到场状态',
  cargoType varchar(32)                    comment '货物品种',
  upstreamTrafficMode varchar(32) not null comment '上游运输方式',
  upstreamCars int                         comment '上游汽运情况下的车数',
  upstreamJHH varchar(64)                  comment '上游火运情况下的计划号',
  upstreamShip varchar(64)                 comment '上游船运情况下的船号',
  downstreamTrafficMode varchar(32)        comment '上游运输方式',
  downstreamCars int                       comment '上游汽运情况下的车数',
  downstreamJHH varchar(64)                comment '上游火运情况下的计划号',
  downstreamShip varchar(64)               comment '上游船运情况下的船号',
  tsc timestamp not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 付款:(主账务公司付给上游)
create table hs_yin_fukuan (
  id bigint(20)                  not null auto_increment,
  orderId bigint(20)             not null comment '订单id, 业务线id',
  hsMonth char(6)                not null comment '核算月YYYYMM',
  payDate datetime               not null comment '付款日期yyyy-mm-dd',
  recieveCompanyId bigint(20)    not null comment '收款单位id',
  payType varchar(32)            not null comment '付款类型',
  payAmount decimal(10, 2)       not null comment '付款金额',
  payMode varchar(32)            not null comment '付款方式',
  capitalId bigint(20)           not null comment '资金方id'  
  useInterest decimal(10, 4)              comment '当资金方为非自由资金时: 外部资金使用利率',
  useDays int                             comment '当资金方为非自由资金时: 外部资金使用利率',
  tsc timestamp not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 回款
create table hs_yin_huikuan (
   id bigint(20)                    not null auto_increment,
   orderId bigint(20)               not null comment '订单id, 业务线id',
   hkCompanyId bigint(20)           not null comment '回款公司-谁回的款',
   hkDate datetime                  not null comment '回款日期',
   hkAmount decimal(10,2)           not null comment '回款总额',
   hkMode varchar(32)               not null comment '回款方式',
   hkPaper tinyint                           comment '是否收到票据, 如果回款方式是银行承兑, 此字段有效',
   hkDiscount tinyint                        comment '是否贴息, 如果回款方式是银行承兑, 此字段有效',
   hkDiscountRate decimal(10, 2)             comment '如果回款方式是银行承兑, 贴息率',
   hkPaperExpire datetime                    comment '票据到期日',
   tsc timestamp                    not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 回款-付款mapping
create table hs_yin_huikuan_map (
   id bigint(20)         not null auto_increment,
   orderId bigint(20)    not null comment '订单id, 业务线id',
   hkId bigint(20)       not null comment '回款id',
   fkId bigint(20)       not null comment '付款id',
   amount decimal(10,2)  not null comment '回款-付款 对应金额',
   tsc timestamp         not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 还款
create table hs_yin_huankuan (
   id bigint(20)           not null auto_increment,
   orderId bigint(20)      not null comment '订单id, 业务线id',
   hkDate datetime         not null comment '还款日期',
   skCompanyId bigint(20)  not null comment '收款单位(资金方), 只有外部资金的情况, 才存在还款',
   hkAmount decimal(10,2)  not null comment '还款总额',
   tsc timestamp           not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 还款 - 付款mapping
create table hs_yin_huankuan_map (
  id bigint(20)            not null auto_increment,
  orderId bigint(20)       not null comment '订单id, 业务线id',
  hsMonth char(6)          not null comment '核算月',

  hkId bigint(20)          not null comment '还款id',
  fkId  bigint(20)         not null comment '还款对应的付款id',
  principal decimal(10, 2) not null comment '本金', 
  amount decimal(10, 2)    not null comment '本金+利息',
  tsc timestamp            not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 上游结算
create table hs_yin_settle_upstream (
   id bigint(20)             not null auto_increment,
   orderId bigint(20)        not null comment '订单id, 业务线id',
  hsMonth char(6)             not null comment '核算月',

   settleDate date           not null comment '结算日期',
   amount decimal(10, 2)     not null comment '结算数量(吨)',
   money decimal(10, 2)      not null comment '结算金额', 

   discountType varchar(32)  not null comment '折扣类型: 利率折扣, 金额折扣',
   discountInterest decimal(10, 4)    comment '利率折扣',
   discountDays int                   comment '利率折扣天数',
   discountAmount decimal(10, 2)      comment '金额折扣',

   tsc timestamp             not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 下游结算
create table hs_yin_settle_downstream (
   id bigint(20)             not null auto_increment,
   orderId bigint(20)        not null comment '订单id, 业务线id',
  hsMonth char(6)             not null comment '核算月',

   settleDate date           not null comment '结算日期',
   amount decimal(10, 2)     not null comment '结算数量(吨)',
   money decimal(10, 2)      not null comment '结算金额', 

   settleGap decimal(10, 2)  not null comment '结算扣吨',

   tsc timestamp             not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 下游结算-发运map
create table hs_yin_settle_downstream_map (
   id bigint(20)             not null auto_increment,
   settleId bigint(20)       not null comment '下游结算id',
   fyId bigint(20)           not null comment '对应发运id',
   deduction decimal(10,2)   not null comment '抵扣发运吨数',
   tsc timestamp             not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 运输方结算
create table hs_yin_settle_traffic (
  id bigint(20)               not null auto_increment,
  orderId bigint(20)          not null comment '订单id, 业务线id',
  hsMonth char(6)             not null comment '核算月',

  settleDate date             not null comment '结算日期',
  amount decimal(10, 2)       not null comment '结算数量(吨)',
  money decimal(10, 2)        not null comment '结算金额', 
  trafficCompanyId bigint(20) not null comment '与哪个运输方结算',

  tsc timestamp               not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 费用
create table hs_yin_fee (
  id bigint(20)              not null auto_increment,
  orderId bigint(20)         not null comment '订单id, 业务线id',
  hsMonth char(6)            not null comment '核算月yyyymm',
  name varchar(64)           not null comment '费用科目',
  amount decimal(10, 2)      not null comment '金额',
  tsc timestamp              not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 发票
create table hs_yin_invoice (
  id bigint(20)                 not null auto_increment,
  orderId bigint(20)            not null comment '订单id, 业务线id',
  hsMonth char(6)               not null comment '核算月yyyymm',
  invoiceDirection varchar(32)  not null comment '进项 or 销项',
  invoiceType varchar(32)       not null comment '货款发票 or 运输发票',
  openDate  datetime            not null comment '开票日期', 
  openCompanyId  bigint(20)     not null comment '开票单位', 
  recieverId  bigint(20)        not null comment '收票单位',
  tsc timestamp                 not null default current_timestamp
)engine=InnoDB default charset=utf8;

-- 应收订单 - 发票明细
create table hs_yin_invoice_detail (
  id bigint(20)              not null auto_increment,
  invoiceId bigint(20)       not null comment '发票记录id',
  invoiceNumber varchar(128) not null comment '发票号',
  cargoAmount decimal(10,2)  not null comment '发票对应的货物数量(吨)',
  taxRate decimal(10,2)      not null comment '税率', 
  tsc timestamp              not null default current_timestamp
);


