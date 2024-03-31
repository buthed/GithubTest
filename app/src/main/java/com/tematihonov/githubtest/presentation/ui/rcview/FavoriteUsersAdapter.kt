package com.tematihonov.githubtest.presentation.ui.rcview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tematihonov.githubtest.R
import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import com.tematihonov.githubtest.databinding.ItemUserBinding
import com.tematihonov.githubtest.presentation.ui.utils.loadImageWithCoil

class FavoriteUsersAdapter(
    private val deleteUserFromFavorites: (String) -> Unit,
    val onClickListener: (String) -> Unit,
) : RecyclerView.Adapter<FavoriteUsersAdapter.FavoriteUsersViewHolder>() {

    var userList: List<FavoritesUserEntity> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    inner class FavoriteUsersViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoriteUsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return FavoriteUsersViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavoriteUsersAdapter.FavoriteUsersViewHolder,
        position: Int,
    ) {
        val user = userList[position]
        with(holder.binding) {
            itemLogin.text = user.login
            itemSubtitle.text = user.id.toString()
            itemAvatar.loadImageWithCoil(user.avatar_url)
            itemFavorite.setBackgroundResource(R.drawable.icon_favorite_filled)
            itemFavorite.setOnClickListener { deleteUserFromFavorites(user.login) }
        }
        holder.itemView.setOnClickListener { onClickListener(user.login) }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}