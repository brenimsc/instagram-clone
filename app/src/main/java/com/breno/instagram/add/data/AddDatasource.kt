package com.breno.instagram.add.data

import android.net.Uri
import com.breno.instagram.common.base.RequestCallback

interface AddDatasource {

    fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallback<Boolean>) { throw UnsupportedOperationException() }

    fun fetchSession(): String { throw UnsupportedOperationException() }
}