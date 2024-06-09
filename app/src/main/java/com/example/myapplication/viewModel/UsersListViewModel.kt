package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.User
import com.example.myapplication.model.UsersManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UsersListViewModel(private val usersManager: UsersManager) : ViewModel() {

    private val _usersLiveData = MutableLiveData<List<User>>()
    val usersLiveData: LiveData<List<User>> = _usersLiveData

    init {
        viewModelScope.launch {
            usersManager.getUsers().collectLatest { usersList ->
                _usersLiveData.value = usersList
            }
        }
    }


    fun deleteUser(user: User) {
        usersManager.deleteUser(user)
    }

    fun restoreUser(listWithDeletedUser: List<User>?) {
        usersManager.restoreUser(listWithDeletedUser)
    }

    fun addUser(user: User) {
        usersManager.addUser(user)
    }

    fun selectUser(user: User) {
        usersManager.selectUser(user)
    }

    fun isAnyContactSelect(): Boolean {
        return usersManager.isAnyContactSelect()
    }

    fun deleteSelectedContacts() {
        usersManager.deleteSelectedContacts()
    }

}