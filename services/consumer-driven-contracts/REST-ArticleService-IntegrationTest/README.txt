How to implement integration tests for services?
-------------------------------------------------------------------------------

1) Add the following plugin settings to pom.xml (see REST-IntegrationTest)
	- maven-surefire-plugin		<--! Unit tests -->
	- maven-failsafe-plugin		<--! Integration tests -->
	
2) Add test cases
	- ArticlesITCase (including DB setup + teardown, and property files)
			

How to run the integration test from the command line?
-------------------------------------------------------------------------------
$ mvn verify 
