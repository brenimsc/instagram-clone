package com.breno.instagram.login.presentation

import android.util.Patterns
import com.breno.instagram.R
import com.breno.instagram.login.Login
import com.breno.instagram.login.data.LoginCallback
import com.breno.instagram.login.data.LoginRepository

class LoginPresenter(private var view: Login.View?, private val repository: LoginRepository) :
    Login.Presenter {

    override fun login(email: String, password: String) {
        var valid = true
        takeIf { password.length < 8 }?.run {
            view?.displayPasswordFailure(R.string.invalid_password)
            valid = false
        } ?: view?.displayPasswordFailure(null)
        takeUnless { Patterns.EMAIL_ADDRESS.matcher(email).matches() }?.run {
            view?.displayEmailFailure(R.string.invalid_email)
            valid = false
        } ?: view?.displayEmailFailure(null)

        if (valid) {
            view?.showProgress(true)
            repository.login(email, password, object : LoginCallback {
                override fun onSuccess() {
                    view?.onUserAuthenticate()
                }

                override fun onFailure(message: String) {
                    view?.onUserUnauthorized(message)
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