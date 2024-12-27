package com.example.contactappcompose.data_layer.repoImp

import com.example.contactappcompose.data_layer.contactTable.Contact
import com.example.contactappcompose.data_layer.database.ContactDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class RepoImp @Inject constructor(private val database: ContactDatabase) {

    suspend fun addContact(contact: Contact) {
        database.contactDao().addContact(contact)
    }

    suspend fun deleteContact(contact: Contact) {
        database.contactDao().deleteContact(contact)
    }

    fun getAllContacts(): Flow<List<Contact>> {
        return database.contactDao().getAllContacts()
    }

}