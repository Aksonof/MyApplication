package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemUserMultiselectBinding
import com.example.myapplication.databinding.ItemUserNormalBinding
import com.example.myapplication.loadImage
import com.example.myapplication.model.User


private const val VIEW_TYPE_NORMAL = 0
private const val VIEW_TYPE_MULTISELECT = 1

class UsersAdapter(private val actionListener: UserActionListener) :
    ListAdapter<User, RecyclerView.ViewHolder>(MyItemCallback()) {

    private var isModeActive: Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    fun changeModeStatus(state: Boolean) {
        isModeActive = state
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isModeActive) VIEW_TYPE_MULTISELECT else VIEW_TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_MULTISELECT -> {
                val binding = ItemUserMultiselectBinding.inflate(inflater, parent, false)
                MultiSelectViewHolder(binding)
            }

            else -> {
                val binding = ItemUserNormalBinding.inflate(inflater, parent, false)
                NormalViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)
        when (holder) {
            is NormalViewHolder -> holder.onBind(user)
            is MultiSelectViewHolder -> holder.onBind(user)
        }
    }

    inner class NormalViewHolder(
        private val binding: ItemUserNormalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: User) {
            with(binding) {
                userNameView.text = item.name
                userCareerView.text = item.career

                itemView.setOnLongClickListener {
                    changeModeStatus(true)
                    actionListener.onMultiSelectModeActive()
                    actionListener.onSelectUser(item)
                    true
                }
                itemView.setOnClickListener {
                    actionListener.onUserDetails(item)
                }
                deleteUserView.setOnClickListener {
                    actionListener.onDeleteUser(item)
                }
            }
            loadImage(binding.userPhotoView, item.photo)
        }
    }

    inner class MultiSelectViewHolder(
        private val binding: ItemUserMultiselectBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: User) {
            with(binding) {
                userNameView.text = item.name
                userCareerView.text = item.career
                checkBox.isChecked = item.isSelected

                itemView.setOnClickListener {
                    actionListener.onSelectUser(item)
                }
                checkBox.setOnClickListener {
                    actionListener.onSelectUser(item)
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
}
