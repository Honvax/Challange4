package com.alfrsms.challange4.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun registerUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUser(username: String) : UserEntity
}