How to start and stop the MySQL server?
---------------------------------------------------------------------

$ su
root66

# systemctl start mariadb.service

# systemctl stop mariadb.service


How to install the JDBC driver in Wildfly AS?
---------------------------------------------------------------------
(see Wildfly9Configurations)

Install a new module within the Wildfly AS:

/home/student/install/wildfly-9.0.1.Final/
└── modules/com/mysql/main/
    ├── module.xml
    └── mysql-connector-java-5.1.24-bin.jar


<module xmlns="urn:jboss:module:1.0" name="com.mysql">
        <resources>             
                <resource-root path="mysql-connector-java-5.1.24-bin.jar"/>     
        </resources>
        <dependencies>
                <module name="javax.api"/>
        </dependencies>
</module>

Add a new <datasource> and <driver> element to the standalone.xml file:

/home/student/install/wildfly-9.0.1.Final/standalone/configuration/standalone.xml:

		<subsystem xmlns="urn:jboss:domain:datasources:2.0">
            <datasources>
                <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
                    <driver>h2</driver>
                    <security>
                        <user-name>sa</user-name>
                        <password>sa</password>
                    </security>
                </datasource>
                <datasource jndi-name="java:jboss/datasources/MySqlDS" pool-name="MySqlDS" enabled="true" use-java-context="true" use-ccm="true">
                    <connection-url>jdbc:mysql://localhost:3306/testdb</connection-url>
                    <driver>mysql</driver>
                    <security>
                        <user-name>student</user-name>
                        <password>student</password>
                    </security>
                </datasource>
                <drivers>
                    <driver name="h2" module="com.h2database.h2">
                        <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
                    </driver>
                    <driver name="mysql" module="com.mysql">
                        <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
                    </driver>
                </drivers>
            </datasources>
        </subsystem>



How to create the user table in the MySQL server?
------------------------------------------------------------------------------

$ cd MVC-UserList
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


How to access tha application from a browser?
------------------------------------------------------------------------------

URL: http://localhost:8080/MVC-UserList/

