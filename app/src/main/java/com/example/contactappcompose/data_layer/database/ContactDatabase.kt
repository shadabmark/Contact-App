package com.example.contactappcompose.data_layer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactappcompose.data_layer.contactDao.ContactDao
import com.example.contactappcompose.data_layer.contactTable.Contact

@Database(entities = [Contact::class], version = 2, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

}