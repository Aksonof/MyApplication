package com.example.myapplication

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.UserPatternBinding

class UsersAdapter: RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {



    class UsersViewHolder (val binding: UserPatternBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}