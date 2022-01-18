package com.example.everydaydelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var tvEmail: TextView
    lateinit var firebaseAuth:FirebaseAuth

    // 주문하기, 배달하기 버튼
    lateinit var order: Button
    lateinit var delivery: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        tvEmail = findViewById(R.id.textView_email)
        tvEmail.text = firebaseAuth.currentUser?.email

        order = findViewById(R.id.order)
        delivery = findViewById(R.id.delivery)

        // 주문하기 버튼을 눌렀을 때
        order.setOnClickListener {

            // deliveryActivity로 이동
            // 주문하기 페이지로 이동하는 것으로 수정해야 함
            val intent = Intent(this, DeliveryActivity::class.java)
            startActivity(intent)
        }

        // 배달하기 버튼을 눌렀을 때
        delivery.setOnClickListener {

            // deliveryActivity로 이동
            val intent = Intent(this, DeliveryActivity::class.java)
            startActivity(intent)
        }

    }
}