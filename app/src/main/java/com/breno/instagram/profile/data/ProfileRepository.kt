package com.breno.instagram.profile.data

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post
import com.breno.instagram.common.model.User

class ProfileRepository(private val datasourceFactory: ProfileDatasourceFactory) {

    fun fetchUserProfile(uuid: String?, callback: RequestCallback<Pair<User, Boolean?>>) {
        val localDatasource = datasourceFactory.createLocalDatasource()
        val userId = uuid ?: localDatasource.fetchSession()

        val datasource = datasourceFactory.createFromUser(uuid)
        datasource.fetchUserProfile(userId, object : RequestCallback<Pair<User, Boolean?>> {
            override fun onSuccess(data: Pair<User, Boolean?>) {
                if (uuid == null) {
                    localDatasource.putUser(data)
                }
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

    fun fetchUserPosts(uuid: String?, callback: RequestCallback<List<Post>>) {
        val localDatasource = datasourceFactory.createLocalDatasource()
        val userId = uuid ?: localDatasource.fetchSession()

        val datasource = datasourceFactory.createFromPosts(uuid)

        datasource.fetchUserPosts(userId, object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (uuid == null) {
                    localDatasource.putPosts(data)
                }
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

    fun followUser(uuid: String?, follow: Boolean, callback: RequestCallback<Boolean>) {
        val localDatasource = datasourceFactory.createLocalDatasource()
        val userId = uuid ?: localDatasource.fetchSession()
        val datasource = datasourceFactory.createRemoteDatasource()

        datasource.followUser(userId, follow, object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
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

    fun clearCache() {
        datasourceFactory.createLocalDatasource().run {
            putPosts(null)
            putUser(null)
        }
    }
}