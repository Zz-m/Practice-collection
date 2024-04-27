package com.demo.bleapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): List<Users>

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun getUserById(id: Int): Users

    @Insert
    fun insertUser(user: Users)

    @Update
    fun updateUser(user: Users)

    @Delete
    fun deleteUser(user: Users)

    @Query("SELECT COUNT(*) FROM user_table")
    fun getUserCount(): Int
}