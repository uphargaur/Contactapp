package com.example.contactconnect.Message.SendMessage

import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TwilioApiService {
    @FormUrlEncoded
    @POST("2010-04-01/Accounts/{accountSid}/Messages.json")
    fun sendSMS(
        @Path("accountSid") accountSid: String,
        @Field("To") toPhoneNumber: String,
        @Field("From") fromPhoneNumber: String,
        @Field("Body") message: String
    ): Call<Void>
}
fun createTwilioApiService(): TwilioApiService {
    val accountSid ="AC389ec42f42ee2e630c1d72ec9fe9990d"
    val authToken = "25511ae88f290fd6421c3c2aaa37c833"

    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", Credentials.basic(accountSid, authToken))
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.twilio.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(TwilioApiService::class.java)
}
