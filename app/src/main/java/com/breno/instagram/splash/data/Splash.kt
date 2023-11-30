package com.breno.instagram.splash.data

import com.breno.instagram.common.base.BasePresenter
import com.breno.instagram.common.base.BaseView

interface Splash {

    interface Presenter : BasePresenter {
        fun authenticated()
    }

    interface View : BaseView<Presenter> {
        fun goToMainScreen()
        fun goToLoginScreen()
    }
}