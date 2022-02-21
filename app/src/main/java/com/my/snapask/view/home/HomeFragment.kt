package com.my.snapask.view.home

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.my.snapask.R
import com.my.snapask.model.api.ApiResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    companion object {
        fun newInstance() = HomeFragment()
    }
    private val viewModel: HomeViewModel by viewModels()

    private val homeFuncItem = HomeFuncItem(
        onUserSelect = { id ->
            viewModel.setSelectedUser(id)
                 },
        onRepoSelect = { url ->
            Toast.makeText(requireContext(), "Link to: $url",
                Toast.LENGTH_SHORT).show()
        }
    )
    private val homeAdapter = HomeAdapter(homeFuncItem)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
        setupListener()
        viewModel.getUsers()
    }

    fun setupUI() {
        rv_home_list.also {
            it.setHasFixedSize(true)
            it.adapter = homeAdapter
            it.itemAnimator = null
        }
    }

    fun setupObserver() {
        viewModel.selectedUserName.observe(viewLifecycleOwner, {
            homeAdapter.setCurrentUser(it)
            homeAdapter.setReposData(null)
            viewModel.getUserByUserName(it)
            viewModel.getUserRepos(it)
            progress_bar.visibility = View.VISIBLE
        })

        viewModel.getUsersResult.observe(viewLifecycleOwner, {
            when (it) {
                is ApiResult.Success -> {
                    if(viewModel.selectedUserName.value == null && it.result != null)
                        viewModel.setSelectedUser(it.result.get(0).login)
                    homeAdapter.setUsersData(it.result)
                }
                is ApiResult.Error -> onApiError(it.throwable)
            }
            layout_refresh.isRefreshing = false
            progress_bar.visibility = GONE
        })

        viewModel.getUserResult.observe(viewLifecycleOwner, {
            when (it) {
                is ApiResult.Success -> {
                    it.result?.run{
                        homeAdapter.setDetail(it.result)
                    }
                }
                is ApiResult.Error -> {
                    onApiError(it.throwable)
                    homeAdapter.setDetail(null)
                }
            }
            layout_refresh.isRefreshing = false
            progress_bar.visibility = GONE
        })

        viewModel.getUserReposResult.observe(viewLifecycleOwner, {
            when (it) {
                is ApiResult.Success -> {
                    it.result?.run{
                        homeAdapter.setReposData(this)
                    }
                }
                is ApiResult.Error -> onApiError(it.throwable)
            }
            layout_refresh.isRefreshing = false
            progress_bar.visibility = GONE
        })
    }

    fun setupListener() {
        layout_refresh.setOnRefreshListener {
            viewModel.getUsers()
        }
    }

    private fun onApiError(throwable: Throwable) {
        Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
    }
}