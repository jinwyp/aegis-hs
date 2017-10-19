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




