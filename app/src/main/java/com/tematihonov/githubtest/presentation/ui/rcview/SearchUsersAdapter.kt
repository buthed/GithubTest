package com.tematihonov.githubtest.presentation.ui.rcview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tematihonov.githubtest.databinding.ItemUserBinding
import com.tematihonov.githubtest.domain.models.responseSearch.Item
import com.tematihonov.githubtest.presentation.ui.utils.loadImageWithCoil

class SearchUsersAdapter(
    val onClickListener: (String) -> Unit,
) : PagingDataAdapter<Item, SearchUsersAdapter.SearchUsersViewHolder>(UsersDiffCallback()) {

    inner class SearchUsersViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchUsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return SearchUsersViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SearchUsersAdapter.SearchUsersViewHolder,
        position: Int,
    ) {
        val user = getItem(position) ?: return
        with(holder.binding) {
            itemLogin.text = user.login
            itemSubtitle.text = user.id.toString()
            itemAvatar.loadImageWithCoil(user.avatar_url)
            itemFavorite.visibility = View.GONE
        }
        holder.itemView.setOnClickListener { onClickListener(user.login) }
    }
}

class UsersDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}