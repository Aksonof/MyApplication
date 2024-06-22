package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.UserPatternBinding
import com.example.myapplication.loadImage
import com.example.myapplication.model.User


class UsersAdapter(private val actionListener: UserActionListener) :
    ListAdapter<User, UsersAdapter.UsersViewHolder>(MyItemCallback()) {


    class UsersViewHolder(
        private val binding: UserPatternBinding, private val actionListener: UserActionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: User) {

            with(binding) {
                userNameView.text = item.name
                userCareerView.text = item.career
                loadImage(userPhotoView, item.photo)

                itemView.setOnClickListener {
                    actionListener.onUserDetails(item)
                }
                deleteUserView.setOnClickListener {
                    actionListener.onDeleteUser(item)
                }
            }
        }
    }

    class MyItemCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserPatternBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding, actionListener)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)
        holder.onBind(user)
    }
}