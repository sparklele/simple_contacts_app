package com.example.finaltaskiti.UI.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finaltaskiti.UI.adapter.ContactsAdapter
import com.example.finaltaskiti.UI.viewmodel.ContactViewModel
import com.example.finaltaskiti.data.local.database.AppDatabase
import com.example.finaltaskiti.data.model.Contact
import com.example.finaltaskiti.data.repo.ContactsRepository
import com.example.finaltaskiti.databinding.FragmentShowAllContactsBinding

class ShowAllContactsFragment: Fragment() {
    private lateinit var adapter: ContactsAdapter
    private var _binding: FragmentShowAllContactsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = AppDatabase.getInstance(requireContext()).contactDao()
                val repo = ContactsRepository(dao)
                return ContactViewModel(repo) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowAllContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = binding.contactsRV
        rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = ContactsAdapter(
            emptyArray(),
            { contact: Contact ->
                val action = ShowAllContactsFragmentDirections.actionShowAllContactsFragmentToAddOrEditFragment(
                    "edit",contact.name,contact.phoneNumber
                )
                findNavController().navigate(action)
            },
            { contact: Contact ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Contact")
                    .setMessage("Are you sure you want to delete ${contact.name}?")
                    .setPositiveButton("Delete") { _, _ ->
                        viewModel.deleteContact(contact)
                        viewModel.getContacts()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        )
        rv.adapter = adapter
        viewModel.getContacts()
        viewModel.contacts.observe(viewLifecycleOwner)
        { contacts ->
            adapter.setContacts(contacts)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}