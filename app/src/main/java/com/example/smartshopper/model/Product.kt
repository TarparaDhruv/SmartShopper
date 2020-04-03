package com.example.smartshopper.model

data class Product(
    var storeName: String = "",
    var storeLogo: String = "",
    var item: Map<String, Any>? = HashMap()
)