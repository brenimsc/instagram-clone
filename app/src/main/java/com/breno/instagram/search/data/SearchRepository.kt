package com.breno.instagram.search.data

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.User

class SearchRepository(private val datasource: SearchDatasource) {

    fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
        datasource.fetchUsers(name, object : RequestCallback<List<User>> {
            override fun onSuccess(data: List<User>) {
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
}