package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.UserPatternBinding
import com.example.myapplication.model.User


interface UserActionListener {
    fun onDeleteUser(user: User)
    fun onUserDetails(user: User)
    fun onSelectUser(user: User)
}

class UsersAdapter(private val actionListener: UserActionListener) :
    ListAdapter<User, UsersAdapter.UsersViewHolder>(MyItemCallback()), View.OnClickListener,
    View.OnLongClickListener {

    var isModeActive = false

    class UsersViewHolder(val binding: UserPatternBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onClick(v: View) {
        val user = v.tag as User

        if (isModeActive) {
            actionListener.onSelectUser(user)
        } else {
            when (v.id) {
                R.id.deleteUserView -> {
                    actionListener.onDeleteUser(user)
                }

                else -> {
                    actionListener.onUserDetails(user)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onLongClick(v: View?): Boolean {
        val user = v?.tag as User
        isModeActive = true
        actionListener.onSelectUser(user)
        notifyDataSetChanged()
        return true
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
        binding.deleteUserView.setOnClickListener(this)
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)


        if (isModeActive) {
            holder.binding.root.setBackgroundResource(R.drawable.selected_user_background)
            val layoutParams =
                holder.binding.userPhotoView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.marginStart =
                (40 * holder.binding.root.context.resources.displayMetrics.density).toInt()
            holder.binding.userPhotoView.layoutParams = layoutParams
            holder.binding.deleteUserView.visibility = View.GONE
            holder.binding.checkBox.visibility = View.VISIBLE


            holder.binding.checkBox.isChecked = user.isSelected


        }

        with(holder.binding) {

            holder.itemView.tag = user
            deleteUserView.tag = user

            userNameView.text = user.name
            userCareerView.text = user.career
            if (user.photo.isNotBlank()) {
                Glide.with(userPhotoView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_default_photo)
                    .error(R.drawable.ic_default_photo)
                    .into(userPhotoView)
            } else {
                Glide.with(userPhotoView.context).clear(userPhotoView)
                userPhotoView.setImageResource(R.drawable.ic_default_photo)
            }
        }
    }


}




