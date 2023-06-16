package com.example.contactconnect.Contactlist


import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.contactconnect.Contactlist.SendMessage.TwilioApiService
import com.example.contactconnect.R
import kotlinx.coroutines.launch
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
            val body = messagestring
            val from ="+14026966904"
            val to = Mobileno.toString()
            val base64EncodedCredentials = "Basic " + Base64.encodeToString(
                "$ACCOUNT_SID:$AUTH_TOKEN".toByteArray(),
                Base64.NO_WRAP
            )
            val data: MutableMap<String, String> = HashMap()
            data["From"] = from
            data["To"] = to
            data["Body"] = body
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.twilio.com/2010-04-01/")
                .build()
            val twilioApi = retrofit.create(TwilioApiService::class.java)
            twilioApi.sendMessage(ACCOUNT_SID, base64EncodedCredentials, data)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            // API request successful
                            println("Response successful")
                        } else {
                            // API request failed
                            println("Response failed")
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        // API request failed
                        println("Request failed: ${t.message}")
                    }
                })



        }


    }


    private fun generateOTP(): String {
        // Generate a random 6-digit number for OTP
        val otp = (100000..999999).random()
        return otp.toString()
    }
}

