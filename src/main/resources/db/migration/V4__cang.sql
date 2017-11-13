use hsdb;

-- 仓押订单 - 入库
create table hs_cang_ruku (
  id bigint(20)                   not null auto_increment,
  orderId bigint(20)              not null comment '订单id, 业务线id',
  hsId bigint(20)                 not null comment '核算月id',
  rukuDate datetime               not null comment '入库日期',
  rukuStatus varchar(32)          not null comment '入库状态, 在途,已入库',
  rukuAmount decimal(10,2)        not null comment '入库吨数',
  rukuPrice decimal(10,2)         not null comment '入库金额: 元',
  locality varchar(128)           not null comment '场库',
  trafficMode varchar(32)         not null comment '上游运输方式',
  cars int                                 comment '上游汽运情况下的车数',
  jhh varchar(64)                          comment '上游火运情况下的计划号',
  ship varchar(64)                         comment '上游船运情况下的船号',
  deleted tinyint(1)              not null default 0 comment '逻辑删除',
  tsc timestamp                   not null default current_timestamp,
  tsu timestamp                   not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_cang_ruku add foreign key(orderId) references hs_same_order(id);
alter table hs_cang_ruku add foreign key(hsId)    references hs_same_order_config(id);

-- 仓押订单 - 出库
create table hs_cang_chuku (
  id bigint(20)                  not null auto_increment,
  orderId bigint(20)             not null comment '订单id, 业务线id',
  hsId bigint(20)                not null comment '核算月id',
  chukuDate datetime             not null comment '出库日期',
  locality varchar(128)          not null comment '出库场地',
  chukuAmount decimal(10, 2)     not null comment '出库吨数',
  chukuPrice decimal(10, 2)      not null comment '出库金额: 元',
  deleted tinyint(1)             not null default 0 comment '逻辑删除',
  tsc timestamp                  not null default current_timestamp,
  tsu timestamp                  not null default current_timestamp,
  primary key (id)
)engine=InnoDB default charset=utf8;
alter table hs_cang_chuku add foreign key(orderId) references hs_same_order(id);
alter table hs_cang_chuku add foreign key(hsId)    references hs_same_order_config(id);

