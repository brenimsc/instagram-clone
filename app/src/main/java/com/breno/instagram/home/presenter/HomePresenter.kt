package com.breno.instagram.home.presenter

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post
import com.breno.instagram.home.Home
import com.breno.instagram.home.data.HomeRepository

class HomePresenter(private var view: Home.View?, private val repository: HomeRepository) :
    Home.Presenter {

    override fun fetchFeed() {
        view?.showProgress(true)
        repository.fetchFeed(object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                when {
                    data.isEmpty() -> view?.displayEmptyPosts()
                    else -> view?.displayFullPosts(data)
                }
            }

            override fun onFailure(message: String) { /*do nothing*/
            }

            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }

    override fun clear() {
        repository.clearCache()
    }

    override fun logout() {
        repository.logout()
    }

    override fun onDestroy() {
        view = null
    }
}