create table hs_user
(
	id bigint auto_increment primary key,
	personalName varchar(25) not null comment '姓名',
	securePhone varchar(12) not null comment '手机号' ,
	isActive tinyint(1) default '1' null comment '1 可用 0 禁用',
	password varchar(40) not null comment '密码',
	passwordSalt varchar(40) not null comment '密码盐',
	createDate datetime not null comment '创建时间',
	constraint name_UNIQUE unique (securePhone)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
