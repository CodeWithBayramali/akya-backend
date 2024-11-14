package com.codewithali.akyabutik.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Order(
    val orderProducts: List<OrderProduct>,
    val totalPrice: Double,
    val nameSurname: String,
    val address: Address,
    val phoneNumber: String,
    val email: String,

    val status: OrderStatus? = OrderStatus.NEW,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
    @Id
    val id: String? = null,
)
