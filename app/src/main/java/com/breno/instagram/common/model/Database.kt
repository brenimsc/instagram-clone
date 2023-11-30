package com.breno.instagram.common.model

import android.net.Uri
import java.io.File
import java.util.UUID

// TODO: Apenas teste
object Database {

    val usersAuth = mutableListOf<UserAuth>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, MutableSet<String>>()

    var sessionAuth: UserAuth? = null

    init {
        val userA = UserAuth(
            UUID.randomUUID().toString(),
            "UserA",
            "userA@gmail.com",
            "12345678",
            Uri.fromFile(File("/storage/emulated/0/Android/media/com.breno.instagram/Instagram/2023-08-10-18-08-32-644.jpg"))
        )
        usersAuth.add(userA)
    }
}