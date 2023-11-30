package com.breno.instagram.register.data

import android.net.Uri
import android.util.Log
import com.breno.instagram.common.model.User
import com.breno.instagram.register.RegisterCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FireRegisterDatasource : RegisterDataSource {

    companion object {
        private const val USERS = "users"
        private const val EMAIL = "email"
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun create(email: String, callback: RegisterCallback) {
        FirebaseFirestore.getInstance()
            .collection("/$USERS")
            .whereEqualTo(EMAIL, email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) callback.onSuccess()
                else callback.onFailure("Usuário já cadastrado")
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro no servidor")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                result.user?.uid?.let { uid ->
                    firestore.collection("/users")
                        .document(uid)
                        .set(
                            hashMapOf(
                                "name" to name,
                                "email" to email,
                                "followers" to 0,
                                "following" to 0,
                                "postCount" to 0,
                                "uuid" to uid,
                                "photoUrl" to null
                            )
                        )
                        .addOnSuccessListener {
                            callback.onSuccess()
                        }
                        .addOnFailureListener {
                            callback.onFailure(it.message ?: "Erro interno no servidor")
                        }
                        .addOnCompleteListener {
                            callback.onSuccess()
                        }
                } ?: callback.onFailure("Erro interno no servidor")
            }
            .addOnFailureListener {
                callback.onFailure(it.message ?: "Erro interno no servidor")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun updateUser(photoUri: Uri, callback: RegisterCallback) {
        photoUri.lastPathSegment?.let { lastPathSegment ->
            FirebaseAuth.getInstance().uid?.let { uid ->

                val storageRef = FirebaseStorage.getInstance().reference

                val imgRef = storageRef
                    .child("images/")
                    .child(uid)
                    .child(lastPathSegment)

                imgRef
                    .putFile(photoUri)
                    .addOnSuccessListener { result ->

                        imgRef.downloadUrl
                            .addOnSuccessListener { url ->
                                val userRef = FirebaseFirestore.getInstance().collection("/$USERS")
                                    .document(uid)

                                userRef.get()
                                    .addOnSuccessListener { document ->
                                        val user = document.toObject(User::class.java)
                                        val userUpdated = user?.copy(photoUrl = url.toString())

                                        userUpdated?.let {
                                            userRef.set(userUpdated)
                                                .addOnSuccessListener {
                                                    callback.onSuccess()
                                                }
                                                .addOnFailureListener {
                                                    Log.e("BRENOL", it.message.toString())
                                                    callback.onFailure(
                                                        it.message ?: "Falha ao atualizar a foto"
                                                    )
                                                }
                                                .addOnCompleteListener {
                                                    callback.onComplete()
                                                }
                                        }

                                    }
                            }

                    }
                    .addOnFailureListener {
                        callback.onFailure(it.message ?: "Houve uma falha ao subir a foto")
                    }

            } ?: callback.onFailure("Usuário não encontrado")
        } ?: callback.onFailure("Houve um problema ao tentar atualizar a foto")

    }
}