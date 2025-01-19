package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemAddContactBinding
import com.example.myapplication.loadImage
import com.example.myapplication.model.User

class AddContactsAdapter(
    private val onDetailContactClick: (User) -> Unit,
    private val onAddContactClick: (User) -> Unit,
    private val isContactAdded: (User) -> Boolean
) : ListAdapter<User, RecyclerView.ViewHolder>(MyItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddContactBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contact = getItem(position)
        val isAdded = isContactAdded(contact)

        holder.itemView.setOnClickListener {
            onDetailContactClick(contact)
        }
        (holder as ContactViewHolder).bind(contact, isAdded)
    }

    inner class ContactViewHolder(
        private val binding: ItemAddContactBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User, isAdded: Boolean) {
            with(binding) {
                userNameView.text = item.name
                userCareerView.text = item.career
                loadImage(userPhotoView, item.imageUrl)

                if (isAdded) {
                    addContactView.setVisibility(View.GONE)
                    addContactTextView.visibility = View.GONE
                    checked.setVisibility(View.VISIBLE)
                } else {
                    addContactView.setVisibility(View.VISIBLE)
                    addContactTextView.visibility = View.VISIBLE
                    checked.setVisibility(View.GONE)

                    addContactView.setOnClickListener { onAddContactClick(item) }
                }
            }
        }
    }


    class MyItemCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

}