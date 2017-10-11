use hsdb;

-- 苍押订单 - 入库
create table hs_cang_ruku (
  id bigint(20)                   not null auto_increment,
  orderId bigint(20)              not null comment '订单id, 业务线id',
  hsId bigint(20)                 not null comment '核算月id',

  rukuDate datetime               not null comment '入库日期',
  rukuStatus varchar(32)              not null comment '入库状态, 在途,已入库',
  rukuAmount decimal(10,2)        not null comment '入库吨数',
  rukuPrice decimal(10,2)         not null comment '入库单价: 元/吨',
  locality varchar(128)           not null comment '场库',

  trafficMode varchar(32) not null comment '上游运输方式',
  cars int                         comment '上游汽运情况下的车数',
  jhh varchar(64)                  comment '上游火运情况下的计划号',
  ship varchar(64)                 comment '上游船运情况下的船号',

  deleted tinyint(1)          not null default 0 comment '逻辑删除',
  tsc timestamp                   not null default current_timestamp,
  tsu timestamp                   not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

alter table hs_cang_ruku add foreign key(orderId) references hs_same_order(id);
alter table hs_cang_ruku add foreign key(hsId)    references hs_same_order_config(id);

create table hs_cang_chuku (
  id bigint(20)                  not null auto_increment,
  orderId bigint(20)             not null comment '订单id, 业务线id',
  hsId bigint(20)                not null comment '核算月id',

  chukuDate datetime             not null comment '出库日期',
  locality varchar(128)          not null comment '出库场地',
  chukuAmount decimal(10, 2)     not null comment '出库吨数',
  chukuPrice decimal(10, 2)      not null comment '出库单价',
  deleted tinyint(1)             not null default 0 comment '逻辑删除',
  tsc timestamp                  not null default current_timestamp,
  tsu timestamp                  not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;

alter table hs_cang_chuku add foreign key(orderId) references hs_same_order(id);
alter table hs_cang_chuku add foreign key(hsId)    references hs_same_order_config(id);

-- 苍押订单 - 上游结算
create table hs_cang_settle_upstream (
   id bigint(20)             not null auto_increment,
   orderId bigint(20)        not null comment '订单id, 业务线id',
   hsId bigint(20)           not null comment '核算月id',

   settleDate datetime       not null comment '结算日期',
   amount decimal(10, 2)     not null comment '结算数量(吨)',
   money decimal(10, 2)      not null comment '结算金额',
   settleGap decimal(10, 2)  not null comment '结算扣吨',

   deleted tinyint(1)        not null default 0 comment '逻辑删除',
   tsc timestamp             not null default current_timestamp,
   tsu timestamp             not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;

-- alter table hs_cang_settle_upstream add foreign key(orderId) references hs_cang_order(id);
-- alter table hs_cang_settle_upstream add foreign key(hsId)    references hs_cang_order_config(id);



-- 苍押订单 - 上游结算-入库-map
create table hs_cang_settle_upstream_map (
   id bigint(20)             not null auto_increment,
   settleId bigint(20)       not null comment '上游结算id',
   rukuId bigint(20)         not null comment '对应入库id',
   deduction decimal(10,2)   not null comment '抵扣入库吨数',

   deleted tinyint(1)        not null default 0 comment '逻辑删除',
   tsc timestamp             not null default current_timestamp,
   tsu timestamp             not null default current_timestamp,
   primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_cang_settle_upstream_map add foreign key(settleId) references hs_cang_settle_upstream(id);
alter table hs_cang_settle_upstream_map add foreign key(rukuId) references hs_cang_ruku(id);




