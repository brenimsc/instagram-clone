package com.breno.instagram.add.data

import android.net.Uri
import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post
import com.breno.instagram.common.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FireAddDatasource : AddDatasource {
    override fun createPost(
        userUUID: String,
        uri: Uri,
        caption: String,
        callback: RequestCallback<Boolean>
    ) {
        val uriLastPath = uri.lastPathSegment ?: throw IllegalArgumentException("Invalid img")

        val imgRef = FirebaseStorage.getInstance().reference
            .child("images/")
            .child(userUUID)
            .child(uriLastPath)

        imgRef.putFile(uri)
            .addOnSuccessListener {
                imgRef.downloadUrl
                    .addOnSuccessListener { url ->
                        val ref = FirebaseFirestore.getInstance()
                            .collection("/users")
                            .document(userUUID)

                        ref.get()
                            .addOnSuccessListener {
                                it.toObject(User::class.java)?.let {
                                    val postRef = FirebaseFirestore.getInstance()
                                        .collection("/posts")
                                        .document(userUUID)
                                        .collection("posts")
                                        .document()

                                    val post = Post(
                                        postRef.id,
                                        url.toString(),
                                        caption,
                                        System.currentTimeMillis(),
                                        it
                                    )

                                    postRef.set(post)
                                        .addOnSuccessListener {

                                            ref.update("postCount", FieldValue.increment(1))

                                            FirebaseFirestore.getInstance()
                                                .collection("/feeds")
                                                .document(userUUID)
                                                .collection("posts")
                                                .document(postRef.id)
                                                .set(post)
                                                .addOnSuccessListener {
                                                    FirebaseFirestore.getInstance()
                                                        .collection("/followers")
                                                        .document(userUUID)
                                                        .get()
                                                        .addOnSuccessListener {
                                                            if (it.exists()) {
                                                                val list =
                                                                    it.get("followers") as List<String>
                                                                list.map {
                                                                    FirebaseFirestore.getInstance()
                                                                        .collection("/feeds")
                                                                        .document(it)
                                                                        .collection("posts")
                                                                        .document(postRef.id)
                                                                        .set(post)
                                                                }
                                                            }
                                                            callback.onSuccess(true)

                                                        }
                                                        .addOnFailureListener {
                                                            callback.onFailure(
                                                                it.message
                                                                    ?: "Falha ao buscar seguidores"
                                                            )
                                                        }
                                                        .addOnCompleteListener {
                                                            callback.onComplete()
                                                        }
                                                }
                                                .addOnFailureListener {
                                                    callback.onFailure(
                                                        it.message ?: "Falha ao inserir post"
                                                    )
                                                }
                                        }
                                        .addOnFailureListener {
                                            callback.onFailure(
                                                it.message ?: "Falha ao inserir post"
                                            )
                                        }
                                }
                            }
                            .addOnFailureListener {
                                callback.onFailure(it.message ?: "Falha ao buscar usuário a foto")
                            }
                    }
                    .addOnFailureListener {
                        callback.onFailure(it.message ?: "Falha ao baixar a foto")
                    }
            }
            .addOnFailureListener {
                callback.onFailure(it.message ?: "Falha ao subir a foto")
            }
    }
}