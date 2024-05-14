package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {


    private var _binding: FragmentViewPagerBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.addFragment(MyProfileFragment())
        viewPagerAdapter.addFragment(MyContactsFragment())

        binding.pager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = "My profile"
                1 -> tab.text = "My contacts"
            }
        }.attach()

        binding.pager[0].requireViewById<AppCompatButton>(R.id.viewMyContactsButton)
            .setOnClickListener {
                binding.pager.setCurrentItem(1, false)
            }

    }
}

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}

