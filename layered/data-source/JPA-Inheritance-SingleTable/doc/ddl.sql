
-- Single Table Strategy
 	drop table TEST_PERSON;
    drop sequence hibernate_sequence;
    
    create table TEST_PERSON (
        DISCRIMINATOR varchar2(31 char) not null,
        id number(10,0) not null,
        firstName varchar2(255 char),
        lastName varchar2(255 char),
        city varchar2(255 char),
        state varchar2(255 char),
        street varchar2(255 char),
        employeeId number(10,0),
        primary key (id)
    );

    create sequence hibernate_sequence;

    
-- Table per Class Strategy
 	drop table TEST_CUSTOMER;
 	drop table TEST_EMPLOYEE;
 	drop table TEST_PERSON;
    drop sequence hibernate_sequence;
    
   create table TEST_CUSTOMER (
       id number(10,0) not null,
       firstName varchar2(255 char),
       lastName varchar2(255 char),
       city varchar2(255 char),
       state varchar2(255 char),
       street varchar2(255 char),
       primary key (id)
   );

   create table TEST_EMPLOYEE (
       id number(10,0) not null,
       firstName varchar2(255 char),
       lastName varchar2(255 char),
       employeeId number(10,0) not null,
       primary key (id)
   );

   create table TEST_PERSON (
       id number(10,0) not null,
       firstName varchar2(255 char),
       lastName varchar2(255 char),
       primary key (id)
   );

   create sequence hibernate_sequence;
   
   
   
-- Joint Strategy

    drop table TEST_CUSTOMER cascade constraints;
    drop table TEST_EMPLOYEE cascade constraints;
    drop table TEST_PERSON cascade constraints;
    drop sequence hibernate_sequence;

    create table TEST_CUSTOMER (
        city varchar2(255 char),
        state varchar2(255 char),
        street varchar2(255 char),
        id number(10,0) not null,
        primary key (id)
    );

    create table TEST_EMPLOYEE (
        employeeId number(10,0) not null,
        id number(10,0) not null,
        primary key (id)
    );

    create table TEST_PERSON (
        id number(10,0) not null,
        firstName varchar2(255 char),
        lastName varchar2(255 char),
        primary key (id)
    );

    alter table TEST_CUSTOMER
        add constraint FK1C4B68EB42A09B67
        foreign key (id)
        references TEST_PERSON;

    alter table TEST_EMPLOYEE
        add constraint FK3F4CCFBB42A09B67
        foreign key (id)
        references TEST_PERSON;

    create sequence hibernate_sequence;
    