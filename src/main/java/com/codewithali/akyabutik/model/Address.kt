package com.codewithali.akyabutik.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Address(
    @JsonProperty("city")
    val city: String,
    @JsonProperty("state")
    val state: String,
    @JsonProperty("apartment")
    val apartment: String,
    @JsonProperty("zipCode")
    val zipCode: String,
    @JsonProperty("addressDetails")
    val addressDetails: String
)
