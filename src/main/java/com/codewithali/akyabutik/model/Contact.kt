package com.codewithali.akyabutik.model

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "contacts")
data class Contact @JvmOverloads constructor(
    val id: String? = null,
    val nameSurname: String,
    val mobilePhoneNumber: String,
    val email: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
