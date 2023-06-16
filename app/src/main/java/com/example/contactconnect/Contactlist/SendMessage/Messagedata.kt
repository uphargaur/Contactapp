package com.example.contactconnect.Contactlist.SendMessage


data class Messagedata(
    val to: String, // The phone number to send the SMS to
    val from: String, // Your Twilio phone number
    val body: String // The message body
)
