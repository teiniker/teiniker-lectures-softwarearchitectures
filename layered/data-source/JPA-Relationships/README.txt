Generated DB Schema by Hibernate
-------------------------------------------------------------------------------

	create table TEST_ADDRESS (
		id number(10,0) not null,
		city varchar2(255 char),
		state varchar2(255 char),
		street varchar2(255 char),
		primary key (id)
	)
	
	create table TEST_CREDIT_CARD (
		id number(10,0) not null,
		CARD_NUMBER varchar2(255 char),
		primary key (id)
	)
	
	create table TEST_CUSTOMER (
		id number(10,0) not null,
		firstName varchar2(255 char),
		lastName varchar2(255 char),
		ADDRESS_ID number(10,0),
		CREDIT_CARD_ID number(10,0),
		primary key (id),
		foreign key (ADDRESS_ID) references TEST_ADDRESS,
		foreign key (CREDIT_CARD_ID) references TEST_CREDIT_CARD
	)
	
	create table TEST_PHONE (
		id number(10,0) not null,
		PHONE_NUMBER varchar2(255 char),
		CUSTOMER_ID number(10,0),
		primary key (id),
		foreign key (CUSTOMER_ID) references TEST_CUSTOMER
	)
	
	create table TEST_RESERVATION (
		id number(10,0) not null,
		AMOUNT_PAID number(19,0),
		DATE_RESERVED timestamp,
		primary key (id)
	)
	
	create table TEST_RESERVATION_CUSTOMER (
		RESERVATION_ID number(10,0) not null,
		CUSTOMER_ID number(10,0) not null,
		foreign key (RESERVATION_ID) references TEST_RESERVATION,
		foreign key (CUSTOMER_ID) references TEST_CUSTOMER
	)

	create sequence hibernate_sequence

