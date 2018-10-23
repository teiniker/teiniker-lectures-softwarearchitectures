How to start and stop the MySQL server?
---------------------------------------------------------------------

$ sudo systemctl start mariadb.service

$ sudo systemctl stop mariadb.service



How to create the user table in the MySQL server?
------------------------------------------------------------------------------

$ cd MVC-UserList/src/main/resources/sql
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


How to start the Wildfly application server?
------------------------------------------------------------------------------

$ cd JBOSS_HOME
$ bin/standalone.sh


How to build and deploy the Web application using Maven?
------------------------------------------------------------------------------
Make sure that you already have installed the following modules:
    DAO-JDBC-User
    ServiceLayer-UserService

$ mvn wildfly:deploy


How to access tha application from a browser?
------------------------------------------------------------------------------

URL: http://localhost:8080/MVC-UserList/

