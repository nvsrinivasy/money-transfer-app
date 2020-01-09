# money-transfer-app
Rest API for money transfer between accounts

### Technology stack
- Java
- Maven 3 
- JAX-RS API
- H2 in memory database
- Log4j
- Jetty Container 
- Jersey Rest API with JUnit.

Application can be started from standalone jar:
or maven 

mvn exec:java

Application will be starts on jetty server - localhost port 8585 (port and contextpath -configurable in propertes file) and H2 in-memory database is initialized with sample user and account data.(note: Assuming we have account and user data in database and implemented transferfunds api to make it simple).

## Money transfer API - 
1.transfer funds API
http://localhost:8585/app/account/transferFund

**POST**
request:

{     
   "amount":750.00,
   "fromAccountId":100011,
   "toAccountId":100012
}

Response:
**Status: 200 OK**

2.Account balance check

*GET**

http://localhost:8585/app/account/{accountId}

Request:
http://localhost:8585/app/account/100012


Reponse:

{
    "accountId": 100012,
    "userName": "Nag",
    "balance": 10000,
    "createdDate": "2020-01-08",
    "lastUpdatedDate": "2020-01-09"
}

