package com.example.contactconnect.History

import SentMessageViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.contactconnect.Contactlist.ContactListadapter
import com.example.contactconnect.Contactlist.ContactViewModel
import com.example.contactconnect.Message.SendMessage.Messagedata
import com.example.contactconnect.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Message_main : Fragment() {

    lateinit var allSentMessages :List<Messagedata>
    lateinit var recyclerView :RecyclerView
     lateinit var MessageViewModel: SentMessageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MessageViewModel=ViewModelProvider(this).get(SentMessageViewModel::class.java)
        GlobalScope.launch {
            val database = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app_database").build()
            val sentMessageDao = database.sentMessageDao()
            allSentMessages = sentMessageDao.getAllSentMessages()
            displayMessage()
        }

            // Observe the contactList LiveData and update the variable when it changes
            MessageViewModel.sentMessages.observe(viewLifecycleOwner) { contacts ->
                allSentMessages = contacts
                displayMessage()
            }



    }

    private fun displayMessage() {
        if (allSentMessages.isNotEmpty()) {
            for (contact in allSentMessages) {
                println(contact.F_name.toString())
            }
            recyclerView = view!!.findViewById(R.id.recyclerMessages)
            val adapter = MessageAdapter(allSentMessages)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        } else {
            println("The list is empty.")
        }
    }


}