
/*
create database ssm;

use ssm;



*/

create table t_user(
id int not null auto_increment, -- id 自增
user_name varchar(20) not null unique, -- 唯一
pass varchar(20) not null,
name varchar(20),
is_del 

constraint pk_user primary key (id)

);