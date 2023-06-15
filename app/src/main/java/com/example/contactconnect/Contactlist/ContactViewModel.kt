package com.example.contactconnect.Contactlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

class ContactViewModel : ViewModel() {
    val contactList: MutableLiveData<List<personcontact>> = MutableLiveData()

    fun loadContacts(inputStream: InputStream?) {
        // loading contacts from assets
        viewModelScope.launch {
            val contacts = loadQuoteFromAssets(inputStream)
            contactList.value = contacts
        }
    }

    private suspend fun loadQuoteFromAssets(inputStream: InputStream?): List<personcontact> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val size: Int = inputStream?.available() ?: 0
                val buffer = ByteArray(size)
                inputStream?.read(buffer)
                inputStream?.close()
                val json = String(buffer, Charsets.UTF_8)
                val gson = Gson()
                gson.fromJson(json, Array<personcontact>::class.java).toList()
            } catch (e: Exception) {
                println("empty list is returned")
                emptyList()
            }
        }
}
