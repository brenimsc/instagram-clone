package com.breno.instagram.search

import com.breno.instagram.common.base.BasePresenter
import com.breno.instagram.common.base.BaseView
import com.breno.instagram.common.model.User

interface Search {

    interface Presenter : BasePresenter {
        fun fetchUsers(name: String)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayFullUsers(users: List<User>)
        fun displayEmptyUsers()
    }
}