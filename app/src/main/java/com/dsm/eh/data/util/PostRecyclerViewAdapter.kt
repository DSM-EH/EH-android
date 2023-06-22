package com.dsm.eh.data.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dsm.eh.data.response.Post
import com.dsm.eh.databinding.GroupPostListBinding

class PostRecyclerViewAdapter :
    RecyclerView.Adapter<PostRecyclerViewAdapter.RecyclerViewHolder>() {
    var postList = mutableListOf<Post>()

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = GroupPostListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = postList[position]
        holder.bind(item)

    }

    inner class RecyclerViewHolder(private val binding: GroupPostListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.title.text = post.title
            Glide.with(binding.profileImage)
                .load(post.writer.profile_image_url)
                .into(binding.profileImage)
            binding.name.text = post.writer.nickname
            binding.day.text = post.created_at
            binding.content.text = post.content
        }

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
    }

    fun replaceList(newList: MutableList<Post>) {
        postList = newList.toMutableList()
        notifyDataSetChanged()
    }
}