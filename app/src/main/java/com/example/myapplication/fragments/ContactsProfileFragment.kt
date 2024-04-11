package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentContactsProfileBinding

class ContactsProfileFragment : Fragment() {

    private lateinit var binding: FragmentContactsProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}