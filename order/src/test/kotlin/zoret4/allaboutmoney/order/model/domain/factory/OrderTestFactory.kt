package zoret4.allaboutmoney.order.model.domain.factory


sealed class OrderTestFactory {

    companion object {
        val jsonBody = """{
                            "customerId": "test-001",
                            "products": [{
                                    "id": "product-1",
                                    "price": 10,
                                    "quantity": 2,
                                    "description": "montlhy"
                                },
                                {
                                    "id": "product-2",
                                    "price": 150,
                                    "quantity": 1,
                                    "description": "forever"
                                }
                            ],
                            "payment": {
                                "shipping": 10,
                                "addition": 5,
                                "discount": 4,
                                "currency": "BRL",
                                "method": "VENDOR_CHECKOUT",
                                "processor": "WIRECARD"
                            }
                        }""".trimIndent()

    }
}
