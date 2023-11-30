package com.breno.instagram.profile.view

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.breno.instagram.R
import com.breno.instagram.common.DependencyInjector
import com.breno.instagram.common.base.BaseFragment
import com.breno.instagram.common.model.Post
import com.breno.instagram.common.model.User
import com.breno.instagram.databinding.FragmentProfileBinding
import com.breno.instagram.main.view.LogoutListener
import com.breno.instagram.profile.Profile
import com.breno.instagram.profile.adapter.PostAdapter
import com.breno.instagram.profile.presenter.ProfilePresenter
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View, BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val BUNDLE_USER_ID: String = "userId"
    }

    private val adapter = PostAdapter()

    private val uuid by lazy {
        arguments?.getString(BUNDLE_USER_ID)
    }

    private var logoutListener: LogoutListener? = null

    private var followListener: FollowListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is LogoutListener) {
            logoutListener = context
        }

        if (context is FollowListener) {
            followListener = context
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.fProfilePbProgress?.visibility =
            takeIf { enabled }?.run { View.VISIBLE } ?: View.GONE
    }

    override fun displayUserProfile(userAuth: Pair<User, Boolean?>) {
        val (userAuth, following) = userAuth

        binding?.fProfileTvQtdPublication?.text = userAuth.postCount.toString()
        binding?.fProfileTvQtdFollowing?.text = userAuth.following.toString()
        binding?.fProfileTvQtdFollowers?.text = userAuth.followers.toString()
        binding?.fProfileTvUsername?.text = userAuth.name
        binding?.fProfileTvBio?.text = "TODO"
        binding?.let {
            Glide.with(requireContext()).load(userAuth.photoUrl).into(it.fProfileCivImage)
        }

        binding?.fProfileBEditProfile?.text = when (following) {
            null -> getString(R.string.edit_profile)
            true -> getString(R.string.unfollow)
            false -> getString(R.string.follow)
        }

        binding?.fProfileBEditProfile?.tag = following

        presenter.fetchUserPosts(uuid)
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun displayEmptyPosts() {
        binding?.fProfileTvEmpty?.visibility = View.VISIBLE
        binding?.fProfileRvPosts?.visibility = View.GONE
    }

    override fun displayFullPosts(posts: List<Post>) {
        binding?.fProfileTvEmpty?.visibility = View.GONE
        binding?.fProfileRvPosts?.visibility = View.VISIBLE

        adapter.posts = posts
    }

    override fun followUpdated() {
        followListener?.followUpdated()
    }

    override lateinit var presenter: Profile.Presenter

    override fun setupViews() {
        binding?.apply {
            fProfileRvPosts.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = this@ProfileFragment.adapter

                presenter.fetchUserProfile(uuid)
            }

            fProfileBEditProfile.setOnClickListener {
                if (it.tag == true) {
                    binding?.fProfileBEditProfile?.text = getString(R.string.follow)
                    binding?.fProfileBEditProfile?.tag = false
                    presenter.followUser(uuid, false)
                } else {
                    binding?.fProfileBEditProfile?.text = getString(R.string.unfollow)
                    binding?.fProfileBEditProfile?.tag = true
                    presenter.followUser(uuid, true)
                }
            }

            fProfileBnvTabs.setOnNavigationItemSelectedListener(this@ProfileFragment)
        }
    }

    override fun setupPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this, repository)
    }

    override fun getMenu(): Int {
        return R.menu.menu_profile
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mBottomNavProfileGrid -> binding?.fProfileRvPosts?.layoutManager =
                GridLayoutManager(requireContext(), 3)

            R.id.mBottomNavProfileList -> binding?.fProfileRvPosts?.layoutManager =
                LinearLayoutManager(requireContext())
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mProfileLogout -> {
                logoutListener?.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    interface FollowListener {
        fun followUpdated()
    }
}