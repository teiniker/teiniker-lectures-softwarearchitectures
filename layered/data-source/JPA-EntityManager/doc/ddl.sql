drop table TEST_USER;

drop sequence hibernate_sequence;


create table TEST_USER (
    id number(10,0) not null,
    password varchar2(255 char),
    USER_NAME varchar2(64 char),
    primary key (id)
);

create sequence hibernate_sequence;
    