package com.example.contactconnect.History

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactconnect.Message.SendMessage.Messagedata

@Database(entities = [Messagedata::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sentMessageDao(): SentMessageDao
}