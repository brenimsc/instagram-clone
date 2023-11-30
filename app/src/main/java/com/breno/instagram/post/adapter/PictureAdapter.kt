package com.breno.instagram.post.adapter

import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.breno.instagram.databinding.ItemProfileGridBinding

class PictureAdapter(private val onClick: (Uri) -> Unit) :
    RecyclerView.Adapter<PictureAdapter.ViewHolder>() {

    var pictures: List<Uri> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProfileGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = pictures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pictures[position])
    }

    inner class ViewHolder(private val binding: ItemProfileGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Uri) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                binding.root.context.contentResolver.loadThumbnail(image, Size(200, 200), null)
                    .run {
                        binding.iProfileGridIvPhoto.setImageBitmap(this)
                    }
                binding.root.setOnClickListener {
                    onClick.invoke(image)
                }
            }
        }
    }
}