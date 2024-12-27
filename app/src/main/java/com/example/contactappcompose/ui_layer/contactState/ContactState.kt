package com.example.contactappcompose.ui_layer.contactState

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.contactappcompose.data_layer.contactTable.Contact

data class ContactState(
    val contactList: List<Contact> = emptyList(),
    val id: MutableState<Int?> = mutableStateOf(null),
    val name: MutableState<String> = mutableStateOf(""),
    val phoneNumber: MutableState<String> = mutableStateOf(""),
    val email: MutableState<String> = mutableStateOf(""),
    val image : MutableState<ByteArray?> = mutableStateOf(null)
)