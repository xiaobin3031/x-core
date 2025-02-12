create database `x-core` default character set utf8mb4 collate utf8mb4_general_ci;
create user `x-core`@`%` identified by 'x-core.1234';
grant all privileges on `x-core`.* to `x-core`@`%`;

create table notes
(
    id       int auto_increment
        primary key,
    name     varchar(200)             not null comment '名称',
    add_time datetime default (now()) null comment '添加时间',
    deleted  tinyint  default 0       not null
);

create table note_content
(
    id              int auto_increment
        primary key,
    note_id         int                                not null,
    type            int      default 9999              not null comment '内容类型, 1 page, 2 table, 3 todo, 9999 text',
    content         varchar(1000)                      null comment '文本内容',
    checked         tinyint                            null,
    note_content_id int                                null comment '页面id',
    table_id        int                                null comment '表格id',
    after_id        int                                null comment '在哪个id之后',
    add_time        datetime default CURRENT_TIMESTAMP not null,
    deleted         tinyint  default 0                 not null
);
