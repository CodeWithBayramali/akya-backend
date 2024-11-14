package com.codewithali.akyabutik.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Product @JvmOverloads constructor(

    val name: String,
    val price: Double,
    var stock: Int,
    val sex: String,
    val description: String,
    var colorSize: List<ColorSize>,
    val mainCategory: String,
    val subCategory: String,

    val images: List<String>? = null,
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Id
    val id: String? = null,
)
