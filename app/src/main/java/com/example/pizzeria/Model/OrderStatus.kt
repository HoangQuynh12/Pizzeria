
package com.example.pizzeria.Model


enum class OrderStatus(code: String)  {
    Processing("Processing"),
    Shipping("Shipping"),
    Shipped("Shipped"),
    Canceled("Canceled"),
    Delivered("Delivered"),

}