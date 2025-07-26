package com.example.finaltaskiti.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finaltaskiti.R
import com.example.finaltaskiti.data.model.Contact
import com.example.finaltaskiti.databinding.ContactListItemBinding

class ContactsAdapter(private var contacts: Array<Contact>,
                      private val onClick: (Contact) -> Unit,
                      private val onLongClick: ((Contact) -> Unit)? = null):
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
        class ContactViewHolder(val binding: ContactListItemBinding):
                RecyclerView.ViewHolder(binding.root)

    fun setContacts(data: Array<Contact>){
        contacts = data
        notifyDataSetChanged()
    }

    private val bear_images: MutableList<Int> = mutableListOf(R.drawable.cute_bear_icon, R.drawable.cute_bear_icon_two,
        R.drawable.cute_bear_icon_three, R.drawable.cute_bear_icon_four, R.drawable.cute_bear_icon_five)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.binding.contactName.text = contact.name
        holder.binding.root.setOnClickListener {
            onClick(contact)
        }
        holder.binding.root.setOnLongClickListener{
            onLongClick?.invoke(contact)
            true
        }
        holder.binding.circleImage.setImageResource(bear_images.random())
    }

    override fun getItemCount(): Int = contacts.size
}