package com.breno.instagram.home.view

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.breno.instagram.R
import com.breno.instagram.common.DependencyInjector
import com.breno.instagram.common.base.BaseFragment
import com.breno.instagram.common.model.Post
import com.breno.instagram.databinding.FragmentHomeBinding
import com.breno.instagram.home.Home
import com.breno.instagram.home.adapter.FeedAdapter
import com.breno.instagram.home.presenter.HomePresenter
import com.breno.instagram.main.view.LogoutListener

class HomeFragment : BaseFragment<FragmentHomeBinding, Home.Presenter>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
), Home.View {

    private var logoutListener: LogoutListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LogoutListener) {
            logoutListener = context
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.fHomePbProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun displayEmptyPosts() {
        binding?.fHomeTvEmpty?.visibility = View.VISIBLE
        binding?.fHomeRvPosts?.visibility = View.GONE
    }

    override fun displayFullPosts(posts: List<Post>) {
        binding?.fHomeTvEmpty?.visibility = View.GONE
        binding?.fHomeRvPosts?.visibility = View.VISIBLE

        adapter.posts = posts
    }

    override fun getMenu(): Int? {
        return R.menu.menu_profile
    }

    override lateinit var presenter: Home.Presenter

    private val adapter = FeedAdapter()

    override fun setupViews() {
        binding?.fHomeRvPosts?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }
        presenter.fetchFeed()
    }

    override fun setupPresenter() {
        presenter = HomePresenter(this, DependencyInjector.homeRepository())
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
}