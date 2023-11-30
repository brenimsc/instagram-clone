package com.breno.instagram.home.data

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post

class HomeRepository(private val datasourceFactory: HomeDatasourceFactory) {

    fun fetchFeed(callback: RequestCallback<List<Post>>) {
        val localDatasource = datasourceFactory.createLocalDatasource()
        val userId = localDatasource.fetchSession()

        val datasource = datasourceFactory.createFromFeed()

        datasource.fetchFeed(userId, object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                localDatasource.putFeed(data)
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

            override fun onComplete() {
                callback.onComplete()
            }
        })
    }

    fun logout() {
        datasourceFactory.createRemoteDatasource().logout()
    }

    fun clearCache() {
        datasourceFactory.createLocalDatasource().putFeed(null)
    }
}