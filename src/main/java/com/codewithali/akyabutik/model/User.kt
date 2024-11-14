package com.codewithali.akyabutik.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "users")
data class User(

    @Id
    val id: String? = null,
    val email: String,
    val hashedPassword: String,
    val name: String,
    val roles: Set<Role>,

): UserDetails {
    override fun getAuthorities()= this.roles

    override fun getPassword() = this.hashedPassword

    override fun getUsername() = this.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
