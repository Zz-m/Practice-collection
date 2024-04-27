package com.demo.bleapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class], version = 1)
abstract class MyDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: MyDatabase? = null

        fun getDatabaseAccess(context: Context): MyDatabase? {
            if (instance == null) {
                synchronized(MyDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "user.db").createFromAsset("user.db").build()
                }
            }
            return instance
        }
    }
}