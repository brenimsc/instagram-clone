package com.breno.instagram.splash.data

class SplashRepository(
    private val dataSource: SplashDatasource
) {

    fun session(callback: SplashCallback) {
        dataSource.session(callback)
    }
}