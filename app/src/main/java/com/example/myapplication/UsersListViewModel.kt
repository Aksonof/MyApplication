package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.User
import com.example.myapplication.model.UsersManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UsersListViewModel(private val usersManager: UsersManager) : ViewModel() {


    private val _usersLiveData = MutableLiveData<List<User>>()
    val usersLiveData = _usersLiveData

    init {
        viewModelScope.launch {
            usersManager.getUsers().collectLatest { usersList ->
                _usersLiveData.value = usersList
            }
        }
    }


    fun deleteUser(user: User) {
        usersManager.delete(user)
    }

    /*fun restoreUser(user: User) {
        usersManager.restoreUser(user)
    }

    fun addUser(user: User) {
        usersManager.addUser(user)
    }*/

}