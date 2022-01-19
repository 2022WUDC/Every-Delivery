package com.example.everydaydelivery

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class OrderSheetActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val order = ArrayList<Order>()

    lateinit var tvStoreAddress: TextView
    lateinit var tvMenu: TextView
    lateinit var tvMenuPrice: TextView
    lateinit var tvDeliveryPrice: TextView
    lateinit var tvTotalPrice: TextView
    lateinit var tvOrderRequest: TextView

    lateinit var inputManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_sheet)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val dbReference = database.reference

        tvStoreAddress = findViewById(R.id.textview_StoreAddress)
        tvMenu = findViewById(R.id.textview_Menu)
        tvMenuPrice = findViewById(R.id.textview_MenuPrice)
        tvDeliveryPrice = findViewById(R.id.textview_DeliveryPrice)
        tvTotalPrice = findViewById(R.id.textview_TotalPrice)
        tvOrderRequest = findViewById(R.id.textview_OrderRequest)

        inputManager = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val uid = firebaseAuth.currentUser?.uid.toString()
        val orderQuery = dbReference.child("orders").orderByChild(uid)

       //dbReference.child("orders/" + uid + "/storeAddress").setValue(tvStoreAddress.text.toString())

        FirebaseDatabase.getInstance().reference.child("orders").addValueEventListener(object :
        ValueEventListener {
            override fun onCancelled(error: DatabaseError) {


            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var snap: DataSnapshot
                order.clear()
                for (snap in snapshot.children) {
                    var item: MutableIterable<DataSnapshot> = snap.children
                    for (data in item) {
                        data.getValue<Order>()?.let { order.add(it) }
                    }
                }

            }
        })

        /*
        orderQuery.addValueEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("Order uid", snapshot.toString())
                val storeAddress:String = snapshot.child("storeAddress").value.toString()
                Log.d("Order uid", "${storeAddress}")
                val menu:String = snapshot.child("menu").value.toString()
                val menuPrice:String = snapshot.child("menuPrice").value.toString()
                val deliveryPrice:String = snapshot.child("deliveryPrice").value.toString()
                val totalPrice: String = snapshot.child("totalPrice").value.toString()
                val orderRequest: String = snapshot.child("orderRequest").value.toString()

                tvStoreAddress.text = storeAddress
                tvMenu.text = menu
                tvMenuPrice.text = menuPrice
                tvDeliveryPrice.text = deliveryPrice
                tvTotalPrice.text = totalPrice
                tvOrderRequest.text = orderRequest


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val storeAddress:String = snapshot.child("storeAddress").value.toString()
                val menu:String = snapshot.child("menu").value.toString()
                val menuPrice:String = snapshot.child("menuPrice").value.toString()
                val deliveryPrice:String = snapshot.child("deliveryPrice").value.toString()
                val totalPrice: String = snapshot.child("totalPrice").value.toString()
                val orderRequest: String = snapshot.child("orderRequest").value.toString()

                tvStoreAddress.text = storeAddress
                tvMenu.text = menu
                tvMenuPrice.text = menuPrice
                tvDeliveryPrice.text = deliveryPrice
                tvTotalPrice.text = totalPrice
                tvOrderRequest.text = orderRequest
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
*/


    }



}
/*
private fun Query.addValueEventListener(childEventListener: ChildEventListener) {

}
*/
