package com.breno.instagram.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.breno.instagram.common.model.User
import com.breno.instagram.databinding.ItemUserListBinding
import com.bumptech.glide.Glide

class SearchAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var items: List<User> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Glide.with(binding.root).load(user.photoUrl).into(binding.iUserListCivPhoto)
            binding.iUserListTvUsername.text = user.name
            binding.root.setOnClickListener {
                onClick.invoke(user.uuid ?: return@setOnClickListener)
            }
        }
    }
}