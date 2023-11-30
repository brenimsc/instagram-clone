package com.breno.instagram.search.data

import android.os.Handler
import android.os.Looper
import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.User

class SearchFakeRemoteDatasource : SearchDatasource {

    // TODO: Apenas testes
    override fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            callback.onComplete()
        }, 2000)
    }
}