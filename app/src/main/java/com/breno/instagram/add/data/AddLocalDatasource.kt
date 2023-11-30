package com.breno.instagram.add.data

import com.google.firebase.auth.FirebaseAuth

class AddLocalDatasource : AddDatasource {
    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuário não logado")
    }
}