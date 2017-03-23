How to setup the database?
-------------------------------------------------------------------------------

1. Start the MySQL server
-------------------------

$ su
root66

# systemctl start mysqld.service

2. Setup database schema
------------------------

$ cd DAO-JDBC-User

$ mysql -u student -p
Enter password: student

MariaDB [(none)]> use testdb;
[testdb]> show tables;

[testdb]> source sql/createUserTable.sql

[testdb]> describe user;
+-----------+-------------+------+-----+---------+----------------+
| Field     | Type        | Null | Key | Default | Extra          |
+-----------+-------------+------+-----+---------+----------------+
| id        | int(11)     | NO   | PRI | NULL    | auto_increment |
| firstname | varchar(32) | NO   |     | NULL    |                |
| lastname  | varchar(32) | NO   |     | NULL    |                |
| username  | varchar(32) | NO   |     | NULL    |                |
| password  | varchar(32) | NO   |     | NULL    |                |
+-----------+-------------+------+-----+---------+----------------+

[testdb]> quit


How to generate code for interprocess communication?
-------------------------------------------------------------------------------

$ ant generate.endpoint

Refresh your project in Eclipse [F5]
Run the org.se.lab.ws.Main class as Java Application.

$ ant generate.stubs
Refresh your project in Eclipse [F5]
Run the org.se.lab.RemoteFacadeTest class as JUnit test.

Note that the execution is delegated to the ServiceLayer and DAO projects.

