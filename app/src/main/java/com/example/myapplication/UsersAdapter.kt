package com.example.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.UserPatternBinding
import com.example.myapplication.model.User


interface UserActionListener {
    fun onDeleteUser(user: User)

    fun onAddUser(user: User)

}


class UsersAdapter(private val actionListener: UserActionListener) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener {

    class UsersViewHolder(val binding: UserPatternBinding) : RecyclerView.ViewHolder(binding.root)

    var users: List<User> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val user = v.tag as User
        actionListener.onDeleteUser(user)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserPatternBinding.inflate(inflater, parent, false)
        binding.deleteUserView.setOnClickListener(this)
        return UsersViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
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