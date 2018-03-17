How to setup the database?
-------------------------------------------------------------------------------

i) Start DB Server

$ su
root66
# systemctl start mariadb.service


ii) Create table and insert test data
All tables will be created as part of the deployment process.
Tipp: Have a look into src/main/resources/META-INF/persistence.xml


How to start the Web application from the command line?
-------------------------------------------------------------------------------

$ cd JSF-DataTable-CRUD
$ mvn wildfly:run


How to access the Web application?
-------------------------------------------------------------------------------

URL: http://localhost:8080/JSF-DataTable-CRUD


This example has been initially implemented by Simone Hierhold on September 2017.


