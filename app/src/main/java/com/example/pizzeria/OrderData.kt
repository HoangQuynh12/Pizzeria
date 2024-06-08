package com.example.pizzeria

import java.util.Date


data class OrderData(
    var OrderID: String? = "",
    val Address: String? = "",
    val Total: Double? = 0.0,
    var OrderDate: Date? = null,
    val Status: String = "",
    val UserID: String? = "",
    val PhoneNumber: String? = "",
    val Name: String? = "",
//    var items: List<OrderItem>? = null
) {
//    constructor() : this("", "", 0.0, null, "Unconfirmed", "", "", "", null)
}

//data class OrderItem(
//    val id: String = "",
//    val name: String = "",
//    val description: String = "",
//    val image: String = "",
//    val price: Double = 0.0,
//    val quantity: Int = 0,
//    val note: String = ""
//)
