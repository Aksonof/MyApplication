package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.AddContactsAdapter
import com.example.myapplication.databinding.FragmentAddContactsBinding
import com.example.myapplication.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactsFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentAddContactsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AddContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {


        userViewModel.cutContacts()
        userViewModel.allContactsLiveData.observe(viewLifecycleOwner) { contacts ->
            adapter.submitList(contacts)
            Log.d("qwe123", "Size ${contacts.size}  ${contacts[130]}")
        }

        userViewModel.addedContacts.observe(viewLifecycleOwner) {

            adapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {

        adapter = AddContactsAdapter(
            onDetailContactClick = { contact ->

            },
            onAddContactClick = { contact ->
                userViewModel.addContact(contact)
            },
            isContactAdded = { contact -> userViewModel.isContactAdded(contact) }
        )

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}