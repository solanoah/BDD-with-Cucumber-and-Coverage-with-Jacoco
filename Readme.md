# Conference Track Management

## Problem Statement
You are planning a big programming conference and have received many proposals which have passed the initial screen process but you're having trouble fitting them into the time constraints of the day -- there are so many possibilities!

* The conference has multiple tracks each of which has a morning and afternoon session.
* Each session contains multiple talks.
* Morning sessions begin at 9am and must finish by 12 noon, for lunch.
* Afternoon sessions begin at 1pm and must finish in time for the networking event.
* The networking event can start no earlier than 4:00 and no later than 5:00.
* No talk title has numbers in it.
* All talk lengths are either in minutes (not hours) or lightning (5 minutes).
* Presenters will be very punctual; there needs to be no gap between sessions.

##Assumptions
* No talk can exceed 240minutes/48lightning
* Propose talk can be scheduled to any time slot.

## Solution
A first fit model was used to solving the problem using the duration of the proposed talks. Talks were sorted first, then the first talk was placed in the next available slot.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
See deployment for notes on how to deploy the project on a live system.

## Prerequisites
You need to install the following tools

* [Java 1.8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) - Lambda and Annotation were used in the code
* [Maven](https://maven.apache.org/) - Dependency Management
* [Cucumber](https://cucumber.io/docs/reference/jvm) - BDD and TDD
* [JaCoCo](http://www.eclemma.org/jacoco/trunk/doc/maven.html) - Code coverage

## Building the code:
* Unzip the supplied zip file
* From the terminal (MAC) navigate to the root location of the project, where the POM is.

```
mvn clean install
```

## Running the App
* Sample test files are located at /src/test/resources
```
From the root path, run the following commands:

* mvn exec:java -Dexec.mainClass=com.thoughtworks.dev.AppEntry -Dexec.args="src/test/resources/TestSample_minute.txt"
* mvn exec:java -Dexec.mainClass=com.thoughtworks.dev.AppEntry -Dexec.args="src/test/resources/TestSample_lightning.txt"
* mvn exec:java -Dexec.mainClass=com.thoughtworks.dev.AppEntry -Dexec.args="src/test/resources/TestSample_exception.txt"
* mvn exec:java -Dexec.mainClass=com.thoughtworks.dev.AppEntry -Dexec.args="src/test/resources/TestSample_networkEventStartTime.txt"
```

## Running the tests
```
mvn test
```

## Code Coverage
JaCoCo has been used to demonstrate the importance of test coverage as a best practice in every software development.
95% code coverage was achieved

```
Navigate to /target/site/jacoco/index.html
```

## Behavioral/Test driven development (BDD/TDD)
Over 146 tests were written to effectively test all scenarios.

* Feature files are located at /src/test/resources
```
Navigate to /target/site/cucumber-reports/feature-overview.html
```

## TO DO
* Generate javadoc
* Implement proper logging 

