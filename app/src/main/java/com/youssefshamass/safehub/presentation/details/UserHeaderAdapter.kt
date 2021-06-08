package com.youssefshamass.safehub.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.youssefshamass.safehub.databinding.ListItemUserHeaderBinding
import com.youssefshamass.domain.entities.UserHeader

class UserHeaderAdapter : PagingDataAdapter<UserHeader, UserHeaderViewHolder>(UserHeaderDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHeaderViewHolder =
        UserHeaderViewHolder(
            ListItemUserHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserHeaderViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}

private class UserHeaderDiffUtil : DiffUtil.ItemCallback<UserHeader>() {
    override fun areItemsTheSame(oldItem: UserHeader, newItem: UserHeader): Boolean =
        oldItem.loginName == newItem.loginName

    override fun areContentsTheSame(oldItem: UserHeader, newItem: UserHeader): Boolean =
        oldItem == newItem
}

