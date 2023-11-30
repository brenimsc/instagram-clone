package com.breno.instagram.home.data

import com.breno.instagram.common.base.Cache
import com.breno.instagram.common.model.Post

class HomeDatasourceFactory(
    private val feedCache: Cache<List<Post>>
) {
    fun createLocalDatasource(): HomeDatasource {
        return HomeLocalDatasource(feedCache)
    }

    fun createRemoteDatasource(): HomeDatasource {
        return FireHomeDatasource()
    }

    fun createFromFeed(): HomeDatasource {
        return if (feedCache.isCached()) createLocalDatasource() else FireHomeDatasource()
    }
}