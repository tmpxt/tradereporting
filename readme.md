# Sample Data
I have use [Mockaroo](https://www.mockaroo.com/) to generate sample data (see mocks/mock_data.json).
In this file, settlement date is not coherent regarding instruction date but that's not a matter for this technical test.

# Execution
This project has been made using JAVA 1.8.0_162.
As this a Maven project you must have installed Apache Maven ([see how to install maven if needed](https://maven.apache.org/install.html)).

To run the console application, execute:

`mvn exec:java -Dexec.mainClass="com.xterr.tradereporting.App"`

To run unit tests, execute:

`mvn test`

For convenience, I have packaged the project so you can run it without Maven:

`java -cp jar/*:. com.xterr.tradereporting.App`
