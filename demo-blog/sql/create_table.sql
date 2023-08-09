-- 创建库
create database if not exists blog;

-- 切换库
use blog;

-- 用户表
create table if not exists user
(
    id            bigint auto_increment comment 'id' primary key,
    user_name     varchar(255)                           null comment '用户昵称',
    user_account  varchar(255)                           not null comment '账号',
    user_avatar   varchar(1024)                          null comment '用户头像',
    gender        tinyint                                null comment '性别',
    user_role     varchar(255) default 'user'            not null comment '用户角色：user / admin',
    user_password varchar(255)                           not null comment '密码',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted    tinyint      default 0                 not null comment '是否删除',
    constraint uk_useraccount unique (user_account)
) comment '用户';

-- 文章信息表
create table if not exists article_info
(
    id             bigint auto_increment comment 'id' primary key,
    title          varchar(255)                       not null comment '文章标题',
    summary        varchar(1024)                      not null comment '文章摘要',
    category       varchar(255)                       comment '分类',
    word_count     int                                not null comment '字数统计',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted     tinyint  default 0                 not null comment '是否删除'
) comment '文章信息';

-- 文章内容表
create table if not exists article_content
(
    id             bigint auto_increment comment 'id' primary key,
    article_info_id     bigint                             not null comment '所属文章ID' ,
    content        MEDIUMTEXT                               not null comment '内容',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted     tinyint  default 0                 not null comment '是否删除'
) comment '文章内容';

-- 分类表
create table if not exists category
(
    id             bigint auto_increment comment 'id' primary key,
    category_name  varchar(255)                       not null comment '分类名',
    description    varchar(1024)                      not null comment '分类描述',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted     tinyint  default 0                 not null comment '是否删除'
) comment '分类';