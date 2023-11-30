package com.breno.instagram.profile.data

import android.os.Handler
import android.os.Looper
import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Database
import com.breno.instagram.common.model.Post
import com.breno.instagram.common.model.User

// TODO: Apenas testes
class ProfileFakeRemoteDatasource : ProfileDatasource {

    override fun fetchUserProfile(
        userUUID: String,
        callback: RequestCallback<Pair<User, Boolean?>>
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { userUUID == it.uuid }

            callback.onComplete()
        }, 2000)
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val posts = Database.posts[userUUID]

            callback.onSuccess(posts?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }

    override fun followUser(
        userUUID: String,
        isFollow: Boolean,
        callback: RequestCallback<Boolean>
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            var followers = Database.followers[Database.sessionAuth!!.uuid]

            if (followers == null) {
                followers = mutableSetOf()
                Database.followers[Database.sessionAuth!!.uuid] = followers
            }

            if (isFollow) {
                Database.followers[Database.sessionAuth!!.uuid]!!.add(userUUID)
            } else {
                Database.followers[Database.sessionAuth!!.uuid]!!.remove(userUUID)
            }

            callback.onSuccess(true)
            callback.onComplete()
        }, 500)
    }
}