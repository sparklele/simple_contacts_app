package com.example.finaltaskiti.UI.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finaltaskiti.R
import com.example.finaltaskiti.UI.viewmodel.ContactViewModel
import com.example.finaltaskiti.data.local.database.AppDatabase
import com.example.finaltaskiti.data.model.Contact
import com.example.finaltaskiti.data.repo.ContactsRepository
import com.example.finaltaskiti.databinding.FragmentAddOrEditBinding
import kotlinx.coroutines.launch

class AddOrEditFragment : Fragment() {

    private var _binding: FragmentAddOrEditBinding? = null
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
        _binding = FragmentAddOrEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: AddOrEditFragmentArgs by navArgs()
        val mode = args.mode
        if(mode == "add"){
            binding.titleTV.text = getString(R.string.ADD)
            binding.editBT.text = getString(R.string.action_add)
            binding.editBT.setOnClickListener{
                val name = binding.nameET.text.toString()
                val number = binding.numberET.text.toString()
                if (name.isEmpty()){
                    Toast.makeText(context,"please enter a name",Toast.LENGTH_LONG).show()
                } else if (number.isEmpty()){
                    Toast.makeText(context,"please enter a phone number", Toast.LENGTH_LONG).show()
                } else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val exists = viewModel.isExists(number)
                        if (exists) {
                            Toast.makeText(requireContext(), "Contact already exists!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.addContact(contact = Contact(name, number))
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        } else {
            val oldName = args.name
            val oldNumber = args.number
            binding.titleTV.text = getString(R.string.EDIT)
            binding.editBT.text = getString(R.string.action_edit)
            binding.nameET.setText(oldName)
            binding.numberET.setText(oldNumber)
            binding.editBT.setOnClickListener{
                val name = binding.nameET.text.toString()
                val number = binding.numberET.text.toString()
                if (name.isEmpty()){
                    Toast.makeText(context,"please enter a name",Toast.LENGTH_LONG).show()
                } else if (number.isEmpty()){
                    Toast.makeText(context,"please enter a phone number", Toast.LENGTH_LONG).show()
                } else {
                    if(oldNumber == number)
                        viewModel.updateContact(contact = Contact(name, number))
                    else{
                        viewLifecycleOwner.lifecycleScope.launch {
                            val exists = viewModel.isExists(number)
                            if (exists) {
                                Toast.makeText(requireContext(), "Contact already exists!", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.deleteContact(contact = Contact(oldName, oldNumber))
                                viewModel.addContact(contact = Contact(name, number))
                                findNavController().navigateUp()
                            }
                        }
                    }
                }
            }
        }
        binding.closeBT.setOnClickListener{
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
