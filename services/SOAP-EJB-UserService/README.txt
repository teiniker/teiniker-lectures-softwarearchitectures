How to access the WSDL definition?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SOAP-EJB-UserService/UserService?wsdl


findAll()
-------------------------------------------------------------------------------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:findAll>
         <arg0>0</arg0>
         <arg1>0</arg1>
      </ser:findAll>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:findAllResponse xmlns:ns2="http://service.lab.se.org/">
         <return id="1">
            <username>homer</username>
            <password>ijD8qepbRnIsva0kx0cKRCcYysg=</password>
         </return>
         <return id="2">
            <username>marge</username>
            <password>xCSuPDv2U6I5jEO1wqvEQ/jPYhY=</password>
         </return>
         <return id="3">
            <username>bart</username>
            <password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>
         </return>
         <return id="4">
            <username>lisa</username>
            <password>xO0U4gIN1F7bV7X7ovQN2TlSUF4=</password>
         </return>
      </ns2:findAllResponse>
   </soap:Body>
</soap:Envelope>



findById()
-------------------------------------------------------------------------------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:findById>
         <arg0>2</arg0>
      </ser:findById>
   </soapenv:Body>
</soapenv:Envelope>


<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:findByIdResponse xmlns:ns2="http://service.lab.se.org/">
         <return id="2">
            <username>marge</username>
            <password>xCSuPDv2U6I5jEO1wqvEQ/jPYhY=</password>
         </return>
      </ns2:findByIdResponse>
   </soap:Body>
</soap:Envelope>


delete()
-------------------------------------------------------------------------------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:delete>
         <arg0>2</arg0>
      </ser:delete>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:deleteResponse xmlns:ns2="http://service.lab.se.org/"/>
   </soap:Body>
</soap:Envelope>


insert()
-------------------------------------------------------------------------------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:insert>
         <arg0 id="0">
            <username>burns</username>
            <password>YDYg2ELvjAWIllkU7wbECt/lr6w=</password>
         </arg0>
      </ser:insert>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:insertResponse xmlns:ns2="http://service.lab.se.org/">
         <return>
            <id>0</id>
            <password>YDYg2ELvjAWIllkU7wbECt/lr6w=</password>
            <username>burns</username>
         </return>
      </ns2:insertResponse>
   </soap:Body>
</soap:Envelope>


update()
-------------------------------------------------------------------------------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:update>
         <arg0>5</arg0>
         <arg1 id="5">
            <username>burns</username>
            <password>yVJZ3h/XGYFNrvjx3EvWT52IX/A=</password>
         </arg1>
      </ser:update>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:updateResponse xmlns:ns2="http://service.lab.se.org/"/>
   </soap:Body>
</soap:Envelope>
