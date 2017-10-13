-- 订单表
create table hs_same_order (
  id bigint(20)                    not null auto_increment,
  businessType varchar(32)         not null comment '业务线类型',
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
  tsu timestamp not null default current_timestamp,
  primary key (id)
) engine=InnoDB default charset=utf8;
alter table hs_same_order add foreign key(upstreamId)     references hs_party(id);
alter table hs_same_order add foreign key(downstreamId)   references hs_party(id);
alter table hs_same_order add foreign key(mainAccounting) references hs_party(id);
alter table hs_same_order add foreign key(creatorId)      references hs_user(id);
alter table hs_same_order add foreign key(ownerId)        references hs_user(id);
alter table hs_same_order add foreign key(teamId)        references hs_team(id);
alter table hs_same_order add foreign key(deptId)        references hs_dept(id);

-- 业务订单-其他参与方
create table hs_same_order_party (
  id bigint(20)         not null auto_increment,
  orderId bigint(20)    not null comment '订单id, 业务线id',
  custType varchar(32)           comment '客户类型: 上游, 贸易商, 下游, 资金方, 运输公司, 账务公司',
  customerId bigint(20) not null comment '业务线(订单)关联的其他账务公司',
  deleted tinyint(1)          not null default 0 comment '逻辑删除',
  tsc timestamp         not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_order_party add foreign key(orderId)    references hs_same_order(id);
alter table hs_same_order_party add foreign key(customerId) references hs_party(id);
alter table hs_same_order_party add foreign key(orderId) references hs_same_order(id);

-- 业务线(订单)核算月全局配置
create table hs_same_order_config (
  id bigint(20)                       not null auto_increment,
  orderId bigint(20)                  not null comment '订单id, 业务线id',
  hsMonth char(6)                     not null comment '核算月',
  maxPrepayRate decimal(10, 2)        not null comment '最高预付款比例',
  unInvoicedRate decimal(10, 2)       not null comment '未开票款付款比例',
  contractBaseInterest decimal(10, 2) not null comment '合同基准利率',
  expectHKDays int                    not null comment '预计回款天数',
  tradeAddPrice decimal(10, 2)        not null comment '贸易公司加价: 单位: 元/吨',
  weightedPrice  decimal(10, 2)              not null comment '加权单价',
  deleted tinyint(1)          not null default 0 comment '逻辑删除',
  tsc timestamp                       not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

-- 费用
create table hs_same_fee (
  id bigint(20)              not null auto_increment,
  orderId bigint(20)         not null comment '订单id, 业务线id',
  hsId bigint(20)            not null comment '核算月id',
  name varchar(64)           not null comment '费用科目',
  amount decimal(10, 2)      not null comment '金额',
  deleted tinyint(1)         not null default 0 comment '逻辑删除',
  tsc timestamp              not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_fee add foreign key(orderId) references hs_same_order(id);
alter table hs_same_fee add foreign key(hsId)    references hs_same_order_config(id);

-- 发票
create table hs_same_invoice (
  id bigint(20)                 not null auto_increment,
  orderId bigint(20)            not null comment '订单id, 业务线id',
  hsId bigint(20)               not null comment '核算月id',
  invoiceDirection varchar(32)  not null comment '进项 or 销项',
  invoiceType varchar(32)       not null comment '货款发票 or 运输发票',
  openDate  datetime            not null comment '开票日期',
  openCompanyId  bigint(20)     not null comment '开票单位',
  receiverId  bigint(20)        not null comment '收票单位',
  deleted tinyint(1)            not null default 0 comment '逻辑删除',
  tsc timestamp                 not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_invoice add foreign key(orderId)       references hs_same_order(id);
alter table hs_same_invoice add foreign key(hsId)          references hs_same_order_config(id);
alter table hs_same_invoice add foreign key(openCompanyId) references hs_party(id);

-- 发票明细
create table hs_same_invoice_detail (
  id bigint(20)              not null auto_increment,
  invoiceId bigint(20)       not null comment '发票记录id',
  invoiceNumber varchar(128) not null comment '发票号',
  cargoAmount decimal(10,2)  not null comment '发票对应的货物数量(吨)',
  taxRate decimal(10,2)      not null comment '税率',
  priceAndTax decimal(10,2)  not null comment '价税合计',
  deleted tinyint(1)         not null default 0 comment '逻辑删除',
  tsc timestamp              not null default current_timestamp,
  tsu timestamp              not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;;
alter table hs_same_invoice_detail add foreign key(invoiceId) references hs_same_invoice(id);

-- 订单转移
create table hs_same_transfer (
  id bigint(20)         not null auto_increment,
  orderId bigint(20)    not null,
  fromUserId bigint(20) not null,
  toUserId bigint(20)   not null,
  deleted tinyint(1)          not null default 0 comment '逻辑删除',
  tsc timestamp         not null default current_timestamp,
  tsu timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_transfer add foreign key(orderId) references hs_same_order(id);

-- 付款
create table hs_same_fukuan (
  id bigint(20)                  not null auto_increment,
  orderId bigint(20)             not null comment '订单id, 业务线id',
  hsId bigint(20)                not null comment '核算月id',
  payDate datetime               not null comment '付款日期yyyy-mm-dd',
  receiveCompanyId bigint(20)    not null comment '收款单位id',
  payUsage varchar(32)           not null comment '付款用途: 货款, 贸易差价, 尾款, 运费, 保证金',
  payAmount decimal(10, 2)       not null comment '付款金额',
  deleted tinyint(1)               not null default 0 comment '是否删除',
  tsc timestamp                  not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_fukuan add foreign key(orderId)   references hs_same_order(id);
alter table hs_same_fukuan add foreign key(hsId)      references hs_same_order_config(id);
alter table hs_same_fukuan add foreign key(receiveCompanyId) references hs_party(id);

-- 借款
create table hs_same_jiekuan(
  id bigint(20)                  not null auto_increment,
  fukuanId bigint(20)            not null comment '关联的付款id',
  orderId bigint(20)             not null comment '订单id, 业务线id',
  hsId bigint(20)                not null comment '核算月id',
  amount decimal(10,2)           not null comment '借款金额',
  jiekuanDate datetime               not null comment '借款日期yyyy-mm-dd',
  capitalId bigint(20)           not null comment '资金方id: 如果为主账务公司id, 则为自有资金',
  useInterest decimal(10, 4)              comment '外部资金使用利率',
  useDays int                             comment '外部资金使用天数',
  deleted tinyint(1)             not null default 0 comment '是否删除',
  tsc timestamp                  not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

-- 还款
create table hs_same_huankuan (
  id bigint(20)           not null auto_increment,
  orderId bigint(20)      not null comment '订单id, 业务线id',
  hsId bigint(20)         not null comment '核算月id',
  huankuankDate datetime  not null comment '还款日期',
  deleted tinyint(1)               not null default 0 comment '是否删除',
  tsc timestamp           not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_huankuan add foreign key(orderId) references hs_same_order(id);
alter table hs_same_huankuan add foreign key(hsId)    references hs_same_order_config(id);

-- 还款-借款-mapping
create table hs_same_huankuan_map (
  id bigint(20)             not null auto_increment,
  orderId bigint(20)        not null comment '还款所属的业务线',
  huankuanId bigint(20)     not null comment '还款id',
  jiekuanId  bigint(20)     not null comment '还款对应的借款id',

  principal decimal(10, 2)  not null comment '本金',
  interest decimal(10, 2)   not null comment '利息',
  fee      decimal(10,2)    not null comment '服务费',

  deleted tinyint(1)        not null default 0 comment '是否删除',
  tsc timestamp             not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_huankuan_map add foreign key(huankuanId)  references hs_same_huankuan(id);
alter table hs_same_huankuan_map add foreign key(jiekuanId)   references hs_same_jiekuan(id);

-- 回款
create table hs_same_huikuan (
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
  deleted tinyint(1)               not null default 0 comment '是否删除',
  tsc timestamp                    not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_huikuan add foreign key(orderId)          references hs_same_order(id);
alter table hs_same_huikuan add foreign key(hsId)             references hs_same_order_config(id);
alter table hs_same_huikuan add foreign key(huikuanCompanyId) references hs_party(id);

--  回款-付款-mapping
create table hs_same_huikuan_map (
  id bigint(20)         not null auto_increment,
  orderId bigint(20)    not null,
  huikuanId bigint(20)  not null comment '回款id',
  fukuanId bigint(20)   not null comment '付款id',
  amount decimal(10,2)  not null comment '回款-付款 对应金额',
  deleted tinyint(1)               not null default 0 comment '是否删除',
  tsc timestamp         not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_huikuan_map add foreign key(huikuanId) references hs_same_huikuan(id);
alter table hs_same_huikuan_map add foreign key(fukuanId)  references hs_same_fukuan(id);

-- 买方结算
create table hs_same_settle_buyer (
  id bigint(20)             not null auto_increment,
  orderId bigint(20)        not null comment '订单id, 业务线id',
  hsId bigint(20)           not null comment '核算月id',
  settleDate datetime       not null comment '结算日期',
  amount decimal(10, 2)     not null comment '结算数量(吨)',
  money decimal(10, 2)      not null comment '结算金额',
  settleGap decimal(10, 2)  not null comment '结算扣吨',
  deleted tinyint(1)               not null default 0 comment '是否删除',
  tsc timestamp             not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_settle_buyer add foreign key(orderId) references hs_same_order(id);
alter table hs_same_settle_buyer add foreign key(hsId)    references hs_same_order_config(id);

-- 卖方结算
create table hs_same_settle_seller (
  id bigint(20)             not null auto_increment,
  orderId bigint(20)        not null comment '订单id, 业务线id',
  hsId bigint(20)           not null comment '核算月id',
  settleDate date           not null comment '结算日期',
  -- amount decimal(10, 2)     not null comment '结算数量(吨)',
  -- money decimal(10, 2)      not null comment '结算金额',
  discountType varchar(32)  not null comment '折扣类型: 利率折扣, 金额折扣, 无折扣',
  discountInterest decimal(10, 4)    comment '利率折扣',
  discountDays int                   comment '利率折扣天数',
  discountAmount decimal(10, 2)      comment '金额折扣',
  deleted tinyint(1)               not null default 0 comment '是否删除',
  tsc timestamp             not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_settle_seller add foreign key(orderId) references hs_same_order(id);
alter table hs_same_settle_seller add foreign key(hsId)    references hs_same_order_config(id);
create unique index  idx_seller_1 on hs_same_settle_seller(orderId, hsId);

-- 应收订单 - 运输方结算
create table hs_same_settle_traffic (
  id bigint(20)               not null auto_increment,
  orderId bigint(20)          not null comment '订单id, 业务线id',
  hsId bigint(20)             not null comment '核算月id',
  settleDate date             not null comment '结算日期',

  amount decimal(10, 2)       not null comment '结算数量(吨)',
  money decimal(10, 2)        not null comment '结算金额',

  trafficCompanyId bigint(20) not null comment '与哪个运输方结算',
  deleted tinyint(1)          not null default 0 comment '是否删除',
  tsc timestamp               not null default current_timestamp,
  tsu timestamp not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_same_settle_traffic add foreign key(orderId) references hs_same_order(id);
alter table hs_same_settle_traffic add foreign key(hsId)    references hs_same_order_config(id);
alter table hs_same_settle_traffic add foreign key(trafficCompanyId) references hs_party(id);

-- 修改记录
create table hs_same_log (
  id bigint(20)           not null auto_increment,
  orderId bigint(20)      not null comment '订单编号',
  entityId bigint(20)     not null comment '实体id',
  entityType varchar(32)  not null comment '实体类型',
  memo varchar(128)       not null comment '修改日志',
  deleted tinyint(1)          not null default 0 comment '逻辑删除',
  tsc timestamp           not null default current_timestamp,
  tsu timestamp not null default current_timestamp,

  primary key (id)
)engine=InnoDB default charset=utf8;

alter table hs_same_log add foreign key(orderId) references hs_same_order(id);

