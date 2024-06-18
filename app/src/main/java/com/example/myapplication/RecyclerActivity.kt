package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.UserActionListener
import com.example.myapplication.adapter.UsersAdapter
import com.example.myapplication.databinding.ActivityRecyclerBinding
import com.example.myapplication.model.User
import com.example.myapplication.viewModel.UsersListViewModel
import com.example.myapplication.viewModel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerBinding
    private lateinit var adapter: UsersAdapter
    private lateinit var viewModel: UsersListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModelFactory = ViewModelFactory(applicationContext as App)

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(modelClass = UsersListViewModel::class.java)

        adapter = UsersAdapter(object : UserActionListener {
            override fun onDeleteUser(user: User) {
                val listBeforeDeletedContact = viewModel.usersLiveData.value
                viewModel.deleteUser(user)

                Snackbar.make(binding.root, getString(R.string.contact_has_been_removed), 5000)
                    .setAction(
                        getString(R.string.cancel)
                    ) { viewModel.restoreUser(listBeforeDeletedContact) }
                    .setActionTextColor(
                        ContextCompat.getColor(
                            this@RecyclerActivity,
                            R.color.my_light_primary
                        )
                    )
                    .show()
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        viewModel.usersLiveData.observe(this) {
            adapter.submitList(it.toMutableList())
        }
        binding.addContactTextView.setOnClickListener {
            showAddUserDialog()
        }
        setupDialogFragmentListener()
    }

    private fun setupDialogFragmentListener() {
        AddUserDialogFragment.setDialogResListener(
            supportFragmentManager,
            this
        ) { name, career ->
            viewModel.addUser(User(name, career, "", 0L))
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun showAddUserDialog() {
        AddUserDialogFragment.show(supportFragmentManager, AddUserDialogFragment.TAG)
    }

}