package com.youssefshamass.safehub.presentation.home

import androidx.recyclerview.widget.RecyclerView
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.safehub.databinding.ListItemUserBinding

class UserViewHolder(val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        binding.user = user
    }
}