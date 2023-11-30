package com.breno.instagram.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val uuid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val postCount: Int = 0,
    val following: Int = 0,
    val followers: Int = 0
) : Parcelable

