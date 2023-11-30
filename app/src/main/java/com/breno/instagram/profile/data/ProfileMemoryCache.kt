package com.breno.instagram.profile.data

import com.breno.instagram.common.base.Cache
import com.breno.instagram.common.model.User

object ProfileMemoryCache : Cache<Pair<User, Boolean?>> {

    private var user: Pair<User, Boolean?>? = null

    override fun isCached(): Boolean {
        return user != null
    }

    override fun get(key: String): Pair<User, Boolean?>? {
        return takeIf { user?.first?.uuid == key }?.run { user }
    }

    override fun put(data: Pair<User, Boolean?>?) {
        user = data
    }
}