# temporary

{
	"info": {
		"_postman_id": "a7b5fccb-92a1-4150-ac7c-9a451fac0f8b",
		"name": "MoneyTransfer",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
	},
	"item": [
		{
			"name": "getAll",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/moneytransfer/api/v1/accounts",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"moneytransfer",
						"api",
						"v1",
						"accounts"
					]
				}
			},
			"response": []
		},
		{
			"name": "getOne",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/moneytransfer/api/v1/accounts/2",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"moneytransfer",
						"api",
						"v1",
						"accounts",
						"2"
					]
				}
			},
			"response": []
		},
		{
			"name": "getOneNotFound",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/moneytransfer/api/v1/accounts/999",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"moneytransfer",
						"api",
						"v1",
						"accounts",
						"999"
					]
				}
			},
			"response": []
		},
		{
			"name": "saveOk",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"name": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{ \"balance\":20000.0}"
				},
				"url": {
					"raw": "http://localhost:8080/moneytransfer/api/v1/accounts",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"moneytransfer",
						"api",
						"v1",
						"accounts"
					]
				}
			},
			"response": []
		},
		{
			"name": "saveBadRequest",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{ \"balance\":-20000.0}"
				},
				"url": {
					"raw": "http://localhost:8080/moneytransfer/api/v1/accounts",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"moneytransfer",
						"api",
						"v1",
						"accounts"
					],
					"query": [
						{
							"key": "Content-Type",
							"value": "appli",
							"disabled": true
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "deleteOk",
			"request": {
				"method": "DELETE",
				"header": [
					{
						"key": "",
						"value": "",
						"type": "text",
						"disabled": true
					}
				],
				"url": {
					"raw": "http://localhost:8080/moneytransfer/api/v1/accounts/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"moneytransfer",
						"api",
						"v1",
						"accounts",
						"1"
					]
				}
			},
			"response": []
		},
		{
			"name": "deleteNotFound",
			"request": {
				"method": "DELETE",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/moneytransfer/api/v1/accounts/999",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"moneytransfer",
						"api",
						"v1",
						"accounts",
						"999"
					]
				}
			},
			"response": []
		},
		{
			"name": "updateOk",
			"request": {
				"method": "PUT",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{ \"id\":2,\"balance\":5550000.0}"
				},
				"url": {
					"raw": "http://localhost:8080/moneytransfer/api/v1/accounts/2",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"moneytransfer",
						"api",
						"v1",
						"accounts",
						"2"
					]
				}
			},
			"response": []
		},
		{
			"name": "updateBadRequest",
			"request": {
				"method": "PUT",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{ \"id\":3,\"balance\":5550000.0}"
				},
				"url": {
					"raw": "http://localhost:8080/moneytransfer/api/v1/accounts/999",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"moneytransfer",
						"api",
						"v1",
						"accounts",
						"999"
					]
				}
			},
			"response": []
		},
		{
			"name": "doTransferOk",
			"request": {
				"method": "PUT",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{ \"fromAccount\":3,\"toAccount\":2,\"amount\":1000.0}"
				},
				"url": {
					"raw": "http://localhost:8080/moneytransfer/api/v1/transfer",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"moneytransfer",
						"api",
						"v1",
						"transfer"
					]
				}
			},
			"response": []
		}
	],
	"protocolProfileBehavior": {}
}