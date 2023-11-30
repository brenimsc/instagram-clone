package com.breno.instagram.post.data

import android.net.Uri

class PostRepository(private val datasource: PostDatasource) {

    suspend fun fetchPictures(): List<Uri> {
        return datasource.fetchPictures()
    }
}