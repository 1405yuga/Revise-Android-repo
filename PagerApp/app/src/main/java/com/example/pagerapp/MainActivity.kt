package com.example.pagerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pagerapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> {
                    tab.text = "Chats"
                    tab.setIcon(R.drawable.baseline_chat_24)
                }

                1 -> {
                    tab.text = "Status"
                    tab.setIcon(R.drawable.baseline_status_24)
                }

                2 -> {
                    tab.text = "Calls"
                    tab.setIcon(R.drawable.baseline_call_24)
                }
            }

        }.attach()
    }

}