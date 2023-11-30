package com.breno.instagram.register.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.breno.instagram.common.model.Database
import com.breno.instagram.common.model.UserAuth
import com.breno.instagram.register.RegisterCallback
import java.util.UUID

class FakeRegisterDataSource : RegisterDataSource {

    override fun create(email: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { email == it.email }

            userAuth?.let {
                callback.onFailure("Usuário já cadastrado")
            } ?: callback.onSuccess()

            callback.onComplete()
        }, 2000)
    }

    override fun create(
        email: String,
        name: String,
        password: String,
        callback: RegisterCallback
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { email == it.email }

            userAuth?.let {
                callback.onFailure("Usuário já cadastrado")
            } ?: UserAuth(
                UUID.randomUUID().toString(),
                name,
                email,
                password
            ).run {
                if (Database.usersAuth.add(this)) {
                    Database.sessionAuth = this

                    Database.followers[this.uuid] = hashSetOf()
                    Database.posts[this.uuid] = hashSetOf()
                    Database.feeds[this.uuid] = hashSetOf()

                    callback.onSuccess()
                } else callback.onFailure("Erro interno no servidor")
            }

            callback.onComplete()
        }, 2000)
    }

    override fun updateUser(photoUri: Uri, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.sessionAuth

            userAuth?.let {
                Database.usersAuth.indexOf(Database.sessionAuth).run {
                    Database.usersAuth[this] = Database.sessionAuth!!.copy(photoUri = photoUri)
                    Database.sessionAuth = Database.usersAuth[this]

                    callback.onSuccess()
                }
            } ?: callback.onFailure("Usuário não encontrado")

            callback.onComplete()
        }, 2000)
    }
}