package com.breno.instagram.search.data

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FireSearchDatasource : SearchDatasource {
    override fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereGreaterThanOrEqualTo("name", name)
            .whereLessThanOrEqualTo("name", name + "\uf8ff")
            .get()
            .addOnSuccessListener {
                val users = mutableListOf<User>()
                val documents = it.documents

                for (document in documents) {
                    document.toObject(User::class.java)?.let { user ->
                        takeIf { user.uuid != FirebaseAuth.getInstance().uid }?.run {
                            users.add(user)
                        }
                    }
                }
                callback.onSuccess(users)
            }
            .addOnFailureListener {
                callback.onFailure(it.message ?: "Falha ao buscar")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }
}