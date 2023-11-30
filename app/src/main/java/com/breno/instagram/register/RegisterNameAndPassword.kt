package com.breno.instagram.register

import androidx.annotation.StringRes
import com.breno.instagram.common.base.BasePresenter
import com.breno.instagram.common.base.BaseView

interface RegisterNameAndPassword {
    interface Presenter : BasePresenter {
        fun create(email: String, name: String, password: String, confirmPassword: String)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)

        fun displayNameFailure(@StringRes nameError: Int?)

        fun displayPasswordFailure(@StringRes passwordError: Int?)

        fun onCreateFailure(message: String)

        fun onCreateSucces(name: String)

    }
}