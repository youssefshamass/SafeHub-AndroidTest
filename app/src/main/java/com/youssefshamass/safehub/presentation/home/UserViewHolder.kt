package com.youssefshamass.safehub.presentation.home

import androidx.recyclerview.widget.RecyclerView
import com.youssefshamass.domain.entities.User
import com.youssefshamass.safehub.databinding.ListItemUserBinding

class UserViewHolder(val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        binding.user = user
    }
}