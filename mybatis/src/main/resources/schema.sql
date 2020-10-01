DROP TABLE IF EXISTS t_person;
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_privilege;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_role_privilege;
SET default_storage_engine=InnoDB;
CREATE TABLE IF NOT EXISTS t_person
(
    id      INTEGER PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(64) NOT NULL,
    age     INT         NOT NULL,
    address VARCHAR(64) NOT NULL
);
CREATE table IF NOT EXISTS country
(
    id          INT          NOT NULL AUTO_INCREMENT,
    countryName varchar(255) NULL,
    countryCode varchar(255) NULL,
    PRIMARY KEY (id)
);
CREATE TABLE sys_user
(
    id            bigint not null auto_increment comment '用户id',
    user_name     varchar(50) comment '用户名',
    user_password varchar(50) comment '密码',
    user_email    varchar(50) comment '邮箱',
    user_info     text comment '简介',
    head_img      blob comment '头像',
    create_time   datetime comment '创建时间',
    primary key (id)
) COMMENT ='用户表';
CREATE TABLE sys_role
(
    id          bigint not null auto_increment comment '角色id',
    role_name   varchar(50) comment '角色名',
    enabled     int comment '有效标志',
    create_by   bigint comment '创建人',
    create_time datetime comment '创建时间',
    primary key (id)
) COMMENT ='角色表';
CREATE TABLE sys_privilege
(
    id             bigint not null auto_increment comment '权限id',
    privilege_name varchar(50) comment '权限名',
    privilege_url  varchar(50) comment '权限url',
    primary key (id)
) COMMENT ='权限表';
CREATE TABLE sys_user_role
(
    user_id bigint comment '用户ID',
    role_id bigint comment '角色ID'
) COMMENT '用户角色关联表';
CREATE TABLE sys_role_privilege
(
    role_id      bigint comment '角色ID',
    privilege_id bigint comment '权限ID'
) COMMENT '角色权限关联表';