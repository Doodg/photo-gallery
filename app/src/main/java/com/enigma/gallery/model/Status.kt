package com.enigma.gallery.model

sealed class Status<out T> {
    data class Success<out T>(val data: T) : Status<T>()

    data class Error(var e: Throwable) : Status<Nothing>()

    object Loading : Status<Nothing>()

}