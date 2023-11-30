package com.breno.instagram.search.data

import com.breno.instagram.common.base.RequestCallback
import com.breno.instagram.common.model.User

interface SearchDatasource {
    fun fetchUsers(name: String, callback: RequestCallback<List<User>>)
}