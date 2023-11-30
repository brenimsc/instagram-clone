package com.breno.instagram.register.presentation

import android.util.Patterns
import com.breno.instagram.R
import com.breno.instagram.register.RegisterCallback
import com.breno.instagram.register.RegisterEmail
import com.breno.instagram.register.data.RegisterRepository

class RegisterEmailPresenter(
    private var view: RegisterEmail.View?,
    private val repository: RegisterRepository
) : RegisterEmail.Presenter {

    override fun create(email: String) {
        var valid = true
        takeUnless { Patterns.EMAIL_ADDRESS.matcher(email).matches() }?.run {
            view?.displayEmailFailure(R.string.invalid_email)
            valid = false
        } ?: view?.displayEmailFailure(null)

        if (valid) {
            view?.showProgress(true)
            repository.create(email, object : RegisterCallback {
                override fun onSuccess() {
                    view?.goToNameAndPasswordScreen(email)
                }

                override fun onFailure(message: String) {
                    view?.onEmailFailure(message)
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