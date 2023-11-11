package com.example.retrofitstudy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitstudy.api.Post

class PostListAdapter(val dataList: List<Post>): RecyclerView.Adapter<PostListAdapter.ItemViewHolder>() {
    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(viewType,parent,false)
        return ItemViewHolder(view)
    }


    //실제로 데이터를 보여줄 때 사용
    //holder (=callback 함수)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item=dataList[position]
        val view=holder.view
        view.findViewById<TextView>(R.id.post_title).text=item.title
        view.setOnClickListener{
            val intent=Intent(view.context,PostReadActivity::class.java)
            intent.putExtra("id",dataList[holder.adapterPosition].id)
            view.context.startActivity(intent)
        }
    }
    override fun getItemCount()=dataList.size
    override fun getItemViewType(position: Int) = R.layout.post_list_item

}