package com.breno.instagram.login.data

import android.os.Handler
import android.os.Looper
import com.breno.instagram.common.model.Database

class FakeDataSource : LoginDataSource {

    override fun login(email: String, password: String, callback: LoginCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull {
                email == it.email
            }

            userAuth?.let { user ->
                takeIf { password == user.password }?.run {
                    Database.sessionAuth = user
                    callback.onSuccess()
                } ?: callback.onFailure("Senha incorreta")
            } ?: callback.onFailure("Usuário não encontrado")


            callback.onComplete()
        }, 4000)
    }
}