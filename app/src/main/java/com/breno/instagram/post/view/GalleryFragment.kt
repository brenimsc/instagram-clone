package com.breno.instagram.post.view

import android.net.Uri
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.breno.instagram.R
import com.breno.instagram.common.DependencyInjector
import com.breno.instagram.common.base.BaseFragment
import com.breno.instagram.databinding.FragmentGalleryBinding
import com.breno.instagram.post.adapter.PictureAdapter
import com.breno.instagram.post.Post
import com.breno.instagram.post.presenter.PostPresenter

class GalleryFragment : BaseFragment<FragmentGalleryBinding, Post.Presenter>(
    R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
),
    Post.View {

    private val adapter = PictureAdapter {
        setPicureSelected(it)
        presenter.selectedUri(it)
    }

    override fun showProgress(enabled: Boolean) {
        activity?.runOnUiThread {
            binding?.fGalleryPbProgress?.visibility =
                takeIf { enabled }?.run { View.VISIBLE } ?: View.GONE
        }
    }

    override fun displayEmptyPictures() {
        activity?.runOnUiThread {
            binding?.fGalleryTvEmpty?.visibility = View.VISIBLE
            binding?.fGalleryRvPictures?.visibility = View.GONE
        }
    }

    override fun displayFullPictures(posts: List<Uri>) {
        activity?.runOnUiThread {
            binding?.fGalleryTvEmpty?.visibility = View.GONE
            binding?.fGalleryRvPictures?.visibility = View.VISIBLE

            adapter.pictures = posts
            adapter.notifyDataSetChanged()
            setPicureSelected(posts.first())
            presenter.selectedUri(posts.first())
        }
    }

    override fun getMenu(): Int {
        return R.menu.menu_send
    }

    private fun setPicureSelected(image: Uri) {
        activity?.runOnUiThread {
            binding?.fGalleryIvImgSelected?.setImageURI(image)
            binding?.fGalleryNsvNested?.smoothScrollTo(0, 0)
        }
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override lateinit var presenter: Post.Presenter

    override fun setupViews() {
        binding?.fGalleryRvPictures?.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = this@GalleryFragment.adapter

            presenter.fetchPictures()
        }
    }

    override fun setupPresenter() {
        presenter = PostPresenter(this, DependencyInjector.postRepository(requireContext()))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> setFragmentResult(
                "takePhotoKey",
                bundleOf("uri" to presenter.getSelectedUri())
            )
        }

        return super.onOptionsItemSelected(item)
    }

}