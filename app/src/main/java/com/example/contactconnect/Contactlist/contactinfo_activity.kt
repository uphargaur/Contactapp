package com.example.contactconnect.Contactlist


import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.contactconnect.History.AppDatabase
import com.example.contactconnect.Message.SendMessage.Messagedata
import com.example.contactconnect.Message.SendMessage.createTwilioApiService
import com.example.contactconnect.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import java.util.*


class contactinfo_activity : AppCompatActivity() {
    val ACCOUNT_SID = "AC389ec42f42ee2e630c1d72ec9fe9990d"
    val AUTH_TOKEN = "25511ae88f290fd6421c3c2aaa37c833"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactinfo_fragment)

        val firstname:TextView = findViewById(R.id.textViewFirstName)
        val lastname :TextView= findViewById(R.id.textViewLastName)
        val Mobileno :TextView= findViewById(R.id.textViewMobileNumber)
        val messagesend :EditText=findViewById(R.id.message1)
        val sendbutton:Button=findViewById(R.id.sendbutton)
        //reterving all the text from bundle
        val firstnametext: String = intent.getStringExtra("firstName").toString()
        val lastnametext: String = intent.getStringExtra("lastname").toString()
        val Mobilenotext: String = intent.getStringExtra("mobileNumber").toString()
        val otp:String=generateOTP()
        //setting up text to ui
        firstname.text=firstnametext
        lastname.text=lastnametext
        Mobileno.text=Mobilenotext
        //setting up other stuff
        val messagestring ="Hi ${intent.getStringExtra("firstName")} ! \n Your OTP is:- $otp \n For your Mobile No $Mobilenotext"
        messagesend.setText(messagestring)
        println("checking in  otp activity $Mobilenotext   $firstnametext")
        val savedMessage= Messagedata(phoneNumber=Mobilenotext ,message=messagestring,F_name=firstnametext,L_name=lastnametext,Otp=otp,timestamp= SimpleDateFormat("HH:mm dd.MM.yyyy").format(Date())
        )
        sendbutton.setOnClickListener()
        {
            sendSMS(Mobileno.toString(),messagestring,savedMessage)
        }

    }




    fun sendSMS(phoneNumber: String, message: String, Savedmessage :Messagedata) {
        val twilioService = createTwilioApiService()

        val accountSid = "AC389ec42f42ee2e630c1d72ec9fe9990d"
        val twilioPhoneNumber = "+14026966904"

        val call = twilioService.sendSMS(accountSid,"+917988204539",twilioPhoneNumber,message)
        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    println("SMS sent successfully!")
//                    nowletssavethisintoadatabase
                    Toast.makeText(this@contactinfo_activity,"Successfully sent otp",Toast.LENGTH_SHORT).show()

                    val context = this@contactinfo_activity // Store the activity context in a variable
                    GlobalScope.launch {
                        val database = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
                        database.sentMessageDao().insertSentMessage(Savedmessage)
                    }
                    finish()

                } else {
                    println(phoneNumber)
                    println("Failed to send SMS: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Failed to send SMS: ${t.message}")
                println(phoneNumber)
            }
        })
    }



    private fun generateOTP(): String {
        // Generate a random 6-digit number for OTP
        val otp = (100000..999999).random()
        return otp.toString()
    }
}

