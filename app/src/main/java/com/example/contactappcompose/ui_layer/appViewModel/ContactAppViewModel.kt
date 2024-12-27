package com.example.contactappcompose.ui_layer.appViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactappcompose.data_layer.contactTable.Contact
import com.example.contactappcompose.data_layer.repoImp.RepoImp
import com.example.contactappcompose.ui_layer.contactState.ContactState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactAppViewModel @Inject constructor(private val repoImp: RepoImp) : ViewModel() {

    private val contactList = repoImp.getAllContacts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ContactState())

    val state = combine(_state, contactList) { _state, contactList ->
        _state.copy(contactList = contactList)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val filteredContacts = combine(contactList, searchQuery) { contacts, query ->
        if (query.isEmpty()) {
            contacts
        } else {
            contacts.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.phoneNumber.contains(query)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addContact() {
        viewModelScope.launch {
            repoImp.addContact(
                Contact(
                    id = state.value.id.value,
                    name = state.value.name.value,
                    phoneNumber = state.value.phoneNumber.value,
                    email = state.value.email.value,
                    image = state.value.image.value
                )
            )
        }
    }

    fun deleteContact() {
        viewModelScope.launch {
            repoImp.deleteContact(
                Contact(
                    id = state.value.id.value,
                    name = state.value.name.value,
                    phoneNumber = state.value.phoneNumber.value,
                    email = state.value.email.value,
                    image = state.value.image.value
                )
            )
        }
    }


}
