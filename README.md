# Money Transfer [![Build Status](https://travis-ci.org/TheMescaline/vtb-medical-guide.svg?branch=master)](https://travis-ci.org/TheMescaline/vtb-medical-guide) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/c48f8cde7eaa466a81351251084080e4)](https://www.codacy.com/manual/TheMescaline/MoneyTransfer?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=TheMescaline/MoneyTransfer&amp;utm_campaign=Badge_Grade)

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
###### **CURL commands for accounts management**
*  saveNew: `curl -X POST http://localhost:8080/api/v1/accounts -H 'Content-Type: application/json' -d '{"balance":30000.0}'`
*  getAll: `curl -X GET http://localhost:8080/api/v1/accounts`
*  getOne: `curl -X GET http://localhost:8080/api/v1/accounts/2`
*  update: `curl -X PUT http://localhost:8080/api/v1/accounts/2 -H 'Content-Type: application/json' -d '{"id":2,"balance":5550000.0}'`
*  delete: `curl -X DELETE http://localhost:8080/api/v1/accounts/1`
###### **CURL command for transfer operation**
Transfer operation consumes entity TransferDataPacket with id of supplier-account, id or receiver-account and transferring amount
* `curl -X PUT http://localhost:8080/api/v1/transfer -H 'Content-Type: application/json' -d '{"fromAccountId":2,"toAccountId":1,"amount":10000.0}'`

