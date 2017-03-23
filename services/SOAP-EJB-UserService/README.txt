How to access the WSDL definition?
-------------------------------------------------------------------------------

How to access this SOAP service using SoapUI?
-------------------------------------------------------------------------------
File > New SOAP Project
Initial WSDL: http://localhost:8080/SOAP-EJB-UserService/UserService?wsdl

FindAll
-------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:findAll>
         <arg0>100</arg0>
         <arg1>66</arg1>
      </ser:findAll>
   </soapenv:Body>
</soapenv:Envelope>


FindById
--------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:findById>
         <arg0>3</arg0>
      </ser:findById>
   </soapenv:Body>
</soapenv:Envelope>

Insert
------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:insert>
         <arg0 id="0">
            <username>maggie</username>
            <password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>
         </arg0>
      </ser:insert>
   </soapenv:Body>
</soapenv:Envelope>

Note that (except of asking for the WSDL) SOAP always uses a POST request.
