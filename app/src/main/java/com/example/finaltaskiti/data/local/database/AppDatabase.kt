package com.example.finaltaskiti.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finaltaskiti.data.local.dao.ContactDao
import com.example.finaltaskiti.data.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao() : ContactDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            return INSTANCE?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "contacts_database"
            )
                .fallbackToDestructiveMigration(false)
                .build()
                .also { INSTANCE = it }
        }
    }
}