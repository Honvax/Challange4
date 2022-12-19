package com.alfrsms.challange4.di

import android.content.Context
import com.alfrsms.challange4.data.LocalRepository
import com.alfrsms.challange4.data.LocalRepositoryImpl
import com.alfrsms.challange4.data.local.AppDatabase
import com.alfrsms.challange4.data.local.note.NoteDao
import com.alfrsms.challange4.data.local.note.NoteDataSource
import com.alfrsms.challange4.data.local.note.NoteDataSourceImpl
import com.alfrsms.challange4.data.local.user.UserDao
import com.alfrsms.challange4.data.local.user.UserDataSource
import com.alfrsms.challange4.data.local.user.UserDataSourceImpl

object ServiceLocator {

    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    fun provideUserDao(context: Context): UserDao {
        return provideAppDatabase(context).userDao
    }

    fun provideUserDataSource(context: Context): UserDataSource {
        return UserDataSourceImpl(provideUserDao(context))
    }

    fun provideNoteDao(context: Context): NoteDao {
        return provideAppDatabase(context).noteDao
    }

    fun provideNoteDataSource(context: Context): NoteDataSource {
        return NoteDataSourceImpl(provideNoteDao(context))
    }

    fun provideServiceLocator(context: Context): LocalRepository {
        return LocalRepositoryImpl(
            provideUserDataSource(context),
            provideNoteDataSource(context)
        )
    }
}