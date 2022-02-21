package com.my.snapask.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.my.snapask.R
import com.my.snapask.model.api.vo.RepoItem
import com.my.snapask.model.api.vo.UserItem
import kotlinx.android.synthetic.main.item_home_detail.view.*
import kotlinx.android.synthetic.main.item_home_repos.view.*
import kotlinx.android.synthetic.main.item_home_users.view.*
import timber.log.Timber


class HomeAdapter(
    private val homeFuncListener: HomeFuncItem
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val homeUserAdapter = HomeUserAdapter(homeFuncListener)
    val homeRepoAdapter = HomeRepoAdapter(homeFuncListener)
    var detailItem: UserItem? = null

    companion object {
        const val VIEW_TYPE_USER = 0
        const val VIEW_TYPE_DETAIL = 1
        const val VIEW_TYPE_REPOS = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_USER
            1 -> VIEW_TYPE_DETAIL
            else -> VIEW_TYPE_REPOS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val mView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_users, parent, false)
                HomeUsersViewHolder(mView)
            }
            VIEW_TYPE_REPOS -> {
                val mView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_repos, parent, false)
                HomeReposViewHolder(mView)
            }
            else -> {
                val mView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_detail, parent, false)
                HomeDetailViewHolder(mView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        when (holder) {
            is HomeUsersViewHolder -> {
                holder.rvUsers.also {
                    it.setHasFixedSize(true)
                    val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.layoutManager = linearLayoutManager
                    it.adapter = homeUserAdapter
                }
            }
            is HomeReposViewHolder -> {
                holder.rvRepos.also {
                    it.setHasFixedSize(true)
                    val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    it.layoutManager = linearLayoutManager
                    it.adapter = homeRepoAdapter
                    initDivider(context, it)
                }
                holder.ivEmpty.visibility = if(homeRepoAdapter.itemCount == 0) VISIBLE else GONE
            }
            is HomeDetailViewHolder -> {
                holder.tvName.text = detailItem?.name?:"-"
                holder.tvBio.text = detailItem?.bio?:"-"
                holder.tvCompany.text = detailItem?.company?:"-"
                holder.tvLocation.text = detailItem?.location?:"-"
                holder.tvEmail.text = detailItem?.email?:"-"
                if(detailItem?.hireable == true) {
                    holder.tvHirable.text = context.getString(R.string.open_to_work)
                    holder.tvHirable.background = context.getDrawable(R.color.design_default_color_secondary)
                } else {
                    holder.tvHirable.text = context.getString(R.string.not_open_to_work)
                    holder.tvHirable.background = context.getDrawable(R.color.design_default_color_primary)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    fun setUsersData(data: List<UserItem>?){
        homeUserAdapter.setData(data)
    }

    fun setCurrentUser(userName: String){
        homeUserAdapter.setCurrentUser(userName)
    }

    fun setReposData(data: List<RepoItem>?){
        homeRepoAdapter.setData(data)
        notifyItemChanged(2)
    }

    fun setDetail(userItem: UserItem?){
        detailItem = userItem
        notifyItemChanged(1)
    }

    private fun initDivider(context: Context, recyclerView: RecyclerView) {
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation
        )

        ContextCompat.getDrawable(context, R.drawable.repos_divider)
            ?.run {
                dividerItemDecoration.setDrawable(this)
                recyclerView.addItemDecoration(dividerItemDecoration)
            }
    }

    class HomeUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvUsers: RecyclerView = itemView.rv_home_users_list
    }

    class HomeDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.tv_full_name
        val tvBio: TextView = itemView.tv_bio
        val tvCompany: TextView = itemView.tv_company
        val tvLocation: TextView = itemView.tv_location
        val tvEmail: TextView = itemView.tv_email
        val tvHirable: TextView = itemView.tv_hirable
    }

    class HomeReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvRepos: RecyclerView = itemView.rv_home_repos_list
        val ivEmpty: ImageView = itemView.iv_empty
    }
}