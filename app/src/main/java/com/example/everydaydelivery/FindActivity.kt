package com.example.everydaydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FindActivity : AppCompatActivity() {
    lateinit var btn_back: ImageButton
    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager2

    var PAGE_CNT = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        btn_back = findViewById(R.id.btn_back)

        btn_back.setOnClickListener {
            finish()
        }

        tablayout = findViewById(R.id.tablayout_find)
        viewpager = findViewById(R.id.viewpager2_find)

        val adapter = ViewPagerAdapter(this)
        viewpager.adapter = adapter

        val tabName = arrayOf<String>("아이디", "비밀번호")

        TabLayoutMediator(tablayout, viewpager){tab,position->
            tab.text = tabName[position]
        }.attach()
    }

    private inner class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
        override fun createFragment(position: Int): Fragment {
            return when(position){
                //0 -> createTeamFragment()
                0 -> FindIdFragment()
                1 -> FindPasswordFragment()
                else -> FindIdFragment()
            }
        }
        override fun getItemCount():Int = PAGE_CNT
    }
}