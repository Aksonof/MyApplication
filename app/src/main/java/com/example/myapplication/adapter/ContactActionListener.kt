package com.example.myapplication.adapter

import com.example.myapplication.model.Contact

interface ContactActionListener {
    fun onDeleteUser(contact: Contact)
    fun onUserDetails(contact: Contact)
    fun onSelectUser(contact: Contact)
    fun onMultiSelectModeActive()
}