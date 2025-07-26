package com.example.finaltaskiti.data.repo

import com.example.finaltaskiti.data.local.dao.ContactDao
import com.example.finaltaskiti.data.model.Contact

class ContactsRepository(val contactDao: ContactDao) {
    suspend fun getContacts() : Array<Contact> {
        val list = contactDao.getAll()
        return list
    }

    suspend fun addContact(contact: Contact) {
        contactDao.insertContact(contact)
    }

    suspend fun updateContact(contact: Contact) {
        contactDao.updateContact(contact)
    }

    suspend fun deleteContact(contact: Contact) {
        contactDao.deleteContact(contact)
    }

    suspend fun isExists(number: String) : Boolean {
        return contactDao.isExists(number)
    }
}