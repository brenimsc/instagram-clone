package com.breno.instagram.profile.data

import com.breno.instagram.common.base.Cache
import com.breno.instagram.common.model.Post
import com.breno.instagram.common.model.User

class ProfileDatasourceFactory(
    private val profileCache: Cache<Pair<User, Boolean?>>,
    private val postsCache: Cache<List<Post>>
) {
    fun createLocalDatasource(): ProfileDatasource {
        return ProfileLocalDatasource(profileCache, postsCache)
    }

    fun createFromUser(uuid: String?): ProfileDatasource {
        if (uuid != null) return createRemoteDatasource()
        return if (profileCache.isCached()) createLocalDatasource() else createRemoteDatasource()
    }

    fun createFromPosts(uuid: String?): ProfileDatasource {
        if (uuid != null) return createRemoteDatasource()
        return if (postsCache.isCached()) createLocalDatasource() else createRemoteDatasource()
    }

    fun createRemoteDatasource(): ProfileDatasource {
        return FireProfileDatasource()
    }

}