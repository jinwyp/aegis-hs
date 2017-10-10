-- 应收订单表
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