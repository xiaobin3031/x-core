alter table note_content drop column table_id;

alter table note_content add heads varchar(1000) null after after_id;

alter table note_content add orders varchar(500) null after heads;

alter table note_content add types varchar(500) null after orders;

alter table note_content add updated tinyint null after types;

alter table note_content add update_time bigint null after updated;

create index idx_updated on note_content (updated);

create table note_table_datas
(
    id       int auto_increment
        primary key,
    content_id int               not null,
    datas    varchar(4000)     null,
    deleted  tinyint default 0 not null
);

alter table note_table_datas add after_id int null after content_id;

alter table note_table_datas add before_id int null after content_id;

alter table note_content add before_id int null after note_content_id;