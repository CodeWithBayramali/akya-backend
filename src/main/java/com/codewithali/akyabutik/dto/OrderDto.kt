package com.codewithali.akyabutik.dto

import com.codewithali.akyabutik.model.Address
import com.codewithali.akyabutik.model.Order
import com.codewithali.akyabutik.model.OrderProduct
import com.codewithali.akyabutik.model.OrderStatus
import java.time.LocalDateTime

data class OrderDto(
    val orderProducts: List<OrderProduct>,
    val totalPrice: Double,
    val nameSurname: String,
    val address: Address,
    val phoneNumber: String,
    val email: String,

    val status: OrderStatus?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val id: String?,
) {
    companion object {
        @JvmStatic
        fun convertToDto(from: Order): OrderDto {
            return OrderDto(
                from.orderProducts,
                from.totalPrice,
                from.nameSurname,
                from.address,
                from.phoneNumber,
                from.email,
                from.status,
                from.createdAt,
                from.updatedAt,
                from.id,
            )
        }

        @JvmStatic
        fun convertToEntity(from: OrderDto): Order {
            return Order(
                from.orderProducts,
                from.totalPrice,
                from.nameSurname,
                from.address,
                from.phoneNumber,
                from.email
            )
        }
    }
}
