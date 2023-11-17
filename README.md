# unique-tick

Prerequisites
-------------
Java 11 or above
Maven 3.5.0 or above
Both the above need to be present on the path.

How to build
------------
Navigate to the project's root using a command line.
From the project root, run `mvn clean install`
The following should appear:

BUILD SUCCESS

How to run tests
----------------
Navigate to the project's root using a command line.
From the project root, run `mvn test`
The following should appear:

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.hazelcast.uniquetick.process.TickGeneratorTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.256 s - in com.hazelcast.uniquetick.process.TickGeneratorTest
[INFO] Running com.hazelcast.uniquetick.process.TickGeneratorIntTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 s - in com.hazelcast.uniquetick.process.TickGeneratorIntTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
    ...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------


How to run application
-----------------------
There is a Spring Boot Application class that can be run from the main method.  This can be run from within any IDE.  
The endpoint can be accessed locally with the url http://localhost:8080/uniqueTick

