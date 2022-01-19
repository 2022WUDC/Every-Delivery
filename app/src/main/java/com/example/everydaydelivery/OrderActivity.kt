package com.example.everydaydelivery

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


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
    lateinit var btn_back: ImageButton
    lateinit var layout_request: LinearLayout

    lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val dbReference = database.reference
        val orderRef = dbReference.child("orders")
        val uid = firebaseAuth.currentUser?.uid.toString()

        val time = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH:MM")
        val curTime = dateFormat.format(Date(time)).toString()

        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        edt_StoreAddress = findViewById(R.id.StoreAddress)
        edt_Menu = findViewById(R.id.Menu)
        edt_MenuPrice = findViewById(R.id.MenuPrice)
        edt_DeliveryPrice = findViewById(R.id.DeliveryPrice)
        edt_TotalPrice = findViewById(R.id.TotalPrice)
        edt_OrderRequest = findViewById(R.id.OrderRequest)
        OrderSend = findViewById(R.id.OrderSend)
        btn_back = findViewById(R.id.btn_back)
        layout_request = findViewById(R.id.layout_request)


        layout_request.setOnClickListener {
            imm.hideSoftInputFromWindow(edt_StoreAddress.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_Menu.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_MenuPrice.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_DeliveryPrice.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_TotalPrice.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_OrderRequest.windowToken, 0)
        }

        btn_back.setOnClickListener {
            finish()
        }

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


                val order = firebaseAuth.currentUser

                Toast.makeText(this,"complete writing", Toast.LENGTH_SHORT).show()

                val order_db = orderRef.child(order?.uid.toString()).push()
                Log.d(TAG, "child.uid : " + order?.uid.toString())

                order_db.child("uid").setValue(order?.uid.toString())
                Log.d(TAG, "child.uid : " + order?.uid.toString())

                order_db.child("storeAddress").setValue(storeAddress)
                Log.d(TAG, "child.uid : " + order?.uid.toString())

                order_db.child("menu").setValue(menu)
                Log.d(TAG, "child.uid : " + order?.uid.toString())

                order_db.child("menuPrice").setValue(menuPrice)
                Log.d(TAG, "child.uid : " + order?.uid.toString())

                order_db.child("deliveryPrice").setValue(deliveryPrice)
                Log.d(TAG, "child.uid : " + order?.uid.toString())

                order_db.child("totalPrice").setValue(totalPrice)
                Log.d(TAG, "child.uid : " + order?.uid.toString())

                order_db.child("orderRequest").setValue(orderRequest)
                Log.d(TAG, "child.uid : " + order?.uid.toString())

                order_db.child("complete_writing").setValue(curTime)

                val intent = Intent(this, OrderSheetActivity::class.java)
                intent.putExtra("storeAddress", storeAddress)
                intent.putExtra("menu", menu)
                intent.putExtra("menuPrice", menuPrice)
                intent.putExtra("deliveryPrice", deliveryPrice)
                intent.putExtra("totalPrice", totalPrice)
                intent.putExtra("complete_writing", orderRequest)
                intent.putExtra("uid", uid)
                startActivity(intent)

            }

        }
    }

}