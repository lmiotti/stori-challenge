package com.stori.challenge.extension

import android.util.Patterns
import java.util.regex.Pattern

fun String.isValidEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean {
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    return Pattern.compile(PASSWORD_PATTERN).matcher(this).matches()
}