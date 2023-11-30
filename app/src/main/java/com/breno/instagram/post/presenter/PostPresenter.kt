package com.breno.instagram.post.presenter

import android.net.Uri
import com.breno.instagram.post.data.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PostPresenter(
    private var view: com.breno.instagram.post.Post.View?,
    private val repository: PostRepository
) : com.breno.instagram.post.Post.Presenter, CoroutineScope {

    private var uri: Uri? = null
    private val job = Job()
    override val coroutineContext = job + Dispatchers.IO

    override fun selectedUri(uri: Uri) {
        this.uri = uri
    }

    override fun getSelectedUri(): Uri? {
        return uri
    }

    override fun fetchPictures() {
        view?.showProgress(true)

        launch {
            val pictures = repository.fetchPictures()

            with(Dispatchers.Main) {
                if (pictures.isEmpty()) {
                    view?.displayEmptyPictures()
                } else {
                    view?.displayFullPictures(pictures)
                }

                view?.showProgress(false)
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        view = null
    }
}