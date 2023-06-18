package com.example.contactconnect.Message.SendMessage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sent_messages")
data class Messagedata(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val phoneNumber: String,
    val message: String,
    val F_name :String,
    val L_name :String,
    val Otp : String,
    val timestamp: String
)
