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

//        val orderAdapter = OrderListViewAdapter(requireContext(), orderList)
//        listView.adapter = orderAdapter

        cardview_chat.setOnClickListener {
            var intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("switch_checked", false)
            startActivity(intent)
        }


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val formatted = current.format(formatter)


        val uid = firebaseAuth.currentUser?.uid.toString()
        Log.d("UID : ", uid)

        val orderQuery = dbReference.child("orders").child(uid)
        var orderList = arrayListOf<OrderListViewItem>()
        var time:String = ""

        orderQuery.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                currentOrder.clear()
                orderList.clear()

                for (snap in snapshot.children) {
                    Log.d("item : ", snap.toString())
                    var item: MutableIterable<DataSnapshot> = snap.children

                    for (item2 in item){
                        Log.d("item2 : ", item2.toString())
                    }


//                    if (snap.child(uid).equals(uid)) {
//                        var item: MutableIterable<DataSnapshot> = snap.children
//                        for (item2 in item) {
//
//                            val data = item2.getValue<CurrentOrder>()
//                            val complete_writing = data?.complete_writing
//                            val request_accept = data?.request_accept
//                            val delivery = data?.delivery
//                            val complete_delivery = data?.complete_delivery
//                            val storeAddress = data?.storeAddress
//
//                            if (complete_delivery != null) {
//                                time = timeCalc(complete_writing.toString())
//                                Log.d("complete delivery time : ", time)
//
//                                if (time != ""){
//                                    orderList.add(0, OrderListViewItem("배달완료", time, storeAddress.toString()))
//                                }
//
//                            } else if (delivery != null) {
//                                time = timeCalc(delivery.toString())
//                                Log.d("delivery time : ", time)
//
//                                if (time != ""){
//                                    orderList.add(0, OrderListViewItem("배달중", time, storeAddress.toString()))
//                                }
//
//                            } else if (request_accept != null) {
//                                time = timeCalc(request_accept.toString())
//                                Log.d("request_accept time : ", time)
//
//                                if (time != ""){
//                                    orderList.add(0, OrderListViewItem("요청수락", time, storeAddress.toString()))
//                                }
//
//                            } else if (complete_writing != null) {
//                                time = timeCalc(complete_writing.toString())
//                                Log.d("complete_writing time : ", time)
//
//                                if (time != ""){
//                                    orderList.add(0, OrderListViewItem("작성완료", time, storeAddress.toString()))
//                                }
//
//                            } else {
//
//                            }
//                        }

//                    }
                }

//                if (orderList.size > 0){
//                    Log.d("Order List state : ", "data 있음")
//
//                    listView.visibility = View.VISIBLE
//                    tvNoneOrder.visibility = View.GONE
//
//                    val orderAdapter = OrderListViewAdapter(requireContext(), orderList)
//                    listView.adapter = orderAdapter
//
//                } else {
//                    Log.d("Order List state : ", "data 없음")
//                    listView.visibility = View.GONE
//                    tvNoneOrder.visibility = View.VISIBLE
//                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                currentOrder.clear()
                orderList.clear()
                for (snap in snapshot.children) {
                    if (snap.key.equals(uid)) {
                        var item: MutableIterable<DataSnapshot> = snap.children
                        for (item2 in item) {

                            val data = item2.getValue<CurrentOrder>()
                            val complete_writing = data?.complete_writing
                            val request_accept = data?.request_accept
                            val delivery = data?.delivery
                            val complete_delivery = data?.complete_delivery
                            val storeAddress = data?.storeAddress

                            if (complete_delivery != null) {
                                time = timeCalc(complete_writing.toString())
                                Log.d("complete delivery time : ", time)

                                if (time != ""){
                                    orderList.add(0, OrderListViewItem("배달완료", time, storeAddress.toString()))
                                }

                            } else if (delivery != null) {
                                time = timeCalc(delivery.toString())
                                Log.d("delivery time : ", time)

                                if (time != ""){
                                    orderList.add(0, OrderListViewItem("배달중", time, storeAddress.toString()))
                                }

                            } else if (request_accept != null) {
                                time = timeCalc(request_accept.toString())
                                Log.d("request_accept time : ", time)

                                if (time != ""){
                                    orderList.add(0, OrderListViewItem("요청수락", time, storeAddress.toString()))
                                }

                            } else if (complete_writing != null) {
                                time = timeCalc(complete_writing.toString())
                                Log.d("complete_writing time : ", time)

                                if (time != ""){
                                    orderList.add(0, OrderListViewItem("작성완료", time, storeAddress.toString()))
                                }

                            } else {

                            }
                        }

                    }
                }

                if (orderList.size > 0){
                    Log.d("Order List state : ", "data 있음")

                    listView.visibility = View.VISIBLE
                    tvNoneOrder.visibility = View.GONE

                    val orderAdapter = OrderListViewAdapter(requireContext(), orderList)
                    listView.adapter = orderAdapter

                } else {
                    Log.d("Order List state : ", "data 없음")
                    listView.visibility = View.GONE
                    tvNoneOrder.visibility = View.VISIBLE
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

//        orderQuery.addListenerForSingleValueEvent(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                currentOrder.clear()
//                orderList.clear()
//                for (snap in snapshot.children) {
//                    if (snap.key.equals(uid)) {
//                        var item: MutableIterable<DataSnapshot> = snap.children
//                        for (item2 in item) {
//
//                            val data = item2.getValue<CurrentOrder>()
//                            val complete_writing = data?.complete_writing
//                            val request_accept = data?.request_accept
//                            val delivery = data?.delivery
//                            val complete_delivery = data?.complete_delivery
//                            val storeAddress = data?.storeAddress
//
//                            if (complete_delivery != null) {
//                                time = timeCalc(complete_writing.toString())
//                                Log.d("complete delivery time : ", time)
//
//                                if (time != ""){
//                                    orderList.add(0, OrderListViewItem("배달완료", time, storeAddress.toString()))
//                                }
//
//                            } else if (delivery != null) {
//                                time = timeCalc(delivery.toString())
//                                Log.d("delivery time : ", time)
//
//                                if (time != ""){
//                                    orderList.add(0, OrderListViewItem("배달중", time, storeAddress.toString()))
//                                }
//
//                            } else if (request_accept != null) {
//                                time = timeCalc(request_accept.toString())
//                                Log.d("request_accept time : ", time)
//
//                                if (time != ""){
//                                    orderList.add(0, OrderListViewItem("요청수락", time, storeAddress.toString()))
//                                }
//
//                            } else if (complete_writing != null) {
//                                time = timeCalc(complete_writing.toString())
//                                Log.d("complete_writing time : ", time)
//
//                                if (time != ""){
//                                    orderList.add(0, OrderListViewItem("작성완료", time, storeAddress.toString()))
//                                }
//
//                            } else {
//
//                            }
//                        }
//
//                    }
//                }
//
//                if (orderList.size > 0){
//                    Log.d("Order List state : ", "data 있음")
//
//                    listView.visibility = View.VISIBLE
//                    tvNoneOrder.visibility = View.GONE
//
//                    val orderAdapter = OrderListViewAdapter(requireContext(), orderList)
//                    listView.adapter = orderAdapter
//
//                } else {
//                    Log.d("Order List state : ", "data 없음")
//                    listView.visibility = View.GONE
//                    tvNoneOrder.visibility = View.VISIBLE
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })


        return view
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeCalc(timeData: String): String {
        val todayDate = System.currentTimeMillis()
        var dataFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val today = dataFormat.format(Date(todayDate)).toString()
        var date:String = timeData.substring(0, 12)

        Log.d("now Date : ", today)
        Log.d("request Date : ", date)

        if (today == date){
            return ""
        }

        var time:String = timeData.substring(14)
        var meridiem:String = ""
        var hour:String = time.substring(0, 1)
        var minute:String = time.substring(2, 4)

        if (hour.toInt() == 12){
            meridiem = "오후 "
        }
        else if (hour.toInt() == 24) {
            meridiem = "오전 "
            hour = (hour.toInt() - 12).toString()
        }
        else if (hour.toInt() > 12){
            meridiem = "오후 "
            hour = (hour.toInt() - 12).toString()
        }
        else {
            meridiem = "오전 "
        }

        var timeResult: String = meridiem + hour + minute

        return timeResult
    }
}