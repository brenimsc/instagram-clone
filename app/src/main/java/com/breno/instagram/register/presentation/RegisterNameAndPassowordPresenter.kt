package com.breno.instagram.register.presentation

import com.breno.instagram.R
import com.breno.instagram.register.RegisterCallback
import com.breno.instagram.register.RegisterNameAndPassword
import com.breno.instagram.register.data.RegisterRepository

class RegisterNameAndPassowordPresenter(
    private var view: RegisterNameAndPassword.View?,
    private val repository: RegisterRepository
) : RegisterNameAndPassword.Presenter {

    override fun create(email: String, name: String, password: String, confirmPassword: String) {
        var valid = true

        takeUnless { name.length > 3 }?.run {
            view?.displayNameFailure(R.string.invalid_name)
            valid = false
        } ?: view?.displayNameFailure(null)

        takeUnless { password == confirmPassword }?.run {
            valid = false
            view?.displayPasswordFailure(R.string.password_not_equal)
        } ?: takeIf { password.length < 8 }?.run {
            view?.displayPasswordFailure(R.string.invalid_password)
            valid = false
        } ?: view?.displayPasswordFailure(null)

        if (valid) {
            view?.showProgress(true)
            repository.create(email, name, password, object : RegisterCallback {
                override fun onSuccess() {
                    view?.onCreateSucces(name)
                }

                override fun onFailure(message: String) {
                    view?.onCreateFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }

            })
        }
    }

    override fun onDestroy() {
        view = null
    }
}