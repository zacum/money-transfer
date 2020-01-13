# Money Transfer REST API

The following project is a simple REST API for money transferring between accounts. The application is designed to be a standalone executable program that could be run without any additional setup. The program supports basic operations with accounts, such as creation, list and querying one by one. The program also allows to deposit, withdraw or transfer money between two accounts.

# Requirements

* Java 11
* Maven 3

# Frameworks

* Spark
* Guice
* Gson
* Moneta
* H2
* Norm
* slf4j
* JUnit 4

# Build

```maven
mvn clean install
```

# Run

```maven
java -jar target/moneytransfer-0.1-jar-with-dependencies.jar
```

# Database Model
The data model is designed around two entities: accounts and transactions. Accounts represent the user account in the system that can hold money is the pre-selected currency. 

# REST API
