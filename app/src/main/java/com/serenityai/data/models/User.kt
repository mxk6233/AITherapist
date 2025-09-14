package com.serenityai.data.models

import java.util.Date

data class User(
    val userId: String = "",
    val email: String = "",
    val username: String = "",
    val dateCreated: Date = Date(),
    val lastActiveAt: Date = Date(),
    val consentGiven: Boolean = false
)
