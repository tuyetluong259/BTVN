package com.example.btth.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String = "",
    val name: String = "",
    val sku: String? = null,
    val price: Double = 0.0,

    @SerialName("des")
    val description: String = "",

    @SerialName("imgURL")
    val imageUrl: String = ""
)

@Serializable
data class ApiResponse(
    val status: String = "",
    val data: Product
)
