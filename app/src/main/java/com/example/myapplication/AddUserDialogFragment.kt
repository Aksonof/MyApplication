package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.AddUserDialogBinding


interface AddUserDialogListener {
    fun onDataEntered(data: Bundle)
}

class AddUserDialogFragment(private val listener: AddUserDialogListener) : DialogFragment() {

    // Default constructor required by the system
    constructor() : this(object : AddUserDialogListener {
        override fun onDataEntered(data: Bundle) {
        }
    })

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
            val bundle = Bundle()
            bundle.putString("userName", binding.userNameEditTextView.text.toString())
            bundle.putString("userCareer", binding.userCareerEditTextView.text.toString())
            listener.onDataEntered(bundle)
            dismiss()
        }

        binding.arrowBackImageView.setOnClickListener {
            dismiss()
        }
        return binding.root
    }


    companion object {
        @JvmStatic
        val TAG: String = AddUserDialogFragment::class.java.simpleName
    }
}

