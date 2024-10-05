package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Contact
import com.example.myapplication.model.ContactRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ContactsViewModel(private val contactRepository: ContactRepository) : ViewModel() {

    private val _usersLiveData = MutableLiveData<List<Contact>>()
    val usersLiveData: LiveData<List<Contact>> = _usersLiveData

    init {
        viewModelScope.launch {
            contactRepository.getUsers().collectLatest { usersList ->
                _usersLiveData.value = usersList
            }
        }
    }

    fun deleteUser(contact: Contact) {
        contactRepository.deleteUser(contact)
    }

    fun restoreUser(listWithDeletedContact: List<Contact>?) {
        contactRepository.restoreUser(listWithDeletedContact)
    }

    fun addUser(name: String, career: String) {

        val newContact = Contact(
            name = name,
            career = career,
            photo = "",
            id = System.currentTimeMillis(),
            isSelected = false
        )
        contactRepository.addUser(newContact)
    }

    fun selectUser(contact: Contact) {
        contactRepository.selectUser(contact)
    }

    fun isAnyContactSelect(): Boolean {
        return contactRepository.isAnyContactSelect()
    }

    fun deleteSelectedContacts() {
        contactRepository.deleteSelectedContacts()
    }

}