package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _isModeActive = MutableLiveData(false)
    private val isModeActive: LiveData<Boolean> get() = _isModeActive

    fun changeModeStatus(state: Boolean) {
        _isModeActive.value = state
    }

    class UsersViewHolder(
        private val binding: UserPatternBinding,
        private val actionListener: UserActionListener,
        private val isModeActive: LiveData<Boolean>,
        private val setModeActive: (Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: User) {
            val context = binding.root.context
            val density = context.resources.displayMetrics.density

            isModeActive.observeForever { active ->
                with(binding) {
                    val layoutParams =
                        userPhotoView.layoutParams as ViewGroup.MarginLayoutParams
                    root.setBackgroundResource(
                        if (active) R.drawable.selected_user_background
                        else R.drawable.item_background_selector
                    )
                    layoutParams.marginStart =
                        if (active) (MULTISELECT_MODE_MARGIN * density).toInt()
                        else (DEFAULT_MARGIN * density).toInt()
                    userPhotoView.layoutParams = layoutParams
                    deleteUserView.visibility = if (active) View.GONE else View.VISIBLE
                    checkBox.visibility = if (active) View.VISIBLE else View.GONE
                    checkBox.isChecked = item.isSelected

                    userNameView.text = item.name
                    userCareerView.text = item.career

                    itemView.setOnLongClickListener {
                        setModeActive(true)
                        actionListener.onMultiSelectModeActive()
                        actionListener.onSelectUser(item)
                        true
                    }
                    itemView.setOnClickListener {
                        if (active) {
                            actionListener.onSelectUser(item)
                        } else {
                            actionListener.onUserDetails(item)
                        }
                    }
                    deleteUserView.setOnClickListener {
                        actionListener.onDeleteUser(item)
                    }
                }
                loadImage(binding.userPhotoView, item.photo)
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
        return UsersViewHolder(binding, actionListener, isModeActive, this::changeModeStatus)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)
        holder.onBind(user)
    }
}
