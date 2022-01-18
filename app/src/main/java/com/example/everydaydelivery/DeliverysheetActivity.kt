package com.example.everydaydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
//내가 추가한거
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class DeliverysheetActivity : AppCompatActivity() {

    // 파이어베이스 연동
    var firestore : FirebaseFirestore? = null
    var auth : FirebaseAuth? = null
    var user : FirebaseUser? = null

    // 리사이클러뷰 설정
    //lateinit var recyclerview_deliv : RecyclerView
    //lateinit var mydelivlist : ArrayList<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliverysheet)

//        val deliveryList = arrayListOf(
//
//        )

        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()
        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser

        // 파이어베이스에서 데이터 불러오기



    }

}