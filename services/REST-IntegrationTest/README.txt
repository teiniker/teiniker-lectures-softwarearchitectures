How to Configure Maven for Wildfly Deployment?
-------------------------------------------------------------------------------

- Create a manamement user for Wildfly, runing the add-user.bat
- Add configuration to pom.xml
	
	<build>
		<plugins>
			<plugin>
				<groupId>org.wildfly.plugins</groupId>
				<artifactId>wildfly-maven-plugin</artifactId>
				<version>${version.wildfly.maven.plugin}</version>
                    <!--
                    -->
				<configuration>
                    <jbossHome>C:/install/wildfly-8.1.0.Final</jbossHome>
                    <port>10090</port>
                  	<!--
                    <username>USERNAME</username>
                    <password>PASSWORD</password>
                    -->
                </configuration>
			</plugin>
 	...
 		</plugins>
 	</build>
 	
 	
- Deploy WAR file via maven
	
	
	Deploy the application to application server invoking no other goals by default.
	> mvn wildfly:deploy-only


	Deploy the application to the application server.
	> mvn wildfly:deploy -DskipTests 	

	Redeploys the application.
	> mvn wildfly:redeploy -DskipTests 	

	Undeploys the application.
	> mvn wildfly:undeploy -DskipTests 	

	
	Shut down a running application server.
	> mvn wildfly:shutdown -DskipTests 	
	
	Start the application server and shuts it down at last when the maven process ends 
	unless an explicit shutdown from a management client or the shutdown goal is executed.
	> mvn wildfly:start -DskipTests 	
	
	Run the application server and deploy your application.
	> mvn wildfly:run -DskipTests 	


How to add automatic start, deployment and shutdown to the build process?
-------------------------------------------------------------------------------

			<plugin>
				<groupId>org.wildfly.plugins</groupId>
				<artifactId>wildfly-maven-plugin</artifactId>
				<version>${version.wildfly.maven.plugin}</version>
				<configuration>
					...
		       	</configuration>
		
				<executions>		
					<!-- Run wildfly and deploy application for integration tests. -->
					<execution>
						<id>wildfly-run</id>
						<phase>pre-integration-test</phase>
						<goals>
							<goal>start</goal>
							<goal>deploy</goal>
						</goals>
					</execution>
		
					<!-- Integration test teardown. -->
					<execution>
						<id>wildfly-shutdown</id>
						<phase>post-integration-test</phase>
						<goals>
							<goal>undeploy</goal>
							<goal>shutdown</goal>
						</goals>
					</execution>
				</executions>
			</plugin>


How to implement and run integration tests?
-------------------------------------------------------------------------------
	
*) Maven Failsafe Plugin	
	http://maven.apache.org/surefire/maven-failsafe-plugin/

      		<plugin>
	        	<groupId>org.apache.maven.plugins</groupId>
	            <artifactId>maven-failsafe-plugin</artifactId>
	            <version>${version.failsafe.plugin}</version>
	            <executions>
	            	<execution>
	                	<goals>
	                    	<goal>integration-test</goal>
	                        <goal>verify</goal>
	                    </goals>
	                </execution>
	            </executions>
	    	</plugin>
	    	
*) Rename integration tests into */*ITCase.java

*) Run integration tests (pre-integration-test, integration-test, post-integration-test)	
	$ mvn verify
	
	Note that this command runs JBoss, deploys the WAR file, executes the test cases,
	undeploys the WAR file, and shutdown the AS.
		
   	All test reports will be generated to target/failsafe-reports
   
    	