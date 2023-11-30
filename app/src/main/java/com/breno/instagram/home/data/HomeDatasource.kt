package com.breno.instagram.home.data

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post

interface HomeDatasource {
    fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>)

    fun fetchSession(): String {
        throw UnsupportedOperationException()
    }

    fun putFeed(response: List<Post>?) {
        throw UnsupportedOperationException()
    }

    fun logout() {
        throw UnsupportedOperationException()
    }
}