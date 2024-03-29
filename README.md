# Money Transfer [![Build Status](https://travis-ci.org/TheMescaline/vtb-medical-guide.svg?branch=master)](https://travis-ci.org/TheMescaline/vtb-medical-guide) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/49a02c01596f402a908cc1701674ddd8)](https://www.codacy.com/gh/TheMescaline/MoneyTransfer/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=TheMescaline/MoneyTransfer&amp;utm_campaign=Badge_Grade)

Simple RESTful API application for money transfer operations and accounts management.

You can start application with default DB implementation (H2), or you can pass a parameter (path to a props.yml file) with connection configuration for your DB (it supports PostgreSQL, MySQL).

Props file must contain this fields:

* url: 
* driver: 
* user: 
* password: 
* poolSize: 
###### **Assemble and start**
* Jar assemble - mvn clean package
* Start application - java -jar moneytransfer.jar _path_to_props.yml_
###### **CURL commands for accounts management (should be open for admin only)**
*  saveNew: `curl -X POST http://localhost:8080/api/v1/closed/accounts -H 'Content-Type: application/json' -d '{"balance":30000.0}'`
*  getAll: `curl -X GET http://localhost:8080/api/v1/closed/accounts`
*  getOne: `curl -X GET http://localhost:8080/api/v1/closed/accounts/2`
*  update: `curl -X PUT http://localhost:8080/api/v1/closed/accounts/2 -H 'Content-Type: application/json' -d '{"id":2,"balance":5550000.0}'`
*  delete: `curl -X DELETE http://localhost:8080/api/v1/closed/accounts/1`
###### **CURL command for transfer operations (transfer, withdraw, deposit)**
Transfer operation consumes entity TransferDataPacket with id of supplier-account, id or receiver-account and transferring amount
* transfer: `curl -X PUT http://localhost:8080/api/v1/open/transfer -H 'Content-Type: application/json' -d '{"fromAccountId":2,"toAccountId":1,"amount":10000.0}'`
* withdraw: `curl -X PUT http://localhost:8080/api/v1/open/withdraw -H 'Content-Type: application/json' -d '{"accountId":1,"amount":10000.0}'`
* deposit: `curl -X PUT http://localhost:8080/api/v1/open/deposit -H 'Content-Type: application/json' -d '{"accountId":2,"amount":10000.0}'`

