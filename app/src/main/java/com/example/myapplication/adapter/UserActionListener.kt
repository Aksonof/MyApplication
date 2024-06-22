package com.example.myapplication.adapter

import com.example.myapplication.model.User

interface UserActionListener {
    fun onDeleteUser(user: User)
    fun onUserDetails(user: User)
}