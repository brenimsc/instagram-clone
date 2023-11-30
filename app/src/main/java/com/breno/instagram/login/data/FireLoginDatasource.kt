package com.breno.instagram.login.data

import com.google.firebase.auth.FirebaseAuth

class FireLoginDatasource : LoginDataSource {
    override fun login(email: String, password: String, callback: LoginCallback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.let {
                    callback.onSuccess()
                } ?: callback.onFailure("Usuário não encontrado")
            }
            .addOnFailureListener {
                callback.onFailure(it.message ?: "Erro ao fazer login")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }
}