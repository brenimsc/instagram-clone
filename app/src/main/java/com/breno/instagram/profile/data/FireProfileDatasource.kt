package com.breno.instagram.profile.data

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post
import com.breno.instagram.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query

class FireProfileDatasource : ProfileDatasource {

    override fun fetchUserProfile(
        userUUID: String,
        callback: RequestCallback<Pair<User, Boolean?>>
    ) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .document(userUUID)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)

                user?.let {
                    if (it.uuid == FirebaseAuth.getInstance().uid) {
                        callback.onSuccess(Pair(user, null))
                    } else {
                        FirebaseFirestore.getInstance()
                            .collection("/followers")
                            .document(userUUID)
                            .get()
                            .addOnSuccessListener {
                                if (!it.exists()) callback.onSuccess(Pair(user, false))
                                else {
                                    (it.get("followers") as? List<String>)?.run {
                                        callback.onSuccess(
                                            Pair(
                                                user,
                                                this.contains(FirebaseAuth.getInstance().uid)
                                            )
                                        )
                                    }
                                }
                            }
                            .addOnFailureListener {
                                callback.onFailure(it.message ?: "Falha ao buscar seguidores")
                            }
                            .addOnCompleteListener {
                                callback.onComplete()
                            }
                    }

                } ?: callback.onFailure("Falha ao converter usuário")

            }
            .addOnFailureListener {
                callback.onFailure(it.message ?: "Falha ao buscar usuário")
            }
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        FirebaseFirestore.getInstance()
            .collection("posts")
            .document(userUUID)
            .collection("posts")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val documents = it.documents
                val posts = mutableListOf<Post>()
                for (document in documents) {
                    val post = document.toObject(Post::class.java)
                    post?.let {
                        posts.add(it)
                    }
                }
                callback.onSuccess(posts)
            }
            .addOnFailureListener {
                callback.onFailure(it.message ?: "Falha ao buscar posts")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun followUser(
        userUUID: String,
        isFollow: Boolean,
        callback: RequestCallback<Boolean>
    ) {
        FirebaseAuth.getInstance().uid?.let { uid ->
            FirebaseFirestore.getInstance()
                .collection("/followers")
                .document(userUUID)
                .update(
                    "followers",
                    takeIf { isFollow }?.run { FieldValue.arrayUnion(uid) }
                        ?: FieldValue.arrayRemove(uid))
                .addOnSuccessListener {
                    followingCounter(uid, isFollow)
                    followersCounter(userUUID, callback)
                    updateFeed(userUUID, isFollow)
                }
                .addOnFailureListener {
                    (it as? FirebaseFirestoreException)?.run {
                        takeIf { this.code == FirebaseFirestoreException.Code.NOT_FOUND }?.run {
                            FirebaseFirestore.getInstance()
                                .collection("/followers")
                                .document(userUUID)
                                .set(
                                    hashMapOf("followers" to listOf(uid))
                                )
                                .addOnSuccessListener {
                                    followingCounter(uid, isFollow)
                                    followersCounter(userUUID, callback)
                                    updateFeed(userUUID, isFollow)

                                }
                                .addOnFailureListener {
                                    callback.onFailure(it.message ?: "Falha ao criar seguidor")
                                }
                        }
                    }

                    callback.onFailure(it.message ?: "Falha ao atualizar seguidor")
                }
                .addOnCompleteListener {
                    callback.onComplete()
                }
        } ?: throw RuntimeException("Usuário não logado")
    }

    private fun updateFeed(uid: String, isFollow: Boolean) {
        takeUnless { isFollow }?.run {
            FirebaseFirestore.getInstance()
                .collection("/feeds")
                .document(FirebaseAuth.getInstance().uid!!)
                .collection("posts")
                .whereEqualTo("publisher.uuid", uid)
                .get()
                .addOnSuccessListener { res ->
                    val documents = res.documents
                    documents.forEach {
                        it.reference.delete()
                    }
                }
        } ?: FirebaseFirestore.getInstance().collection("/posts")
            .document(uid)
            .collection("posts")
            .get()
            .addOnSuccessListener { res ->
                val posts = res.toObjects(Post::class.java)

                posts.lastOrNull()?.let {
                    FirebaseFirestore.getInstance()
                        .collection("/feeds")
                        .document(FirebaseAuth.getInstance().uid!!)
                        .collection("posts")
                        .document(it.uuid!!)
                        .set(it)
                }
            }
    }

    private fun followingCounter(uid: String, isFollow: Boolean) {
        val meRef = FirebaseFirestore.getInstance().collection("/users").document(uid)

        if (isFollow) meRef.update("following", FieldValue.increment(1))
        else meRef.update("following", FieldValue.increment(-1))
    }

    private fun followersCounter(uid: String, callback: RequestCallback<Boolean>) {
        val meRef = FirebaseFirestore.getInstance().collection("/users").document(uid)

        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(uid)
            .get()
            .addOnSuccessListener { response ->
                takeIf { response.exists() }?.run {
                    val list = response.get("followers") as List<String>
                    meRef.update("followers", list.size)
                }
                callback.onSuccess(true)
            }
    }
}