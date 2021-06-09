package com.youssefshamass.safehub.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.youssefshamass.domain.entities.User
import com.youssefshamass.safehub.databinding.ListItemUserBinding

class UserAdapter(private val onClickListener: (User) -> Unit) :
    ListAdapter<User, UserViewHolder>(UserDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            ListItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            onClickListener(holder.binding.user!!)
        }
    }
}

class UserDiffUtil : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.equals(newItem)
}