package com.example.myapplication

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.AddUserDialogBinding


interface AddUserDialogListener {
    fun onDataEntered(data: Bundle)
}

class AddUserDialogFragment(private val listener: AddUserDialogListener) : DialogFragment() {

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userNameEditTextView.setTextColor(Color.BLACK)
        binding.userCareerEditTextView.setTextColor(Color.BLACK)

        binding.saveButtonView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("userName", binding.userNameEditTextView.text.toString())
            bundle.putString("userCareer", binding.userCareerEditTextView.text.toString())
            listener.onDataEntered(bundle)
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        private val TAG = AddUserDialogFragment::class.java.simpleName
    }
}

