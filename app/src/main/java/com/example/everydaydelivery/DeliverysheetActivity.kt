package com.example.everydaydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
//내가 추가한거
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DeliverysheetActivity : AppCompatActivity() {

    // 파이어베이스 연동
    var firestore : FirebaseFirestore? = null
    var auth : FirebaseAuth? = null

    // 리사이클러뷰 설정
    lateinit var recyclerview_deliv : RecyclerView
    lateinit var mydelivlist : ArrayList<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliverysheet)

//        val deliveryList = arrayListOf(
//
//        )
    }

}