package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityRecyclerBinding
import com.example.myapplication.model.User

class RecyclerActivity : ComponentActivity() {

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
            }

            override fun onAddUser(user: User) {
                TODO("Not yet implemented")
            }
        })

        viewModel.users.observe(this, Observer {
            adapter.users = it
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }


}