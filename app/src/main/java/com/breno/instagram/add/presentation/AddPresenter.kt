package com.breno.instagram.add.presentation

import android.net.Uri
import com.breno.instagram.add.Add
import com.breno.instagram.add.data.AddRepository
import com.breno.instagram.common.base.RequestCallback

class AddPresenter(private var view: Add.View? = null, private val repository: AddRepository) :
    Add.Presenter {
    override fun createPost(uri: Uri, caption: String) {
        view?.showProgress(true)
        repository.createPost(uri, caption, object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                if (data) {
                    view?.displayRequestSuccess()
                } else {
                    view?.displayRequestFailure("internal error")
                }
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }

        })
    }

    override fun onDestroy() {
        view = null
    }
}