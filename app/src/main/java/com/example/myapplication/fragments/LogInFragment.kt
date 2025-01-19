package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLogInBinding
import com.example.myapplication.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val pass = binding.passEditText.text.toString().trim()
            userViewModel.loginUser(email, pass)
        }

        userViewModel.registerState.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_LONG)
                    .show()
            }.onFailure { error ->
                Toast.makeText(
                    requireContext(),
                    "Login failure: ${error.message}",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }


        userViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                val action = LogInFragmentDirections.actionLoginFragmentToViewPagerFragment(user)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}