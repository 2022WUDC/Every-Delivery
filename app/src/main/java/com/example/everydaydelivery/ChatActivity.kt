package com.example.everydaydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        if (switch_checked == "false") {
            bundle.putString("switch_checked", "false")
        } else {
            bundle.putString("switch_checked", "true")
        }

        chatFragment = ChatFragment.newInstance()
        chatFragment.arguments = bundle

        supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, chatFragment).commit()
    }
}