package com.example.contactconnect.Contactlist.SendMessage

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface TwilioService {
    @FormUrlEncoded
    @POST("Accounts/AC389ec42f42ee2e630c1d72ec9fe9990d/Messages/SMaa680420a3d3945c5da6e3f998a9b0a7.json")
    fun sendOtp(
        @Field("+14026966904") from: String,
        @Field("+917988204539") to: String,
        @Field("Body") body: String
    ): Call<Any>
}