package com.breno.instagram.profile.presenter

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.Post
import com.breno.instagram.common.model.User
import com.breno.instagram.profile.Profile
import com.breno.instagram.profile.data.ProfileRepository

class ProfilePresenter(
    private var view: Profile.View?,
    private val repository: ProfileRepository
) : Profile.Presenter {

    override fun fetchUserProfile(uuid: String?) {
        view?.showProgress(true)
        repository.fetchUserProfile(uuid, object : RequestCallback<Pair<User, Boolean?>> {
            override fun onSuccess(data: Pair<User, Boolean?>) {
                view?.displayUserProfile(data)
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() { /*do nothing*/
            }

        })
    }

    override fun fetchUserPosts(uuid: String?) {
        repository.fetchUserPosts(uuid, object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                when {
                    data.isEmpty() -> view?.displayEmptyPosts()
                    else -> view?.displayFullPosts(data)
                }
            }

            override fun onFailure(message: String) { /*do nothing*/
            }

            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }

    override fun followUser(uuid: String?, follow: Boolean) {
        repository.followUser(uuid, follow, object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                fetchUserProfile(uuid)

                if (data) {
                    view?.followUpdated()
                }
            }

            override fun onFailure(message: String) {}

            override fun onComplete() {}

        })
    }

    override fun clear() {
        repository.clearCache()
    }

    override fun onDestroy() {
        view = null
    }
}