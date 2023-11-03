package com.example.retrofitstudy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitstudy.api.Post

class PostListAdapter(val dataList: List<Post>): RecyclerView.Adapter<PostListAdapter.ItemViewHolder>() {
    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(viewType,parent,false)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

    }
    override fun getItemCount()=dataList.size
    override fun getItemViewType(position: Int) = R.layout.post_list_item

}