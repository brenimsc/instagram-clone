package com.breno.instagram.splash.data

import com.google.firebase.auth.FirebaseAuth

class FireSplashDatasource : SplashDatasource {
    override fun session(callback: SplashCallback) {
        FirebaseAuth.getInstance().uid?.let {
            callback.onSuccess()
        } ?: callback.onFailure()
    }
}