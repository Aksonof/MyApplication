package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityRecyclerBinding
import com.example.myapplication.model.User
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
                viewModel.deleteUser(user)

                /*Snackbar.make(binding.root, getString(R.string.contact_has_been_removed), 5000)
                    .setAction(
                        getString(R.string.cancel)
                    ) { viewModel.restoreUser(user) }
                    .setActionTextColor(
                        ContextCompat.getColor(
                            this@RecyclerActivity,
                            R.color.my_light_primary
                        )
                    )
                    .show()*/
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        viewModel.usersLiveData.observe(this) {
            adapter.submitList(it.toMutableList())
        }

        /*binding.addContactTextView.setOnClickListener {
            showAddUserDialog()
        }*/
    }

    /*private fun showAddUserDialog() {

        val dialogFragment = AddUserDialogFragment(object : AddUserDialogListener {
            override fun onDataEntered(data: Bundle) {
                val userName = data.getString("userName")
                val userCareer = data.getString("userCareer")
                if (userCareer!!.isNotBlank() && userName!!.isNotBlank()) {
                    viewModel.addUser(User(userName, userCareer, "", 0))
                }
            }
        })
        dialogFragment.show(supportFragmentManager, AddUserDialogFragment.TAG)
    }*/
}