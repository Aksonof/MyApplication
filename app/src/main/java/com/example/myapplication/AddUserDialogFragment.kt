package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.databinding.AddUserDialogBinding


class AddUserDialogFragment : DialogFragment() {


    private lateinit var binding: AddUserDialogBinding


    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddUserDialogBinding.inflate(inflater, container, false)

        binding.saveButtonView.setOnClickListener {
            val userName = binding.userNameEditTextView.text.toString()
            val userCareer = binding.userCareerEditTextView.text.toString()

            parentFragmentManager.setFragmentResult(
                REQUEST_KEY,
                bundleOf("Name" to userName, "Career" to userCareer)
            )
            dismiss()
        }

        binding.arrowBackImageView.setOnClickListener {
            dismiss()
        }
        return binding.root
    }


    companion object {

        const val TAG = "AddUserDialogFragment"

        @JvmStatic
        val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager, tag: String) {
            val dialogFragment = AddUserDialogFragment()
            dialogFragment.show(manager, tag)
        }

        fun setDialogResListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (name: String, career: String) -> Unit
        ) {
            manager.setFragmentResultListener(
                REQUEST_KEY,
                lifecycleOwner
            ) { _, result ->
                listener.invoke(result.getString("Name")!!, result.getString("Career")!!)
            }
        }
    }
}

