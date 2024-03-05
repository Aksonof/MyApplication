package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.User
import com.example.myapplication.model.UsersListener
import com.example.myapplication.model.UsersManager

class UsersListViewModel(private val usersManager: UsersManager) : ViewModel() {


    private val _usersLiveData = MutableLiveData<List<User>>()
    val usersLiveData = _usersLiveData

    private val listener: UsersListener = {
        _usersLiveData.value = it
    }

    init {
        loadUsers()
    }

    private fun loadUsers() {
        usersManager.addListener(listener)
    }

    fun deleteUser(user: User) {
        usersManager.deleteUser(user)
    }

    fun restoreUser(user: User) {
        usersManager.restoreUser(user)
    }

    fun addUser(user: User) {
        usersManager.addUser(user)
    }

    override fun onCleared() {
        super.onCleared()
        usersManager.removeListener(listener)
    }
}