package com.example.contactconnect.Contactlist.SendMessage

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface TwilioApiService {
    @FormUrlEncoded
    @POST("Accounts/{ACCOUNT_SID}/SMS/Messages")
    fun sendMessage(
        @Path("ACCOUNT_SID") accountSID: String,
        @Header("Authorization") signature: String,
        @FieldMap metadata: Map<String, String>
    ): Call<ResponseBody>
}
