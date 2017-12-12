use hsdb;

-- 用户表
create table hs_user (
  id bigint(20)            not null auto_increment,
  deptId bigint(20)        not null comment '所属部门id',
  phone varchar(12)        not null comment '手机号',
  username varchar(128)    not null comment '用户名',
  password varchar(40)     not null comment '密码',
  passwordSalt varchar(40) not null comment '密码盐',
  createDate datetime      not null comment '创建时间',
  createBy varchar(45)     not null comment '创建人',
  isAdmin tinyint(1)       not null comment '是否为管理员, 2 是 1 否',
  isActive tinyint(1) default '2'   comment '2 可用 1 禁用',
  PRIMARY KEY (id),
  UNIQUE KEY name_UNIQUE (phone)
)engine=InnoDB default charset=utf8;

-- 部门表
create table hs_dept (
  id bigint(20)    not null auto_increment,
  name varchar(64) not null comment '部门名称',
  deleted tinyint(1)             not null default 0 comment '是否删除',
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;

-- 团队表
create table hs_team (
  id bigint(20)     not null auto_increment,
  name varchar(64)  not null comment '团队名称',
  deptId bigint(20) not null comment '所属部门',
  deleted tinyint(1)             not null default 0 comment '是否删除',
  tsu timestamp,
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;

alter table hs_team add foreign key(deptId) references hs_dept(id);


-- 外部客户: 上游、下游、贸易商, 运输方, 资金方, ccs账务公司
create table hs_party (
  id bigint(20)          not null auto_increment,
  partyType int       not null comment '1: ccs账务公司, 2: 资金方, 3: 外部 ,4: 贸易公司'  ,
  name varchar(128)      not null comment '客户名称',
  shortName varchar(128) not null comment '客户简称',
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;
