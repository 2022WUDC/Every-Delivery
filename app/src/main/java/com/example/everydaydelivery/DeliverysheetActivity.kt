package com.example.everydaydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//내가 추가한거
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class DeliverysheetActivity : AppCompatActivity() {

    // 파이어베이스 연동
//    var firestore : FirebaseFirestore? = null
//    var auth : FirebaseAuth? = null
//    var user : FirebaseUser? = null
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId: String
    private lateinit var database: FirebaseDatabase
    lateinit var ref: DatabaseReference

    lateinit var dtextView1: TextView
    lateinit var dtextView2: TextView
    lateinit var dtextView3: TextView

    // 리사이클러뷰 설정
    //lateinit var recyclerview_deliv : RecyclerView
    //lateinit var mydelivlist : ArrayList<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_deliverysheet)

//        val deliveryList = arrayListOf(
//
//        )

//        // 파이어스토어 인스턴스 초기화
//        firestore = FirebaseFirestore.getInstance()
//        // 파이어베이스 인증 객체
//        auth = FirebaseAuth.getInstance()
//        user = auth!!.currentUser

        // 파이어베이스에서 데이터 불러오기



    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View?{
//        var view = inflater.inflate(R.layout.delivery_sheet, container, false)
//
//        firebaseAuth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()
//        val dbReference = database.reference
//        val userRef = dbReference.child("orders")
//        ref = userRef
//
//        dtextView1 = view.findViewById(R.id.arrive_add)
//        dtextView2 = view.findViewById(R.id.tv_deliveryprice)
//        dtextView3 = view.findViewById(R.id.tv_name)
//
//        return view
//    }

}