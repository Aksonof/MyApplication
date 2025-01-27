package com.example.myapplication.adapter

import com.example.myapplication.model.Contact
import com.example.myapplication.model.User

interface ContactActionListener {
    fun onDeleteUser(contact: User)
    fun onUserDetails(contact: User)
    fun onSelectUser(contact: User)
    fun onMultiSelectModeActive()
}