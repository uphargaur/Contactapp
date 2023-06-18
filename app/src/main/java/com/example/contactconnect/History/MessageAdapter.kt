package com.example.contactconnect.History

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactconnect.Message.SendMessage.Messagedata
import com.example.contactconnect.R


class MessageAdapter(private val contactsList: List<Messagedata>) :
    RecyclerView.Adapter<MessageAdapter.exampleview>() {

    class exampleview( ItemView : View) :RecyclerView.ViewHolder(ItemView)
    {
        val timestamp: TextView = itemView.findViewById(R.id.message)
        val message: TextView = itemView.findViewById(R.id.timestamp)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): exampleview {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_message_history,parent,false)
        return exampleview(itemView)
    }

    override fun onBindViewHolder(holder: exampleview, position: Int) {
        val currentitem=contactsList[position]
        holder.timestamp.text=currentitem.timestamp
        holder.message.text=currentitem.message

    }

    override fun getItemCount(): Int {
        return contactsList.size
    }


}
