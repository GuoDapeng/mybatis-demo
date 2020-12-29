create database link_user;

use link_user;

-- ------------------------------------------------------------
-- 用户
-- ------------------------------------------------------------
create table user_base
(
    id            bigint(64)              not null comment '主键id',
    account       varchar(255) default '' not null comment '用户名',
    password      varchar(60)  default '' not null comment '密码',
    nickname      varchar(255) default '' not null comment '昵称',
    avatar        varchar(255) default '' not null comment '头像',
    mobile        varchar(50)  default '' not null comment '手机号',
    email         varchar(255) default '' not null comment '邮箱',
    birthday      int(10)      default -1 not null comment '生日',
    sex           tinyint(3)   default -1 not null comment '性别，男（1），女（2）',
    integral      int(20)      default 0  not null comment '积分',
    service_level tinyint(3)   default 0  not null comment '服务水平；普通（0）',
    create_time   bigint(20)   default 0  not null comment '创建时间',
    update_time   bigint(20)   default 0  not null comment '更新时间',
    status        tinyint(3)   default 1  not null comment '状态；逻辑删除（-1），隐藏（0），显示（1）',
    unique key (account),
    primary key (id)
)
    engine = innodb
    default charset = utf8mb4
    auto_increment = 10
    comment ='用户表';

-- ------------------------------------------------------------
-- 角色
-- ------------------------------------------------------------
create table role
(
    id          bigint(64)           not null comment '主键id',
    role        varchar(255)         not null comment '角色名称',
    create_time bigint(20) default 0 not null comment '创建时间',
    update_time bigint(20) default 0 not null comment '更新时间',
    status      tinyint(3) default 1 not null comment '状态；逻辑删除（-1），隐藏（0），显示（1）',
    unique key (role),
    primary key (id)
)
    engine = innodb
    default charset = utf8mb4
    auto_increment = 10
    comment '角色';

insert into role (id, create_time, update_time, status, role)
values (10, 0, 0, 1, 'ROLE_USER'),
       (11, 0, 0, 1, 'ROLE_ADMIN');

-- ------------------------------------------------------------
-- 用户角色关系
-- ------------------------------------------------------------
create table user_roles
(
    user_id     bigint(64)           not null comment '用户id',
    roles_id    bigint(64)           not null comment '角色id',
    create_time bigint(20) default 0 not null comment '创建时间',
    update_time bigint(20) default 0 not null comment '更新时间',
    status      tinyint(3) default 1 not null comment '状态；逻辑删除（-1），隐藏（0），显示（1）',
    primary key (user_id, roles_id)
)
    engine = innodb
    default charset = utf8mb4
    auto_increment = 10
    comment '用户角色关系';
