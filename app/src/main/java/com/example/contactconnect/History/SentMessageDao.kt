package com.example.contactconnect.History

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.contactconnect.Message.SendMessage.Messagedata

@Dao
interface SentMessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSentMessage(sentMessage: Messagedata)

    @Query("SELECT * FROM sent_messages")
    fun getAllSentMessages(): List<Messagedata>
}
