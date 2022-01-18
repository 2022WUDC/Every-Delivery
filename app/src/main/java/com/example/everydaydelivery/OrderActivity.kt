package com.example.everydaydelivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class OrderActivity : AppCompatActivity() {
    //lateinit var OrderSend: Button
    lateinit var tvStoreAddress: TextView
    lateinit var tvMenu: TextView
    lateinit var tvMenuPrice: TextView
    lateinit var tvDeliveryPrice: TextView
    lateinit var tvTotalPrice: TextView
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        firebaseAuth = FirebaseAuth.getInstance()

        tvStoreAddress = findViewById(R.id.StoreAddress)
        tvMenu = findViewById(R.id.Menu)
        tvMenuPrice = findViewById(R.id.MenuPrice)
        tvDeliveryPrice = findViewById(R.id.DeliveryPrice)
        tvTotalPrice = findViewById(R.id.TotalPrice)

        //OrderSend.setOnClickListener {
            //주문서 요청 다음 페이지로 넘어감
        //}

    }

}