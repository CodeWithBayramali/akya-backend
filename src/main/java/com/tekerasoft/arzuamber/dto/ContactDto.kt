package com.tekerasoft.arzuamber.dto

import com.tekerasoft.arzuamber.model.Contact
import java.time.LocalDateTime
import java.util.UUID

data class ContactDto(
    val id: UUID?,
    val nameSurname: String,
    val email: String,
    val message: String,
    val createdAt: LocalDateTime?
) {
    companion object {
        @JvmStatic
        fun toDto(from: Contact): ContactDto {
            return ContactDto(
                from.id,
                from.nameSurname,
                from.email,
                from.message,
                from.createdAt
            )
        }
    }
}
