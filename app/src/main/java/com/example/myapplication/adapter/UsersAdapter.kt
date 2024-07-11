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

class UsersAdapter(private val actionListener: UserActionListener) :
    ListAdapter<User, UsersAdapter.UsersViewHolder>(MyItemCallback()) {

    private var isModeActive: Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    fun changeModeStatus(state: Boolean) {
        isModeActive = state
        notifyDataSetChanged()
    }

    inner class UsersViewHolder(
        private val binding: UserPatternBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: User) {
            val context = binding.root.context
            val density = context.resources.displayMetrics.density

            with(binding) {
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
                checkBox.isChecked = item.isSelected

                userNameView.text = item.name
                userCareerView.text = item.career

                itemView.setOnLongClickListener {
                    changeModeStatus(true)
                    actionListener.onMultiSelectModeActive()
                    actionListener.onSelectUser(item)
                    true
                }
                itemView.setOnClickListener {
                    if (isModeActive) {
                        actionListener.onSelectUser(item)
                    } else {
                        actionListener.onUserDetails(item)
                    }
                }
                checkBox.setOnClickListener {
                    actionListener.onSelectUser(item)
                }
                deleteUserView.setOnClickListener {
                    actionListener.onDeleteUser(item)
                }
            }
            loadImage(binding.userPhotoView, item.photo)
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
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)
        holder.onBind(user)
    }
}
