package com.breno.instagram.register

import androidx.annotation.StringRes
import com.breno.instagram.common.base.BasePresenter
import com.breno.instagram.common.base.BaseView

interface RegisterEmail {

    interface Presenter : BasePresenter {
        fun create(email: String)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)

        fun displayEmailFailure(@StringRes emailError: Int?)

        fun onEmailFailure(message: String)

        fun goToNameAndPasswordScreen(email: String)
    }
}