package com.example.pizzeria

data class OrderData(
    var OrderID: String? = "",
    val Address: String? = "",
    val Total: Double? = 0.0,
    var OrderDate: String? = "",
    val status: String = "Uncomfimred",
    val UserID: String? = "",
    val PhoneNumber: Int? = 0,
    val Name: String? = "",
//    val ProductID: String? = "",
//    val ProductID: Array<String> = arrayOf()


)
