package com.example.contactconnect.Contactlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactconnect.R
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream


class ContactFragment : Fragment() {

    private lateinit var contactViewModel: ContactViewModel
    private var contactList: List<personcontact> = emptyList()
    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        lifecycleScope.launch {
            try {
                val inputStream: InputStream? = requireContext().assets.open("contact.json")
                contactViewModel.loadContacts(inputStream)
            } catch (e: IOException) {
                // Handle the exception
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         recyclerView = view.findViewById(R.id.recyclerViewContacts)

        // Observe the contactList LiveData and update the variable when it changes
        contactViewModel.contactList.observe(viewLifecycleOwner) { contacts ->
            contactList = contacts
            displayContacts()
        }
    }

    private fun displayContacts() {
        if (contactList.isNotEmpty()) {
            for (contact in contactList) {
                println(contact.firstName.toString())
            }
            val adapter = ContactListadapter(contactList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
