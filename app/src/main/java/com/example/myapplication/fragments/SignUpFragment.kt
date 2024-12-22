package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentSignUpBinding
import com.example.myapplication.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val pass = binding.passEditText.text.toString().trim()
            userViewModel.registerUser(
                email,
                pass,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
            )
        }

        userViewModel.registerState.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_LONG)
                    .show()
            }.onFailure { error ->
                Toast.makeText(
                    requireContext(),
                    "Register failure: ${error.message}",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}