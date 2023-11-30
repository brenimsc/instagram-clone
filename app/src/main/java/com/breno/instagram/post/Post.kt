package com.breno.instagram.post

import android.net.Uri
import com.breno.instagram.common.base.BasePresenter
import com.breno.instagram.common.base.BaseView

interface Post {

    interface Presenter : BasePresenter {
        fun selectedUri(uri: Uri)
        fun getSelectedUri(): Uri?
        fun fetchPictures()
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmptyPictures()
        fun displayFullPictures(posts: List<Uri>)
        fun displayRequestFailure(message: String)
    }
}