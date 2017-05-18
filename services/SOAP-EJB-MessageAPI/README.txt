How to access the WSDL definition?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SOAP-EJB-MessageAPI/OrderService?wsdl


process(Order)
-------------------------------------------------------------------------------

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:process>
         <arg0>
            <name>FHJ-20151020-007</name>
            <lines>
               <quantity>2</quantity>
               <product>
                  <description>Effective Java</description>
                  <price>3336</price>
               </product>
            </lines>
            <lines>
               <quantity>7</quantity>
               <product>
                  <description>Design Patterns</description>
                  <price>5280</price>
               </product>
            </lines>
         </arg0>
      </ser:process>
   </soapenv:Body>
</soapenv:Envelope>


<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:processResponse xmlns:ns2="http://service.lab.se.org/"/>
   </soap:Body>
</soap:Envelope>
