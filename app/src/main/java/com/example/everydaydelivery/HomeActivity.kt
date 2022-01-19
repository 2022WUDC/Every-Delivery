package com.example.everydaydelivery

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Switch
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar

class HomeActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit var switch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        switch = findViewById(R.id.switch_homeMode)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout_home, OrderFragment())
            .commit()

        switch.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (isChecked) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_home, TestDeliveryFragment())
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_home, OrderFragment())
                    .commit()
            }
        }

    }
}