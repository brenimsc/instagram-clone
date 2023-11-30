package com.breno.instagram.profile.data

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post
import com.breno.instagram.common.model.User

interface ProfileDatasource {
    fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User, Boolean?>>)

    fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>)

    fun followUser(userUUID: String, isFollow: Boolean, callback: RequestCallback<Boolean>) {
        throw UnsupportedOperationException()
    }

    fun fetchSession(): String {
        throw UnsupportedOperationException()
    }

    fun putUser(response: Pair<User, Boolean?>?) {
        throw UnsupportedOperationException()
    }

    fun putPosts(response: List<Post>?) {
        throw UnsupportedOperationException()
    }
}