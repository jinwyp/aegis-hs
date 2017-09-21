use hsdb;

-- 用户表
create table hs_user (
  id bigint(20)            not null auto_increment,
  deptId bigint(20)        not null comment '所属部门id',
  phone varchar(12)        not null comment '手机号',
  password varchar(40)     not null comment '密码',
  passwordSalt varchar(40) not null comment '密码盐',
  createDate datetime      not null comment '创建时间',
  createBy varchar(45)     not null comment '创建人',
  isAdmin tinyint(1)       not null comment '是否为管理员',
  isActive tinyint(1) default '1'   comment '1 可用 0 禁用',
  PRIMARY KEY (id),
  UNIQUE KEY name_UNIQUE (phone)
)engine=InnoDB default charset=utf8;
insert into hs_user(deptId, phone, password, passwordSalt, createDate, createBy, isAdmin) values
  (1, '13022117050', '12345678', 'salt', '2017-01-01', 'hary', 1);

-- 部门表
create table hs_dept (
  id bigint(20)    not null auto_increment,
  name varchar(64) not null comment '部门名称',
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;
insert into hs_dept(name) values('管理部');
insert into hs_dept(name) values('金融产品事业部')
;

-- 团队表
create table hs_team (
  id bigint(20)     not null auto_increment,
  name varchar(64)  not null comment '团队名称',
  deptId bigint(20) not null comment '所属部门',
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;
alter table hs_team add foreign key(deptId) references hs_dept(id);

insert into hs_team(name, deptId) values
('赵善文团队', 2),
('张培栓团队', 2),
('魏靖团队',2),
('卢昆团队', 2),
('赵悝团队', 2),
('余东升团队', 2),
('孔光明团队', 2),
('张超超团队', 2),
('宁夏自营分公司', 2),
('钢材金融分公司',2),
('赵孟晓团队', 2),
('陈璐团队', 2),
('杨邓团队', 2),
('田雪冬团队', 2),
('冷链团队', 2);

-- 外部客户: 上游、下游、贸易商, 运输方, 资金方, ccs账务公司
create table hs_party (
  id bigint(20)          not null auto_increment,
  custType int       not null comment '1: ccs账务公司, 2: 资金方, 3: 外部',
  name varchar(128)      not null comment '客户名称',
  shortName varchar(128) not null comment '客户简称',
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;
insert into hs_party(custType, name, shortName) values
  (1, '江苏晋和电力燃料有限公司', '晋和'),
  (1, '那曲瑞昌煤炭运销有限公司','那曲'),
  (1, '郑州嘉瑞供应链管理有限公司', '嘉瑞'),
  (1, '山西瑞茂通供应链有限公司', '山瑞'),
  (1, '宁夏瑞茂通供应链管理有限公司', '宁夏瑞茂通'),
  (1, '宁夏华运昌煤炭运销有限公司', '华运昌'),
  (1, '宁夏腾瑞达电力燃料有限公司', '腾瑞达'),
  (1, '陕西秦瑞丰煤炭运销有限公司', '秦瑞丰'),
  (1, '陕西吕通贸易有限公司', '吕通'),
  (1, '新疆瑞茂通煤炭供应链管理有限公司', '新疆瑞茂通'),
  (1, '浙江瑞茂通供应链管理有限公司', '浙江瑞茂通'),
  (1, '浙江和辉电力燃料有限公司', '和辉'),
  (1, '宣威瑞茂通商贸有限公司', '宣威瑞茂通'),
  (1, '江西瑞茂通供应链管理有限公司', '江西瑞茂通'),
  (1, '上海瑞易供应链管理有限公司', '上海瑞易'),
  (1, '深圳前海瑞茂通供应链平台服务有限公司', '前海瑞茂通'),
  (2, '中瑞财富', ''),
  (2, '中平金融', '');

