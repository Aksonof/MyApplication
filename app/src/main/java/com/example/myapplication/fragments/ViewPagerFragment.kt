package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

const val FIRST_TAB = 0
const val SECOND_TAB = 1


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.addFragment(MyProfileFragment())
        viewPagerAdapter.addFragment(MyContactsFragment())

        binding.pager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                FIRST_TAB -> tab.text = "My profile"
                SECOND_TAB -> tab.text = "My contacts"
            }
        }.attach()
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

