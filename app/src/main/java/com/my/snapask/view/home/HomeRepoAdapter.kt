package com.my.snapask.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.my.snapask.R
import com.my.snapask.model.api.vo.RepoItem
import kotlinx.android.synthetic.main.item_home_repo.view.*
import timber.log.Timber


class HomeRepoAdapter(
    private val homeFuncListener: HomeFuncItem
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<RepoItem> = arrayListOf()

    companion object {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_repo, parent, false)
        return HomeReposViewHolder(mView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data.get(position)
        val context = holder.itemView.context
        holder as HomeReposViewHolder
        holder.tvFullName.text = item.full_name
        holder.tvDescription.text = item.description
        holder.tvLanguage.text = item.language
        holder.tvWatchersCount.text = item.watchers_count.toString()

        holder.clRoot.setOnClickListener {
            homeFuncListener.onRepoSelect.invoke(item.html_url?:"")
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<RepoItem>?) {
        this.data.clear()
        data?.also {
            this.data.addAll(it)
        }
        notifyDataSetChanged()
    }

    class HomeReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFullName: TextView = itemView.tv_full_name
        val tvDescription: TextView = itemView.tv_description
        val tvLanguage: TextView = itemView.tv_language
        val tvWatchersCount: TextView = itemView.tv_watcher_count
        val clRoot: ConstraintLayout = itemView.cl_root
    }
}