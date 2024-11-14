package com.codewithali.akyabutik.model

import com.codewithali.akyabutik.dto.ProductDto
import com.fasterxml.jackson.annotation.JsonProperty

data class OrderProduct(
    @JsonProperty("productId")
    val productId: String,
    @JsonProperty("productName")
    val productName: String,
    @JsonProperty("imageUrl")
    val imageUrl: String,
    @JsonProperty("color")
    val color: String,
    @JsonProperty("size")
    val size: String,
    @JsonProperty("quantity")
    val quantity: Int
)
