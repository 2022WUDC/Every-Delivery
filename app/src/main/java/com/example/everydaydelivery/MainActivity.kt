package com.example.everydaydelivery

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit var switch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar_mode)
        switch = findViewById(R.id.switch_homeMode)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout_home, OrderFragment())
            .commit()

        switch.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (isChecked) {
                toolbar.setBackground(getDrawable(R.drawable.gradation_pink))
                window.statusBarColor = Color.parseColor("#ff6175")

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_home, DeliveryFragment())
                    .commit()
            } else {
                toolbar.setBackground(getDrawable(R.drawable.gradation_orange))
                window.statusBarColor = Color.parseColor("#ffaa00")


                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_home, OrderFragment())
                    .commit()
            }
        }

    }
}