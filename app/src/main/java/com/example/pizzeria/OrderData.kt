package com.example.pizzeria

data class OrderData(
    var OrderID: String? = "",
    val Address: String? = "",
    val Total: Double? = 0.0,
    var OrderDate: String? = "",
    val Status: String = "Uncomfimred",
    val UserID: String? = "",
    val PhoneNumber: Int? = 0,
    val Name: String? = "",
//    val ProductID: String? = "",
//    val ProductID: Array<String> = arrayOf()


)
fun calculateTotal(orders: List<OrderData>): Double {
    var totalSum = 0.0
    for (order in orders) {
        totalSum += order.Total ?: 0.0
    }
    return totalSum
}