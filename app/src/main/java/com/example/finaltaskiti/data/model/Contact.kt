package com.example.finaltaskiti.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    val name: String,
    @PrimaryKey(autoGenerate = false) val phoneNumber: String
)
