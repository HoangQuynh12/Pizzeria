package com.example.pizzeria

data class OrderData(
    var OrderID: String? = "",
    val Address: String? = "",
    val Total: Double? = 0.0,
    var OrderDate: String? = "",
    val status: String = "Chưa xác nhận",
    val UserID: String? = "",
    val PhoneNumber: Int? = 0,
    val Name: String? = "",

//    var orderItem: OrderItem,
//    var product: ProductData
)
