package com.youssefshamass.safehub.presentation.details

import androidx.recyclerview.widget.RecyclerView
import com.youssefshamass.safehub.databinding.ListItemUserHeaderBinding
import com.youssefshamass.domain.entities.UserHeader

class UserHeaderViewHolder(val binding: ListItemUserHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(user: UserHeader) {
        binding.user = user
    }
}