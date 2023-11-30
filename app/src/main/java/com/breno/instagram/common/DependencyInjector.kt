package com.breno.instagram.common

import android.content.Context
import com.breno.instagram.add.data.AddLocalDatasource
import com.breno.instagram.add.data.AddRepository
import com.breno.instagram.add.data.FireAddDatasource
import com.breno.instagram.home.data.FeedMemoryCache
import com.breno.instagram.home.data.HomeDatasourceFactory
import com.breno.instagram.home.data.HomeRepository
import com.breno.instagram.login.data.FireLoginDatasource
import com.breno.instagram.login.data.LoginRepository
import com.breno.instagram.post.data.PostLocalDatasource
import com.breno.instagram.post.data.PostRepository
import com.breno.instagram.profile.data.PostsMemoryCache
import com.breno.instagram.profile.data.ProfileDatasourceFactory
import com.breno.instagram.profile.data.ProfileMemoryCache
import com.breno.instagram.profile.data.ProfileRepository
import com.breno.instagram.register.data.FireRegisterDatasource
import com.breno.instagram.register.data.RegisterRepository
import com.breno.instagram.search.data.FireSearchDatasource
import com.breno.instagram.search.data.SearchRepository
import com.breno.instagram.splash.data.FireSplashDatasource
import com.breno.instagram.splash.data.SplashRepository

object DependencyInjector {

    fun splashRepository(): SplashRepository {
        return SplashRepository(FireSplashDatasource())
    }

    fun loginRepository(): LoginRepository {
        return LoginRepository(FireLoginDatasource())
    }

    fun registerEmailRepository(): RegisterRepository {
        return RegisterRepository(FireRegisterDatasource())
    }

    fun searchRepository(): SearchRepository {
        return SearchRepository(FireSearchDatasource())
    }

    fun profileRepository(): ProfileRepository {
        return ProfileRepository(
            ProfileDatasourceFactory(ProfileMemoryCache, PostsMemoryCache)
        )
    }

    fun homeRepository(): HomeRepository {
        return HomeRepository(
            HomeDatasourceFactory(FeedMemoryCache)
        )
    }

    fun addRepository(): AddRepository {
        return AddRepository(FireAddDatasource(), AddLocalDatasource())
    }

    fun postRepository(context: Context): PostRepository {
        return PostRepository(PostLocalDatasource(context))
    }
}