package com.example.pizzeria
import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val productID: String = "", val quantity: Int = 1, val productPrice: Double
) {
    constructor() : this(productID = "", quantity = 1, productPrice = 0.0)
}
