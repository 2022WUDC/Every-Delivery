package com.example.everydaydelivery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class OrderActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    lateinit var OrderSend: Button
    lateinit var edt_StoreAddress: EditText
    lateinit var edt_Menu: EditText
    lateinit var edt_MenuPrice: EditText
    lateinit var edt_DeliveryPrice: EditText
    lateinit var edt_TotalPrice: EditText
    lateinit var edt_OrderRequest: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val dbReference = database.reference
        val orderRef = dbReference.child("orders")

        edt_StoreAddress = findViewById(R.id.StoreAddress)
        edt_Menu = findViewById(R.id.Menu)
        edt_MenuPrice = findViewById(R.id.MenuPrice)
        edt_DeliveryPrice = findViewById(R.id.DeliveryPrice)
        edt_TotalPrice = findViewById(R.id.TotalPrice)
        edt_OrderRequest = findViewById(R.id.OrderRequest)
        OrderSend = findViewById(R.id.OrderSend)


        OrderSend.setOnClickListener {
            var storeAddress = edt_StoreAddress.text.toString()
            var menu = edt_Menu.text.toString()
            var menuPrice = edt_MenuPrice.text.toString()
            var deliveryPrice = edt_DeliveryPrice.text.toString()
            var totalPrice = edt_TotalPrice.text.toString()
            var orderRequest = edt_OrderRequest.text.toString()
            if (storeAddress.length == 0) {
                Toast.makeText(this, "해당 가게의 도로명 주소를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (menu.length == 0) {
                Toast.makeText(this, "요청 메뉴를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (menuPrice.length == 0) {
                Toast.makeText(this, "메뉴 금액을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (deliveryPrice.length == 0) {
                Toast.makeText(this, "배달 금액을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (totalPrice.length == 0) {
                Toast.makeText(this, "최종 금액을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {

                val intent = Intent(this, DeliveryActivity::class.java)
                startActivity(intent)
            }

        }
    }

}