package com.dsm.eh.data.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dsm.eh.data.response.Group
import com.dsm.eh.databinding.GroupListBinding

class GroupRecyclerViewAdapter :
    RecyclerView.Adapter<GroupRecyclerViewAdapter.RecyclerViewHolder>() {
    var groupList = mutableListOf<Group>()

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = GroupListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = groupList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = groupList[position]
        holder.bind(item)

    }

    inner class RecyclerViewHolder(private val binding: GroupListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(group: Group) {
            binding.title.text = group.title
            Glide.with(binding.profile)
                .load(group.profile_image)
                .into(binding.profile)
            binding.oneliner.text = group.description
        }

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
    }

    fun replaceList(newList: MutableList<Group>) {
        groupList = newList.toMutableList()
        notifyDataSetChanged()
    }
}