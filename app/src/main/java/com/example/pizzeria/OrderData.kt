package com.example.pizzeria

data class OrderData(
    var OrderID: String? = "",
    val AddressID: String? = "",
    val Total: Double? = 0.0,
    var OrderDate: Long = 0,
    val Status: String? = "",
    val UserID: String? = ""
//    var orderItem: OrderItem,
//    var product: ProductData
)
