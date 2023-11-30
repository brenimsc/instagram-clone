package com.breno.instagram.profile.data

import com.breno.instagram.common.base.Cache
import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post
import com.breno.instagram.common.model.User
import com.google.firebase.auth.FirebaseAuth

class ProfileLocalDatasource(
    private val profileCache: Cache<Pair<User, Boolean?>>,
    private val postsCache: Cache<List<Post>>,
) : ProfileDatasource {

    override fun fetchUserProfile(
        userUUID: String,
        callback: RequestCallback<Pair<User, Boolean?>>
    ) {
        profileCache.get(userUUID)?.let {
            callback.onSuccess(it)
        } ?: callback.onFailure("Usuário não encontrado")

        callback.onComplete()
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        postsCache.get(userUUID)?.let {
            callback.onSuccess(it)
        } ?: callback.onFailure("Posts não encontrado")

        callback.onComplete()
    }

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("usuario nÃo logado")
    }

    override fun putUser(response: Pair<User, Boolean?>?) {
        profileCache.put(response)
    }

    override fun putPosts(response: List<Post>?) {
        postsCache.put(response)
    }
}