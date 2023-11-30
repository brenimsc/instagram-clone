package com.breno.instagram.post.data

import android.net.Uri

interface PostDatasource {
    suspend fun fetchPictures(): List<Uri>
}