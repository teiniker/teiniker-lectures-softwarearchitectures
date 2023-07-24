
    drop table TEST_ADDRESS cascade constraints;

    drop table TEST_CREDIT_CARD cascade constraints;

    drop table TEST_CUSTOMER cascade constraints;

    drop table TEST_PHONE cascade constraints;

    drop table TEST_RESERVATION cascade constraints;

    drop table TEST_RESERVATION_CUSTOMER cascade constraints;

    drop sequence hibernate_sequence;

    create table TEST_ADDRESS (
        id number(10,0) not null,
        city varchar2(255 char),
        state varchar2(255 char),
        street varchar2(255 char),
        primary key (id)
    );

    create table TEST_CREDIT_CARD (
        id number(10,0) not null,
        CARD_NUMBER varchar2(255 char),
        primary key (id)
    );

    create table TEST_CUSTOMER (
        id number(10,0) not null,
        firstName varchar2(255 char),
        lastName varchar2(255 char),
        ADDRESS_ID number(10,0),
        CREDIT_CARD_ID number(10,0),
        primary key (id)
    );

    create table TEST_PHONE (
        id number(10,0) not null,
        PHONE_NUMBER varchar2(255 char),
        CUSTOMER_ID number(10,0),
        primary key (id)
    );

    create table TEST_RESERVATION (
        id number(10,0) not null,
        AMOUNT_PAID number(19,0),
        DATE_RESERVED timestamp,
        primary key (id)
    );

    create table TEST_RESERVATION_CUSTOMER (
        RESERVATION_ID number(10,0) not null,
        CUSTOMER_ID number(10,0) not null
    );

    alter table TEST_CUSTOMER 
        add constraint FK1C4B68EB4567E923 
        foreign key (ADDRESS_ID) 
        references TEST_ADDRESS;

    alter table TEST_CUSTOMER 
        add constraint FK1C4B68EB4E21BA84 
        foreign key (CREDIT_CARD_ID) 
        references TEST_CREDIT_CARD;

    alter table TEST_PHONE 
        add constraint FKCEBDAF61BCCE0E31 
        foreign key (CUSTOMER_ID) 
        references TEST_CUSTOMER;

    alter table TEST_RESERVATION_CUSTOMER 
        add constraint FK3C5B789E4BA9DA3 
        foreign key (RESERVATION_ID) 
        references TEST_RESERVATION;

    alter table TEST_RESERVATION_CUSTOMER 
        add constraint FK3C5B789EBCCE0E31 
        foreign key (CUSTOMER_ID) 
        references TEST_CUSTOMER;

    create sequence hibernate_sequence;
