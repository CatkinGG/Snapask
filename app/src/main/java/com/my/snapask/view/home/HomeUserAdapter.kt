package com.my.snapask.view.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my.snapask.R
import com.my.snapask.model.api.vo.UserItem
import kotlinx.android.synthetic.main.item_home_user.view.*


class HomeUserAdapter(
    private val homeFuncListener: HomeFuncItem,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: MutableList<UserItem> = arrayListOf()
    private var currentUserName: String = ""

    companion object {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_user, parent, false)
        return HomeUserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data.get(position)
        val context = holder.itemView.context
        holder as HomeUserViewHolder
        Glide.with(context)
            .load(item.avatar_url)
            .circleCrop()
            .into(holder.ivAvatar)
        holder.tvName.text = item.login
        holder.clRoot.setOnClickListener {
            homeFuncListener.onUserSelect.invoke(item.login)
        }
        if(currentUserName == item.login)
            holder.clRoot.setBackgroundColor(Color.LTGRAY)
        else
            holder.clRoot.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<UserItem>?) {
        this.data.clear()
        data?.also {
            this.data.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun setCurrentUser(userName: String){
        currentUserName = userName
        notifyDataSetChanged()
    }


    class HomeUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.iv_avatar
        val tvName: TextView = itemView.tv_name
        val clRoot: ConstraintLayout = itemView.cl_root
    }

}