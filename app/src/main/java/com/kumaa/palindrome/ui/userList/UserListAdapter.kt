package com.kumaa.palindrome.ui.userList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kumaa.palindrome.data.response.UserItem
import com.kumaa.palindrome.databinding.ItemUserBinding
import com.kumaa.palindrome.utils.setImageFromUrl

class UserListAdapter(private val onClick: (UserItem) -> Unit): PagingDataAdapter<UserItem, UserListAdapter.ListViewHolder>(DiffCallback) {

    class ListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, user: UserItem) {
            binding.apply {
                tvFirstName.text = user.firstName
                tvLastName.text = user.lastName
                tvUserEmail.text = user.email
                ivItemPhoto.setImageFromUrl(context, user.avatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null){
            holder.bind(holder.itemView.context, user)
        }

        holder.itemView.setOnClickListener {
            user?.let {
                onClick(it)
            }
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
