package com.breno.instagram.add.data

import android.net.Uri
import com.breno.instagram.common.base.RequestCallback

class AddRepository(
    private val remoteDatasource: FireAddDatasource,
    private val localDatasource: AddLocalDatasource
) {
    fun createPost(uri: Uri, caption: String, callback: RequestCallback<Boolean>) {
        val uuid = localDatasource.fetchSession()

        remoteDatasource.createPost(uuid, uri, caption, object : RequestCallback<Boolean> {
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
}