package com.breno.instagram.add

import android.net.Uri
import com.breno.instagram.common.base.BasePresenter
import com.breno.instagram.common.base.BaseView

interface Add {
    interface Presenter : BasePresenter {
        fun createPost(uri: Uri, caption: String)

    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestSuccess()
        fun displayRequestFailure(message: String)
    }
}