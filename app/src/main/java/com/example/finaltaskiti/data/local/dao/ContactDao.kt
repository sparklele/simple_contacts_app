package com.example.finaltaskiti.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.finaltaskiti.data.model.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    suspend fun getAll(): Array<Contact>

    @Insert
    suspend fun insertContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT EXISTS(SELECT 1 FROM contacts WHERE phoneNumber = :number)")
    suspend fun isExists(number: String): Boolean

}