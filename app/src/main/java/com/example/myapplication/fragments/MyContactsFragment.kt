package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.adapter.ContactActionListener
import com.example.myapplication.adapter.ContactsAdapter
import com.example.myapplication.databinding.FragmentMyContactsBinding
import com.example.myapplication.model.User
import com.example.myapplication.setVisibility
import com.example.myapplication.viewModel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val DEFAULT_MARGIN = 50
private const val MULTISELECT_MODE_MARGIN = 137

@AndroidEntryPoint
class MyContactsFragment : Fragment() {

    private var _binding: FragmentMyContactsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ContactsAdapter
    private val userViewModel: UserViewModel by activityViewModels()

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

        setupRecyclerView()

        setupObservers()

        setupListeners()
    }

    private fun setupListeners() {
        binding.addContactTextView.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_addContactsFragment)
        }
        binding.arrowBackImageView.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager2>(R.id.pager)
            viewPager?.setCurrentItem(MY_PROFILE, true)
        }
    }

    private fun setupObservers() {
        userViewModel.addedContacts.observe(viewLifecycleOwner) {
            adapter.submitList(it.toMutableList())
        }
    }

    private fun setupRecyclerView() {
        adapter = ContactsAdapter(object : ContactActionListener {

            override fun onDeleteUser(contact: User) {
                val listBeforeDeletedContact = userViewModel.addedContacts.value
                userViewModel.deleteContact(contact)
//                showRestoreUserMessage(listBeforeDeletedContact)
            }

            override fun onUserDetails(contact: User) {
                val action =
                    ViewPagerFragmentDirections.actionViewPagerFragmentToContactsProfileFragment(
                        contact.imageUrl.toString(),
                        contact.name.toString(),
                        contact.career.toString()
                    )
                findNavController().navigate(action)
            }

            override fun onSelectUser(contact: User) {
//                viewModel.selectUser(contact)
//
//                if (!viewModel.isAnyContactSelect()) {
//                    adapter.changeModeStatus(false)
//                    updateRecyclerViewMargin(DEFAULT_MARGIN)
//                    binding.deleteUsersImageView.setVisibility(false)
//                }
            }

            override fun onMultiSelectModeActive() {
//                updateRecyclerViewMargin(MULTISELECT_MODE_MARGIN)
//                binding.deleteUsersImageView.setVisibility(true)
//
//                binding.deleteUsersImageView.setOnClickListener {
//                    adapter.changeModeStatus(false)
//                    viewModel.deleteSelectedContacts()
//                    updateRecyclerViewMargin(DEFAULT_MARGIN)
//                    binding.deleteUsersImageView.setVisibility(false)
//                }
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

//    private fun showRestoreUserMessage(listBeforeDeletedContact: List<User>?) {
//        Snackbar.make(
//            requireView(),
//            getString(R.string.contact_has_been_removed),
//            Snackbar.LENGTH_LONG
//        ).setAction(
//            getString(R.string.cancel)
//        ) {
//            viewModel.restoreUser(listBeforeDeletedContact)
//        }
//            .setActionTextColor(
//                ContextCompat.getColor(
//                    requireContext(),
//                    R.color.my_light_primary
//                )
//            )
//            .show()
//    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}