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
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.adapter.ContactActionListener
import com.example.myapplication.adapter.ContactsAdapter
import com.example.myapplication.databinding.FragmentMyContactsBinding
import com.example.myapplication.model.Contact
import com.example.myapplication.setVisibility
import com.example.myapplication.viewModel.ContactsViewModel
import com.example.myapplication.viewModel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

private const val DEFAULT_MARGIN = 50
private const val MULTISELECT_MODE_MARGIN = 137

class MyContactsFragment : Fragment() {

    private var _binding: FragmentMyContactsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ContactsAdapter
    private lateinit var viewModel: ContactsViewModel

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
        viewModel = ViewModelProvider(this, viewModelFactory)[ContactsViewModel::class.java]

        setupRecyclerView()
        setupObservers()
        setupListeners()
        setupDialogFragmentListener()
    }

    private fun setupListeners() {
        binding.addContactTextView.setOnClickListener {
            showAddUserDialog()
        }
        binding.arrowBackImageView.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager2>(R.id.pager)
            viewPager?.setCurrentItem(MY_PROFILE, true)
        }
    }

    private fun setupObservers() {
        viewModel.usersLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it.toMutableList())
        }
    }

    private fun setupRecyclerView() {
        adapter = ContactsAdapter(object : ContactActionListener {

            override fun onDeleteUser(contact: Contact) {
                val listBeforeDeletedContact = viewModel.usersLiveData.value
                viewModel.deleteUser(contact)
                showRestoreUserMessage(listBeforeDeletedContact)
            }

            override fun onUserDetails(contact: Contact) {
                val action =
                    ViewPagerFragmentDirections.actionViewPagerFragmentToContactsProfileFragment(
                        contact.photo,
                        contact.name,
                        contact.career
                    )
                findNavController().navigate(action)
            }

            override fun onSelectUser(contact: Contact) {
                viewModel.selectUser(contact)

                if (!viewModel.isAnyContactSelect()) {
                    adapter.changeModeStatus(false)
                    updateRecyclerViewMargin(DEFAULT_MARGIN)
                    binding.deleteUsersImageView.setVisibility(false)
                }
            }

            override fun onMultiSelectModeActive() {
                updateRecyclerViewMargin(MULTISELECT_MODE_MARGIN)
                binding.deleteUsersImageView.setVisibility(true)

                binding.deleteUsersImageView.setOnClickListener {
                    adapter.changeModeStatus(false)
                    viewModel.deleteSelectedContacts()
                    updateRecyclerViewMargin(DEFAULT_MARGIN)
                    binding.deleteUsersImageView.setVisibility(false)
                }
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }


    private fun updateRecyclerViewMargin(dp: Int) {
        val layoutParams =
            binding.recyclerView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.bottomMargin =
            (dp * binding.root.context.resources.displayMetrics.density).toInt()
        binding.recyclerView.layoutParams = layoutParams
    }

    private fun showRestoreUserMessage(listBeforeDeletedContact: List<Contact>?) {
        Snackbar.make(
            requireView(),
            getString(R.string.contact_has_been_removed),
            Snackbar.LENGTH_LONG
        ).setAction(
            getString(R.string.cancel)
        ) {
            viewModel.restoreUser(listBeforeDeletedContact)
        }
            .setActionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.my_light_primary
                )
            )
            .show()
    }

    private fun setupDialogFragmentListener() {
        AddUserDialogFragment.setDialogResultListener(
            childFragmentManager,
            viewLifecycleOwner
        ) { name, career ->
            viewModel.addUser(
                name, career
            )
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun showAddUserDialog() {
        AddUserDialogFragment.show(childFragmentManager, AddUserDialogFragment.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}