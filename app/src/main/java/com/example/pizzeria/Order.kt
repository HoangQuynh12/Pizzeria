package com.example.pizzeria
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    var OrderID: String? = "",
    val AddressID: String? = "",
    val Total: Double? = 0.0,
    var OrderDate: Long = 0,
    val Status: String? = "",
    val UserID: String? = "",
    var items: List<OrderItem>
) {
    constructor() : this(
        OrderID = "", AddressID = "", OrderDate = 0, Total = 0.0, UserID = "", Status = "", items = emptyList()
    )
}