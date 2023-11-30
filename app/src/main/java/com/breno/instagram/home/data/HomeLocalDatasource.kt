package com.breno.instagram.home.data

import com.breno.instagram.common.base.Cache
import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post
import com.google.firebase.auth.FirebaseAuth

class HomeLocalDatasource(
    private val feedCache: Cache<List<Post>>,
) : HomeDatasource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        feedCache.get(userUUID)?.let {
            callback.onSuccess(it)
        } ?: callback.onFailure("Posts não encontrado")

        callback.onComplete()
    }

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("usuario não logado")
    }

    override fun putFeed(response: List<Post>?) {
        feedCache.put(response)
    }
}