package com.example.contactappcompose.data_layer.contactTable

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val image: ByteArray?
)