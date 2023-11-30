package com.breno.instagram.splash.data

import com.breno.instagram.common.model.Database


// TODO: Apenas testes
class FakeLocalDataSource : SplashDatasource {
    override fun session(callback: SplashCallback) {
        Database.sessionAuth?.let {
            callback.onSuccess()
        } ?: callback.onFailure()
    }
}