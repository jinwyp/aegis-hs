-- 用户表
CREATE TABLE `hs_user` (
  `id` bigint(20)            not null auto_increment,
  `deptId` bigint(20)        not null comment '所属部门id',
  `securePhone` varchar(12)  not null comment '手机号',
  `password` varchar(40)     not null comment '密码',
  `passwordSalt` varchar(40) not null comment '密码盐',
  `createDate` datetime      not null comment '创建时间',
  `createBy` varchar(45)     not null comment '创建人',
  `isAdmin` tinyint          not null comment '是否为管理员',
  `isActive` tinyint(1) default '1'   comment '1 可用 0 禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`securePhone`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 部门表
CREATE TABLE `hs_dept` (
  `id` bigint(20)   not null auto_increment,
   name varchar(64) not null comment '部门名称',
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 团队表
CREATE TABLE `hs_team` (
  `id` bigint(20)    not null auto_increment,
   name varchar(64)  not null comment '团队名称',
   deptId bigint(20) not null comment '所属部门'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 外部客户: 上游、下游、贸易商, 运输方, 资金方, ccs账务公司
CREATE TABLE `hs_party` (
  `id` bigint(20)        not null auto_increment,
  custType tinyint       not null comment '1: ccs账务公司, 2: 资金方, 3: 外部',
  name varchar(128)      not null comment '客户名称'
  shortName varchar(128) not null comment '客户简称'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

