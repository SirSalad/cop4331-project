package edu.ucf.cop4331project.storage

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val password: String,
    var coins: Int = 0,
    var premium: Boolean = false
)
