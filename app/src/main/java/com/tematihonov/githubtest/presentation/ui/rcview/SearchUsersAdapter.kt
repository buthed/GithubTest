package com.tematihonov.githubtest.presentation.ui.rcview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.tematihonov.githubtest.R
import com.tematihonov.githubtest.data.models.responseSearch.Item
import com.tematihonov.githubtest.databinding.ItemUserBinding
import com.tematihonov.githubtest.presentation.ui.utils.loadImageWithCoil

class SearchUsersAdapter(
    val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<SearchUsersAdapter.SearchUsersViewHolder>() {

    var userList: List<Item> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

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
        val user = userList[position]
        with(holder.binding) {
            itemLogin.text = user.login
            itemSubtitle.text = user.id.toString()
            itemAvatar.loadImageWithCoil(user.avatar_url)
        }
        holder.itemView.setOnClickListener { onClickListener(user.login) }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}