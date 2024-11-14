package com.codewithali.akyabutik.dto.request

import com.codewithali.akyabutik.model.Address
import com.codewithali.akyabutik.model.Order
import com.codewithali.akyabutik.model.OrderProduct
import com.fasterxml.jackson.annotation.JsonProperty

data class CreateOrderRequest(
    @JsonProperty("orderProducts")
    val orderProducts: List<OrderProduct>,
    @JsonProperty("totalPrice")
    val totalPrice: Double,
    @JsonProperty("nameSurname")
    val nameSurname: String,
    @JsonProperty("address")
    val address: Address,
    @JsonProperty("phoneNumber")
    val phoneNumber: String,
    @JsonProperty("email")
    val email: String,
) {
    companion object {
        @JvmStatic
        fun from(from: CreateOrderRequest): Order {
            return Order(
                from.orderProducts,
                from.totalPrice,
                from.nameSurname,
                from.address,
                from.phoneNumber,
                from.email,
            )
        }
    }
}
