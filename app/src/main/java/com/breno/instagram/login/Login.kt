package com.breno.instagram.login

import com.breno.instagram.common.base.BasePresenter
import com.breno.instagram.common.base.BaseView

interface Login {

    interface Presenter : BasePresenter {
        fun login(email: String, password: String)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(emailError: Int?)
        fun displayPasswordFailure(emailError: Int?)
        fun onUserAuthenticate()
        fun onUserUnauthorized(message: String)
    }
}