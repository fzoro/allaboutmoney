# All About Money - [![CircleCI](https://circleci.com/gh/zoret4/allaboutmoney.svg?style=shield)](https://circleci.com/gh/zoret4/allaboutmoney)
Is a micro service to handle payments and orders. It is intended to keep your business logic apart from payment/order logic. 

## Tech stack

It is fully implemented in *Kotlin 1.3.x*. You can either run on your **JVM** or as a **Docker container**

##### Modules
- core - *in progress*
- wirecard - *in progress*
##### Payment methods

- Checkout Wirecard - *in progress*

## Roadmap
- wirecard (not Checkout wirecard)
- Braintree integration
- message driven

# Documentation

#### Resources 
 - /**customers**
	 - **POST**: creates a customer
		 - body:  ``` { 
		 "ownId": "",
		 "fullname": "",
		 "email": "",
		 "birthDate": "1980-5-10"
		 "taxDocument": {
			"type": "CPF",
			"number": "10013390023" 
		 }
	  }```
		 - response:  *Status*: 201 ```{}```

 - /**orders**
	 - **POST**: creates an order
		 - body:  ```{"customer": {"id": "someCustomerId"}, "transactions" : [{"id": "someProductId", "price": 44.5 "links": [] }], "amount": {"total": 44.5, "currency": "BRL"}}```
		 - response:  *Status*: 201 ```{"id": "orderId", "links" : [{"self": "host/order/orderId"}]}```
	 - **GET**: retrieve order(by id or by query params )
