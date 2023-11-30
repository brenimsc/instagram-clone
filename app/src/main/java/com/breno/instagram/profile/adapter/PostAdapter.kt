package com.breno.instagram.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.breno.instagram.common.model.Post
import com.breno.instagram.databinding.ItemProfileGridBinding
import com.bumptech.glide.Glide

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var posts: List<Post> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProfileGridBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemProfileGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            Glide.with(binding.root).load(post.photoUrl).into(binding.iProfileGridIvPhoto)
        }
    }
}