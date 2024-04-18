package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.AddUserDialogFragment
import com.example.myapplication.AddUserDialogListener
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.adapter.UserActionListener
import com.example.myapplication.adapter.UsersAdapter
import com.example.myapplication.databinding.FragmentMyContactsBinding
import com.example.myapplication.model.User
import com.example.myapplication.viewModel.UsersListViewModel
import com.example.myapplication.viewModel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class MyContactsFragment : Fragment() {

    private var _binding: FragmentMyContactsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UsersAdapter
    private lateinit var viewModel: UsersListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewModelFactory = ViewModelFactory(requireContext().applicationContext as App)
        viewModel = ViewModelProvider(this, viewModelFactory)[UsersListViewModel::class.java]

        adapter = UsersAdapter(object : UserActionListener {
            override fun onDeleteUser(user: User) {
                val listBeforeDeletedContact = viewModel.usersLiveData.value
                viewModel.deleteUser(user)

                Snackbar.make(
                    requireView(),
                    getString(R.string.contact_has_been_removed),
                    Snackbar.LENGTH_LONG
                ).setAction(
                    getString(R.string.cancel)
                ) { viewModel.restoreUser(listBeforeDeletedContact) }
                    .setActionTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.my_light_primary
                        )
                    )
                    .show()
            }

            override fun onUserDetails(user: User) {
                findNavController().navigate(R.id.action_myContactsFragment_to_contactsProfileFragment)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        viewModel.usersLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it.toMutableList())
        }
        binding.addContactTextView.setOnClickListener {
            showAddUserDialog()
        }

    }


    private fun showAddUserDialog() {
        val dialogFragment = AddUserDialogFragment(object : AddUserDialogListener {
            override fun onDataEntered(data: Bundle) {
                val userName = data.getString("userName")
                val userCareer = data.getString("userCareer")
                if (!userCareer.isNullOrBlank() && !userName.isNullOrBlank()) {
                    viewModel.addUser(User(userName, userCareer, "", 0))
                    binding.recyclerView.smoothScrollToPosition(0)
                }
            }
        })
        dialogFragment.show(requireFragmentManager(), AddUserDialogFragment.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}