package com.breno.instagram.home.data

import android.os.Handler
import android.os.Looper
import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Database
import com.breno.instagram.common.model.Post

// TODO: Apenas testes
class HomeFakeRemoteDatasource : HomeDatasource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val feed = Database.feeds[userUUID]

            callback.onSuccess(feed?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }
}