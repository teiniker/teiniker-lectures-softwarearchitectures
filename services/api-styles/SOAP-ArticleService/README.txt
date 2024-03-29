Example: SOAP-Article Service
-------------------------------------------------------------------------------

Build and Deploy:
-----------------
$ mvn wildfly:deploy

Setup:
------
$ sudo systemctl start mariadb.service

$ mysql -u student -p
Enter password: student

MariaDB [(none)]> use testdb;

copy + paste test/resources/sql/createTale.sql


Access the WSDL definition:
---------------------------
http://localhost:8080/SOAP-ArticleService/ArticleService?wsdl


SOAP Messages (use SoapUI)
--------------------------
insert()
--------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:insert>
         <arg0 price="9999">
            <description>Book: Effective Java</description>
         </arg0>
      </ser:insert>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:insertResponse xmlns:ns2="http://service.lab.se.org/"/>
   </soap:Body>
</soap:Envelope>



findAll()
---------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:findAll/>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:findAllResponse xmlns:ns2="http://service.lab.se.org/">
         <return id="1" price="4295">
            <description>Design Patterns</description>
         </return>
         <return id="2" price="3336">
            <description>Effective Java (2nd Edition)</description>
         </return>
      </ns2:findAllResponse>
   </soap:Body>
</soap:Envelope>



findById()
----------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:findById>
         <arg0>1</arg0>
      </ser:findById>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:findByIdResponse xmlns:ns2="http://service.lab.se.org/">
         <return id="1" price="4295">
            <description>Design Patterns</description>
         </return>
      </ns2:findByIdResponse>
   </soap:Body>
</soap:Envelope>



delete()
--------


update()
--------


How to generate the DB schema from entity classes?
-------------------------------------------------------------------------------

see persistence.xml

<property name="hibernate.hbm2ddl.auto" value="create-drop" />

