package com.example.everydaydelivery

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var chatFragment: ChatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        auth = Firebase.auth

        val bundle = Bundle()
        val switch_checked = intent.getStringExtra("switch_checked")


        Log.d("swingch Check : ", switch_checked.toString())

        if (switch_checked == "false") {
            bundle.putString("switch_checked", "false")
            window.statusBarColor = Color.parseColor("#ffaa00")
            Log.d("check", "!!!!")

        } else {
            bundle.putString("switch_checked", "true")
            window.statusBarColor = Color.parseColor("#ff6175")

        }

        chatFragment = ChatFragment.newInstance()
        chatFragment.arguments = bundle

        supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, chatFragment).commit()
    }
}