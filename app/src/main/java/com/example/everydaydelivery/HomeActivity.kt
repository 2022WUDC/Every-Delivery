package com.example.everydaydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar

class HomeActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        toolbar = findViewById(R.id.toolbar)  // 메뉴 아이템으로 해야 하는 것 같기도
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}