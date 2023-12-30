package com.sametb.cinequiltapp._custom

import org.springframework.security.crypto.bcrypt.BCrypt
import kotlin.random.Random


// for update password
fun generateRandomPassword(length: Int): String {
    val charPool = ('A'..'Z') + ('a'..'z') + ('0'..'9') + "!@#$%^&*()_+[]{}|;:,.<>?".toList()
    return (1..length)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}


fun hashPassword(password: String): String {
    val saltRounds = 12 // You can adjust this value based on your security requirements
    val salt = BCrypt.gensalt(saltRounds)
    return BCrypt.hashpw(password, salt)
}
