How to start and stop the MySQL server?
---------------------------------------------------------------------

$ su
root66

# systemctl start mariadb.service

# systemctl stop mariadb.service


How to set the JDBC properties?
---------------------------------------------------------------------
Edit the jdbc.properties file.

Here are the default settings:

jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/testdb
jdbc.username=student
jdbc.password=student


How to access the MySQL server from the shell?
------------------------------------------------------------------------------

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

