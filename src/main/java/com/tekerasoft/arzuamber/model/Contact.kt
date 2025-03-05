package com.tekerasoft.arzuamber.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "contacts")
data class Contact(

    val nameSurname: String,
    val email: String,
    @Column(columnDefinition = "TEXT")
    val message: String,

    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contact_id", columnDefinition = "uuid")
    @JsonProperty("id")
    val id: UUID? = null,
)
