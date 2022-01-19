package com.example.everydaydelivery

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class OrderFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId: String
    private lateinit var database: FirebaseDatabase
    lateinit var ref: DatabaseReference

    lateinit var listView: ListView
    lateinit var tvNoneOrder: TextView
    lateinit var cardview_chat: CardView

    private var currentOrder: ArrayList<CurrentOrder> = arrayListOf()

//    var orderList = arrayListOf<OrderListViewItem>(
//        OrderListViewItem("작성완료", "오전 10:49", "응급실국물떡볶이 서울자양점"),
//        OrderListViewItem("요청수락", "오전 10:54", "응급실국물떡볶이 서울자양점"),
//        OrderListViewItem("배달중", "오전 11:21", "응급실국물떡볶이 서울자양점"),
//        OrderListViewItem("배달완료", "오전 11:47", "응급실국물떡볶이 서울자양점"),
//    )

//    var orderList = arrayListOf<OrderListViewItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val dbReference = database.reference

        listView = view.findViewById(R.id.listView_home)
        tvNoneOrder = view.findViewById(R.id.textView_noneOrder)
        cardview_chat = view.findViewById(R.id.cardview_chat)

        cardview_chat.setOnClickListener {
            var intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("switch_checked", false)
            startActivity(intent)
        }


//        val orderAdapter = OrderListViewAdapter(requireContext(), orderList)
//        listView.adapter = orderAdapter


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val formatted = current.format(formatter)


        val uid = firebaseAuth.currentUser?.uid.toString()
        val orderQuery = dbReference.child("orders").child(uid).equalTo(uid)




        orderQuery.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                currentOrder.clear()
                for (snap in snapshot.children) {
                    var item: MutableIterable<DataSnapshot> = snap.children
                    for (item2 in item) {
                        val data = item2.getValue<CurrentOrder>()
                        val complete_writing = data?.complete_writing
                        val request_accept = data?.request_accept
                        val delivery = data?.delivery
                        val complete_delivery = data?.complete_delivery
                        val storeAddress = data?.storeAddress

                        Log.d("item2 complete : ", complete_writing.toString())
                        Log.d("item2 storeAddress : ", storeAddress.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return view
    }
}