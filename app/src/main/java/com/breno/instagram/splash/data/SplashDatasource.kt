package com.breno.instagram.splash.data

interface SplashDatasource {
    fun session(callback: SplashCallback)
}