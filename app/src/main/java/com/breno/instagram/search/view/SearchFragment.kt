package com.breno.instagram.search.view

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.breno.instagram.R
import com.breno.instagram.common.DependencyInjector
import com.breno.instagram.common.base.BaseFragment
import com.breno.instagram.common.model.User
import com.breno.instagram.databinding.FragmentSearchBinding
import com.breno.instagram.search.Search
import com.breno.instagram.search.adapter.SearchAdapter
import com.breno.instagram.search.presenter.SearchPresenter

class SearchFragment : BaseFragment<FragmentSearchBinding, Search.Presenter>(
    R.layout.fragment_search,
    FragmentSearchBinding::bind
), Search.View {

    private val adapter by lazy {
        SearchAdapter {
            searchListener?.goToProfile(it)
        }
    }

    private var searchListener: SearchListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchListener) {
            searchListener = context
        }
    }

    override fun setupViews() {
        binding?.fSearchRvUsers?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
        }
    }

    override fun setupPresenter() {
        presenter = SearchPresenter(this, DependencyInjector.searchRepository())
    }

    override fun getMenu() = R.menu.menu_search

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.mSearchSearch).actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isNotEmpty() == true) {
                        presenter.fetchUsers(newText)
                        return true
                    }
                    return false
                }

            })
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.fSearchPbProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayFullUsers(users: List<User>) {
        binding?.fSearchTvEmpty?.visibility = View.GONE
        binding?.fSearchRvUsers?.visibility = View.VISIBLE
        adapter.items = users
    }

    override fun displayEmptyUsers() {
        binding?.fSearchTvEmpty?.visibility = View.VISIBLE
        binding?.fSearchRvUsers?.visibility = View.GONE
    }

    override lateinit var presenter: Search.Presenter

    interface SearchListener {
        fun goToProfile(uuid: String)
    }
}