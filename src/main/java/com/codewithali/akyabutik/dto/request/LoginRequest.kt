package com.codewithali.akyabutik.dto.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class LoginRequest(

    @field:NotEmpty
    val email: String,

    @field:Size(min = 6, max = 24)
    @field:NotEmpty
    val hashedPassword: String
)
