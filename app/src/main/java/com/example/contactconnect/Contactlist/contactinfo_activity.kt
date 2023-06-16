package com.example.contactconnect.Contactlist


import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.contactconnect.Contactlist.SendMessage.TwilioService
import com.example.contactconnect.R
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
            val twilioService = createTwilioService()
            val fromPhoneNumber ="+14026966904"
            val toPhoneNumber = Mobileno.toString()

            val call = twilioService.sendOtp(
                fromPhoneNumber,
                toPhoneNumber,
                messagestring
            )
            call.enqueue(object : retrofit2.Callback<Any> {
                override fun onResponse(call: Call<Any>, response: retrofit2.Response<Any>) {
                    if (response.isSuccessful) {
                        println("OTP sent successfully")
                    } else {
                        println("Failed to send OTP: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    println("Failed to send OTP: ${t.message}")
                }
            })



        }


    }

    private fun createTwilioService(): TwilioService {
        val credentials = Credentials.basic(ACCOUNT_SID, AUTH_TOKEN)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.twilio.com/2010-04-01/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp3.OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().header("Authorization", credentials).build()
                chain.proceed(request)
            }.build())
            .build()

        return retrofit.create(TwilioService::class.java)
    }

    private fun generateOTP(): String {
        // Generate a random 6-digit number for OTP
        val otp = (100000..999999).random()
        return otp.toString()
    }
}

