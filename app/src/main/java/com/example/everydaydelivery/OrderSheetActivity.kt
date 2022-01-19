package com.example.everydaydelivery

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class OrderSheetActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val order = ArrayList<Order>()
    private val fireDatabase = FirebaseDatabase.getInstance().reference

    lateinit var tvStoreAddress: TextView
    lateinit var tvMenu: TextView
    lateinit var tvMenuPrice: TextView
    lateinit var tvDeliveryPrice: TextView
    lateinit var tvTotalPrice: TextView
    lateinit var tvOrderRequest: TextView
    lateinit var tvuid:TextView
    lateinit var uid:String
    lateinit var home:ImageButton

    lateinit var inputManager: InputMethodManager

    private lateinit var time: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_sheet)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val dbReference = database.reference
        uid = firebaseAuth.currentUser?.uid.toString()

        tvStoreAddress = findViewById(R.id.textview_StoreAddress)
        tvMenu = findViewById(R.id.textview_Menu)
        tvMenuPrice = findViewById(R.id.textview_MenuPrice)
        tvDeliveryPrice = findViewById(R.id.textview_DeliveryPrice)
        tvTotalPrice = findViewById(R.id.textview_TotalPrice)
        tvOrderRequest = findViewById(R.id.textview_OrderRequest)
        tvuid = findViewById(R.id.textview_uid)
        home = findViewById(R.id.home_btn)

        time = intent.getStringExtra("complete_writing").toString()
        Log.d("시간", "$time")


        inputManager =
            getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager


        tvStoreAddress.text = intent.getStringExtra("storeAddress").toString()
        tvMenu.text = intent.getStringExtra("menu").toString()
        tvMenuPrice.text = intent.getStringExtra("menuPrice").toString()
        tvDeliveryPrice.text = intent.getStringExtra("deliveryPrice").toString()
        tvTotalPrice.text = intent.getStringExtra("totalPrice").toString()
        tvOrderRequest.text = intent.getStringExtra("complete_writing").toString()
        val uid = intent.getStringExtra("uid").toString()

        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }



}