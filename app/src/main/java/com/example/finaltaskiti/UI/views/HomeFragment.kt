package com.example.finaltaskiti.UI.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finaltaskiti.databinding.FragmentHomeBinding

class HomeFragment:Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.AddBT.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAddOrEditFragment(
                "add", "", ""
            )
            findNavController().navigate(action)
        }
        binding.ShowAllBT.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToShowAllContactsFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
