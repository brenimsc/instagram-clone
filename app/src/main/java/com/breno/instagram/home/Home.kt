package com.breno.instagram.home

import com.breno.instagram.common.base.BasePresenter
import com.breno.instagram.common.base.BaseView
import com.breno.instagram.common.model.Post

interface Home {

    interface Presenter : BasePresenter {
        fun fetchFeed()
        fun logout()
        fun clear()
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestFailure(message: String)
        fun displayEmptyPosts()
        fun displayFullPosts(posts: List<Post>)
    }

}