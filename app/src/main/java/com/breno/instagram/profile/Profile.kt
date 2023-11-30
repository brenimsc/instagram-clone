package com.breno.instagram.profile

import com.breno.instagram.common.base.BasePresenter
import com.breno.instagram.common.base.BaseView
import com.breno.instagram.common.model.Post

interface Profile {

    interface Presenter : BasePresenter {
        fun fetchUserProfile(uuid: String?)
        fun fetchUserPosts(uuid: String?)
        fun followUser(uuid: String?, follow: Boolean)
        fun clear()
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(userAuth: Pair<com.breno.instagram.common.model.User, Boolean?>)
        fun displayRequestFailure(message: String)
        fun displayEmptyPosts()
        fun displayFullPosts(posts: List<Post>)
        fun followUpdated()
    }
}