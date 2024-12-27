package com.example.contactappcompose.data_layer.di

import android.app.Application
import androidx.room.Room
import com.example.contactappcompose.data_layer.database.ContactDatabase
import com.example.contactappcompose.data_layer.repoImp.RepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContactDatabase(application: Application): ContactDatabase {
        return Room.databaseBuilder(application, ContactDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideContactRepo(database: ContactDatabase): RepoImp{
        return RepoImp(database)
    }
}