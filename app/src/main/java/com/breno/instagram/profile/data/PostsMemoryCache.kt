package com.breno.instagram.profile.data

import com.breno.instagram.common.base.Cache
import com.breno.instagram.common.model.Post

object PostsMemoryCache : Cache<List<Post>> {

    private var posts: List<Post>? = null

    override fun isCached(): Boolean {
        return posts != null
    }

    override fun get(key: String): List<Post>? {
        return posts
    }

    override fun put(data: List<Post>?) {
        posts = data
    }
}