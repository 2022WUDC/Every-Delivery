package com.example.everydaydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PeopleActivty : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var peopleFragment: PeopleFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_activty)

        auth = Firebase.auth

        peopleFragment = PeopleFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, peopleFragment).commit()
    }
}