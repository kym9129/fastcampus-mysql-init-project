create table Member
(
    id int auto_increment,
    email varchar(20) not null,
    nickname varchar(20) not null,
    birthday date not null,
    created_at datetime not null,
    updated_at datetime not null,
    constraint member_id_uindex
        primary key (id)
);

create table member_nickname_history
(
    id int auto_increment,
    memberId int not null,
    nickname varchar(20) not null,
    created_at datetime not null,
    updated_at datetime not null,
    constraint member_nickname_history_id_uindex
        primary key (id)
);

create table Follow
(
    id int auto_increment,
    from_member_id int not null,
    to_member_id int not null,
    created_at datetime not null,
    updated_at datetime not null,
    constraint Follow_id_uindex
        primary key (id)
);

create unique index Follow_from_member_id_to_member_id_uindex
    on Follow (from_member_id, to_member_id);


create table POST
(
    id int auto_increment,
    member_id int not null,
    contents varchar(100) not null,
    created_date date not null,
    created_at datetime not null,
    updated_at datetime not null,
    constraint POST_id_uindex
        primary key (id)
);

create index POST__index_member_id
    on POST (memberId);

create index POST__index_created_date
    on POST (createdDate);

create index POST__index_member_id_created_date
    on POST (memberId, createdDate);

create table Timeline
(
    id int auto_increment,
    member_id int not null,
    post_id int not null,
    created_at datetime not null,
    updated_at datetime not null,
    constraint Timeline_id_uindex
        primary key (id)
);


