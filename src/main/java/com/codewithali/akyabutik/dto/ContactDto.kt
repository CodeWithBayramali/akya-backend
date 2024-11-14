package com.codewithali.akyabutik.dto

import com.codewithali.akyabutik.model.Contact

data class ContactDto(
    val id: String?,
    val nameSurname: String,
    val mobilePhoneNumber: String,
    val email: String
) {
    companion object {
        @JvmStatic
        fun convertToDto(contact: Contact): ContactDto {
            return ContactDto(
                contact.id,
                contact.nameSurname,
                contact.mobilePhoneNumber,
                contact.email,
            )
        }
        @JvmStatic
        fun convertToEntity(dto: ContactDto): Contact {
            return Contact(
                dto.id,
                dto.nameSurname,
                dto.mobilePhoneNumber,
                dto.email,
            )
        }
    }
}
