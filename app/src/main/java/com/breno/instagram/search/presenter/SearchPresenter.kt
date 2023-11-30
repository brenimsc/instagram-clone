package com.breno.instagram.search.presenter

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.User
import com.breno.instagram.search.Search
import com.breno.instagram.search.data.SearchRepository

class SearchPresenter(
    private var view: Search.View?,
    private val repository: SearchRepository
) : Search.Presenter {

    override fun fetchUsers(name: String) {
        view?.showProgress(true)
        repository.fetchUsers(name, object : RequestCallback<List<User>> {
            override fun onSuccess(data: List<User>) {
                when {
                    data.isEmpty() -> view?.displayEmptyUsers()
                    else -> view?.displayFullUsers(data)
                }
            }

            override fun onFailure(message: String) {
                view?.displayEmptyUsers()
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