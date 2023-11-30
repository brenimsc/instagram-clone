package com.breno.instagram.home.data

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FireHomeDatasource : HomeDatasource {
    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        FirebaseAuth.getInstance().uid?.let {
            FirebaseFirestore.getInstance()
                .collection("/feeds")
                .document(it)
                .collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    val feed = mutableListOf<Post>()
                    val documents = it.documents
                    for (document in documents) {
                        val post = document.toObject(Post::class.java)
                        post?.let { feed.add(it) }
                    }

                    callback.onSuccess(feed)
                }
                .addOnFailureListener {
                    callback.onFailure(it.message ?: "Erro ao carregar o feed")
                }
                .addOnCompleteListener {
                    callback.onComplete()
                }
        }
    }

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}