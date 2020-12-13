# Connote Number Generator
Consignment Note Number Generator

## Introduction
This is a Spring boot based Java application to generate next consignment note number based on the input parameters provided via the arguments. 

* The thought process for the solution was to cover most of the possible edge cases and optimum implementations even though the requirement is not so complex as real business requirements.
* The application is developed using Spring Boot framework, while it could have been done with an easy command line based program. The reason for this is to make sure the application can be easily extended to a web based API or any other mechanisms which are cloud friendly. 
* Also wherever possible, considerations were taken to make sure the application can be extended as the requirements change in future. Eg: Change in checksum calculation algorithm, Index digit size changes are possible easily.

### Design thought process
![Design thought proces](https://github.com/sudarsang/connote-num-generator/blob/master/assets/connote-num-generator-design-thought-process.jpg?raw=true)
## Assumptions
* First field in the Carrier Account POJO is the prefix, not the carrier name as the prefix code is required for the Connote generation.
* Assumed this is likely to become a cloud friendly web based application in future, so Spring Boot used to support that.
* Assumed it's enough to support just once off execution of generation to test the functionality. It's possible to extend this further to support from CSV file load/ REST API, etc as per the requirement.
* Assumption made that the Checksum should be 1 digit always. i.e, when the Index total comes in 10s multiples, the checksum should be 0, not 10. Eg: If the total sum comes as 40, then the checksum is 0 (40-40) for that scenario.

## Requirements
* JRE 1.11 or above needed to execute the program.
* Maven or an IDE with Maven support required to execute the Test cases

## Execute the program
* Extract the Zip file ConnoteGenerator.zip
* In Windows PC, Double click the HtpLogParser.bat
* For Mac/ Linux environments, execute the below command from a terminal
  * Go to the directory extracted 
  * java -jar connote-num-generator-1.0.0-SNAPSHOT.jar FMCC ABC123 10 19604 19000 20000
  * Steps and expected results are provided in the TestSteps.txt file.
  * It's posible to validate the expected result is displayed in the console
* Alternatively, it's possible to run the program using an IDE. Just need to make sure the Program arguments are given in the format Prefix Account Number Index Digits Last Used Index Index range start Index range end
  
 ### Help executing the application
  * The settings are passed via program arguments. The order of the values do matter, so the below given format needs to be followed:
    * Prefix Account Number Index Digits Last Used Index Index range start Index range end
    * Eg: FMCC ABC123 10 19604 19000 20000
## Maven Commands to run test cases
* Test cases require JUnit library for execution.
* Compile: mvn compile
* Test: mvn test
* Packaging: mvn package
  * The Jar file is saved under target directory
## Limitations/ Further enhancements
* While this is just an intereting small application, there are possibilities to improve this further.
* The input for the program are loaded via Program arguments currently, but loading multi records from CSV, extend this as a REST API can be done further.
* Usage of loggers with differnt log levels.

### Author
Sudarsan Gopal 
