package com.breno.instagram.login.data

interface LoginCallback {
    fun onSuccess()
    fun onFailure(message: String)
    fun onComplete()
}
