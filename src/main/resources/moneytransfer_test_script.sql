--This script is creatd for Intial data setup

DROP TABLE IF EXISTS User;

CREATE TABLE User (Id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,UserId LONG NOT NULL,
 UserName VARCHAR(30) NOT NULL,
 EmailAddress VARCHAR(30) NOT NULL);

CREATE UNIQUE INDEX idx_ue on User(UserId,EmailAddress);

INSERT INTO User (UserId,UserName, EmailAddress) VALUES (1000,'Srinivas','sritest@gmail.com');
INSERT INTO User (UserId,UserName, EmailAddress) VALUES (1001'Nag','nagtest@gmail.com');
INSERT INTO User (UserId,UserName, EmailAddress) VALUES (10002,'Venkat','venkattest@gmail.com');

DROP TABLE IF EXISTS Account;

CREATE TABLE Account (id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,AccountId LONG NOT NULL,
UserName VARCHAR(30),
Balance DECIMAL(19,4),
Staus VARCHAR(30),
CreatedDate DATE,
LastUpdatedDate DATE
);

CREATE UNIQUE INDEX idx_acc on Account(AccountId,UserName);

INSERT INTO Account (AccountId,UserName,Balance,Staus,CreatedDate) VALUES (100011,'Srinivas',10000.00,'ACTIVE','2020-01-08');
INSERT INTO Account (AccountId,UserName,Balance,Staus,CreatedDate) VALUES (100012,'Nag',30000.00,'ACTIVE','2020-01-08');
INSERT INTO Account (AccountId,UserName,Balance,Staus,CreatedDate) VALUES (100013,'Venkat',40000.00,'ACTIVE','2020-01-08');
INSERT INTO Account (AccountId,UserName,Balance,Staus,CreatedDate) VALUES (100014,'Rajesh',50000.00,'ACTIVE','2020-01-08');
INSERT INTO Account (AccountId,UserName,Balance,Staus,CreatedDate) VALUES (100015,'Sekhar',20000.00,'ACTIVE','2020-01-08');
