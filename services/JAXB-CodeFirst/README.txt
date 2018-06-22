
How to generate XML schema from annotated Java classes?
-------------------------------------------------------------------------------

$ schemagen src/main/java/org/se/lab/*.java

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">

  <xs:element name="order" type="order"/>

  <xs:complexType name="entity" abstract="true">
    <xs:sequence/>
    <xs:attribute name="id" type="xs:long" use="required"/>
  </xs:complexType>

  <xs:complexType name="order">
    <xs:complexContent>
      <xs:extension base="entity">
        <xs:sequence>
          <xs:element name="name" type="xs:string" minOccurs="0"/>
          <xs:element name="lines" type="orderLine" nillable="true" minOccurs="0" maxOccurs="unbounded"/>
        </xs:sequence>
      </xs:extension>
    </xs:complexContent>
  </xs:complexType>

  <xs:complexType name="orderLine">
    <xs:complexContent>
      <xs:extension base="entity">
        <xs:sequence>
          <xs:element name="quantity" type="xs:int"/>
          <xs:element name="product" type="product" minOccurs="0"/>
        </xs:sequence>
      </xs:extension>
    </xs:complexContent>
  </xs:complexType>

  <xs:complexType name="product">
    <xs:complexContent>
      <xs:extension base="entity">
        <xs:sequence>
          <xs:element name="description" type="xs:string" minOccurs="0"/>
          <xs:element name="price" type="xs:long"/>
        </xs:sequence>
      </xs:extension>
    </xs:complexContent>
  </xs:complexType>
</xs:schema>


How to add a wrapper element for collections?
-------------------------------------------------------------------------------
Java:
	/*
	 * Association: ---[*]-> OrderLines
	 */
	@XmlElementWrapper(name = "lines")
	@XmlElement(name = "line")
	private List<OrderLine> lines = new ArrayList<>();
	
XSD:	
	  <xs:complexType name="order">
	    <xs:complexContent>
	      <xs:extension base="entity">
	        <xs:sequence>
	          <xs:element name="name" type="xs:string" minOccurs="0"/>
	          <xs:element name="lines" minOccurs="0">
	            <xs:complexType>
	              <xs:sequence>
	                <xs:element name="line" type="orderLine" minOccurs="0" maxOccurs="unbounded"/>
	              </xs:sequence>
	            </xs:complexType>
	          </xs:element>
	        </xs:sequence>
	      </xs:extension>
	    </xs:complexContent>
	  </xs:complexType>
	
XML:	
	 <lines>
        <line id="101">
            <quantity>2</quantity>
            <product id="102">
                <description>Effective Java</description>
                <price>3336</price>
            </product>
        </line>
        <line id="101">
            <quantity>2</quantity>
            <product id="103">
                <description>Design Patterns</description>
                <price>5280</price>
            </product>
        </line>
    </lines>
    
	

Also see:
https://docs.oracle.com/javase/tutorial/jaxb/intro/index.html
