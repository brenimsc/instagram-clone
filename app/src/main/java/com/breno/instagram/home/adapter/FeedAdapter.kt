package com.breno.instagram.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.breno.instagram.common.model.Post
import com.breno.instagram.databinding.ItemPostListBinding
import com.bumptech.glide.Glide

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    var posts: List<Post> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPostListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    inner class ViewHolder(private val binding: ItemPostListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            Glide.with(binding.root).load(post.photoUrl).into(binding.iPostListIvPhoto)
            Glide.with(binding.root).load(post.publisher?.photoUrl)
                .into(binding.iPostListCivPhotoProfile)
            binding.iPostListTvUsername.text = post.publisher?.name
            binding.iPostListTvDescription.text = post.caption
        }
    }
}