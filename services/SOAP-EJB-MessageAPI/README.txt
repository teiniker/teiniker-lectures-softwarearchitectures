How to access the WSDL definition?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SOAP-EJB-MessageAPI/OrderService?wsdl


How to access the SOAP service?
-------------------------------------------------------------------------------

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:process>
         <arg0 id="1">
            <name>ORDER-20151213</name>
            <lines>
               <quantity>5</quantity>
               <product>
                  <description>Effective Java</description>
                  <price>3900</price>
               </product>
            </lines>
         </arg0>
      </ser:process>
   </soapenv:Body>
</soapenv:Envelope>

=> Order [id=1, name=ORDER-20151213, lines=[OrderLine [quantity=5, product=Effective Java,3900]]]


