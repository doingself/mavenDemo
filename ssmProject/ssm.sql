
drop database if exists `ssm`;

create database if not exists ssm character set utf8;

use `ssm`;

drop TABLE if EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(30) NOT NULL COMMENT 'pwd',
  `name` varchar(32) COMMENT '名称',
  `is_disabled` tinyint(1) DEFAULT '0' COMMENT '1=禁用',
  `is_deleted` char(1) DEFAULT '0' COMMENT '1=删除',
  `create_time` datetime COMMENT '添加时间',

  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_UNAME` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

drop table if exists `role`;
create table `role`(
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `description` text,
  `is_disabled` tinyint(1) DEFAULT '0' COMMENT '1=禁用',
  `is_deleted` char(1) DEFAULT '0' COMMENT '1=删除',
  PRIMARY KEY (`id`)
);

drop table if exists `r_user_role`;
create table `r_user_role`(
  `id` bigint(20) DEFAULT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Users_Roles_idx` (`user_id`),
  KEY `Roles_Users_idx` (`role_id`),
  CONSTRAINT `Users_Roles_idx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Roles_Users_idx` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


insert into `user`(`username`,`password`) values('admin','abc123');
insert into `user`(`username`,`password`) values('temp','abc123');

insert into `role`(`name`) values('manager');
insert into `role`(`name`) values('operator');

insert into r_user_role(`user_id`,`role_id`) values(1,1);
insert into r_user_role(`user_id`,`role_id`) values(2,2);

select * from user;
select * from role;
select * from r_user_role;

select t1.username,t1.password,t1.name,t3.name role_name
from user t1
left join r_user_role t2 on t1.id = t2.user_id
left join role t3 on t2.role_id = t3.id
where 1=1;
