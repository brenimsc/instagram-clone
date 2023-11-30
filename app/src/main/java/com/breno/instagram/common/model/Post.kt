package com.breno.instagram.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val uuid: String? = null,
    val photoUrl: String? = null,
    val caption: String? = null,
    val timestamp: Long? = null,
    val publisher: User? = null
) : Parcelable
