package com.example.finaltaskiti.UI.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaltaskiti.data.model.Contact
import com.example.finaltaskiti.data.repo.ContactsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class ContactViewModel(val repository: ContactsRepository): ViewModel() {
    private val _contacts = MutableLiveData<Array<Contact>>()

    val contacts: LiveData<Array<Contact>>
        get() = _contacts

    fun getContacts(){
        viewModelScope.launch {
            _contacts.value = repository.getContacts()
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            repository.addContact(contact)
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }

    suspend fun isExists(number: String): Boolean {
        return repository.isExists(number)
    }

}