package com.example.everydaydelivery

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Switch
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class HomeActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit var switch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar = findViewById(R.id.toolbar_mode)
        switch = findViewById(R.id.switch_homeMode)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout_home, OrderFragment())
            .commit()

        switch.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (isChecked) {
                toolbar.setBackground(getDrawable(R.drawable.gradation_pink))

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_home, TestDeliveryFragment())
                    .commit()
            } else {
                toolbar.setBackground(getDrawable(R.drawable.gradation_orange))

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_home, OrderFragment())
                    .commit()
            }
        }

    }
}