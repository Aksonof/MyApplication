package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.UserPatternBinding
import com.example.myapplication.loadImage
import com.example.myapplication.model.User

private const val DEFAULT_MARGIN = 8
private const val MULTISELECT_MODE_MARGIN = 40

interface UserActionListener {
    fun onDeleteUser(user: User)
    fun onUserDetails(user: User)
    fun onSelectUser(user: User)
    fun onMultiSelectModeActive()
}

class UsersAdapter(private val actionListener: UserActionListener) :
    ListAdapter<User, UsersAdapter.UsersViewHolder>(MyItemCallback()), View.OnClickListener,
    View.OnLongClickListener {

    var isModeActive: Boolean = false
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class UsersViewHolder(val binding: UserPatternBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val user = v.tag as User

        if (isModeActive) {
            actionListener.onSelectUser(user)
        } else {
            when (v.id) {
                R.id.deleteUserView -> actionListener.onDeleteUser(user)
                else -> actionListener.onUserDetails(user)
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
        val user = v?.tag as User
        isModeActive = true
        actionListener.onMultiSelectModeActive()
        actionListener.onSelectUser(user)
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
        binding.checkBox.setOnClickListener(this)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)

        val context = holder.binding.root.context
        val density = context.resources.displayMetrics.density

        with(holder.binding) {
            val layoutParams =
                userPhotoView.layoutParams as ViewGroup.MarginLayoutParams
            root.setBackgroundResource(
                if (isModeActive) R.drawable.selected_user_background
                else R.drawable.item_background_selector
            )
            layoutParams.marginStart =
                if (isModeActive) (MULTISELECT_MODE_MARGIN * density).toInt()
                else (DEFAULT_MARGIN * density).toInt()
            userPhotoView.layoutParams = layoutParams
            deleteUserView.visibility = if (isModeActive) View.GONE else View.VISIBLE
            checkBox.visibility = if (isModeActive) View.VISIBLE else View.GONE
            checkBox.isChecked = user.isSelected

            holder.itemView.tag = user
            deleteUserView.tag = user
            checkBox.tag = user

            userNameView.text = user.name
            userCareerView.text = user.career
        }

        loadImage(holder.binding.userPhotoView, user.photo)
    }

}




