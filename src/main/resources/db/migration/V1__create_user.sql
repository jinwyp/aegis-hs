use hsdb;

-- 用户表
create table hs_user (
  id bigint(20)            not null auto_increment,
  deptId bigint(20)        not null comment '所属部门id',
  securePhone varchar(12)  not null comment '手机号',
  password varchar(40)     not null comment '密码',
  passwordSalt varchar(40) not null comment '密码盐',
  createDate datetime      not null comment '创建时间',
  createBy varchar(45)     not null comment '创建人',
  isAdmin tinyint          not null comment '是否为管理员',
  isActive tinyint(1) default '1'   comment '1 可用 0 禁用',
  PRIMARY KEY (id),
  UNIQUE KEY name_UNIQUE (securePhone)
)engine=InnoDB default charset=utf8;
-- insert into hs_dept() values();

-- 部门表
create table hs_dept (
  id bigint(20)    not null auto_increment,
  name varchar(64) not null comment '部门名称',
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;
insert into hs_dept(name) values('金融产品事业部');

-- 团队表
create table hs_team (
  id bigint(20)     not null auto_increment,
  name varchar(64)  not null comment '团队名称',
  deptId bigint(20) not null comment '所属部门',
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;
insert into hs_party(name, deptId) values
('赵善文团队', 1),
('张培栓团队', 1),
('魏靖团队',1),
('卢昆团队', 1),
('赵悝团队', 1),
('余东升团队', 1),
('孔光明团队', 1),
('张超超团队', 1),
('宁夏自营分公司', 1),
('钢材金融分公司',1),
('赵孟晓团队', 1),
('陈璐团队', 1),
('杨邓团队', 1),
('田雪冬团队', 1),
('冷链团队', 1);

-- 外部客户: 上游、下游、贸易商, 运输方, 资金方, ccs账务公司
create table hs_party (
  id bigint(20)        not null auto_increment,
  custType tinyint       not null comment '1: ccs账务公司, 2: 资金方, 3: 外部',
  name varchar(128)      not null comment '客户名称',
  shortName varchar(128) not null comment '客户简称',
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;
insert into hs_party(custType, name, shortName) values
  (1, 'ccs', 'short');

