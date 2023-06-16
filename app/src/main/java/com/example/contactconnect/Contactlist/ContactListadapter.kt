package com.example.contactconnect.Contactlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactconnect.R

interface OnItemClickListener {
    fun onItemClick(item: personcontact)
}

class ContactListadapter(private val contactsList: List<personcontact>) :
    RecyclerView.Adapter<ContactListadapter.exampleview>() {

    class exampleview( ItemView : View) :RecyclerView.ViewHolder(ItemView)
    {
        val Firstname: TextView =itemView.findViewById(R.id.textViewFirstName)
        val lastname: TextView = itemView.findViewById(R.id.textViewLastName)
        val mobileno: TextView = itemView.findViewById(R.id.textViewMobileNumber)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): exampleview {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_contact_list,parent,false)
        return exampleview(itemView)
    }

    override fun onBindViewHolder(holder: exampleview, position: Int) {
        val currentitem=contactsList[position]
        holder.Firstname.text=currentitem.firstName
        holder.lastname.text=currentitem.lastName
        holder.mobileno.text=currentitem.mobileNumber
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, contactinfo_activity::class.java).apply {
                putExtra("firstName", currentitem.firstName)
                putExtra("lastname", currentitem.lastName)
                putExtra("mobileNumber", currentitem.mobileNumber)
            }
            context.startActivity(intent)
        }
        println(currentitem.firstName)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }


}

//10 july