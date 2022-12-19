package com.alfrsms.challange4.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alfrsms.challange4.data.local.note.NoteDao
import com.alfrsms.challange4.data.local.note.NoteEntity
import com.alfrsms.challange4.data.local.user.UserDao
import com.alfrsms.challange4.data.local.user.UserEntity


@Database(entities = [UserEntity::class, NoteEntity::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val noteDao: NoteDao

    companion object {

        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}