package com.example.contactconnect.Contactlist


import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.contactconnect.Contactlist.SendMessage.createTwilioApiService
import com.example.contactconnect.R
import kotlinx.coroutines.launch
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class contactinfo_activity : AppCompatActivity() {
    val ACCOUNT_SID = "AC389ec42f42ee2e630c1d72ec9fe9990d"
    val AUTH_TOKEN = "b710f4c70b5087fe107cafe64a158321"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactinfo_fragment)

        val firstname:TextView = findViewById(R.id.textViewFirstName)
        val lastname :TextView= findViewById(R.id.textViewLastName)
        val Mobileno :TextView= findViewById(R.id.textViewMobileNumber)
        val messagesend :EditText=findViewById(R.id.message1)
        val sendbutton:Button=findViewById(R.id.sendbutton)
        firstname.setText(intent.getStringExtra("firstName") )
        lastname.setText(intent.getStringExtra("lastname") )
        Mobileno.setText(intent.getStringExtra("mobileNumber") )
        val otp:String=generateOTP()
        val messagestring ="Hi ${intent.getStringExtra("firstName")}  Your OTP is:- $otp"
        messagesend.setText(messagestring)
        sendbutton.setOnClickListener()
        {
            sendSMS(Mobileno.toString(),messagestring)

        }

    }




    fun sendSMS(phoneNumber: String, message: String) {
        val twilioService = createTwilioApiService()

        val accountSid = "AC389ec42f42ee2e630c1d72ec9fe9990d"
        val twilioPhoneNumber = "+14026966904"

        val call = twilioService.sendSMS(accountSid,phoneNumber,twilioPhoneNumber,message)
        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    println("SMS sent successfully!")
                } else {
                    println("Failed to send SMS: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Failed to send SMS: ${t.message}")
            }
        })
    }



    private fun generateOTP(): String {
        // Generate a random 6-digit number for OTP
        val otp = (100000..999999).random()
        return otp.toString()
    }
}

