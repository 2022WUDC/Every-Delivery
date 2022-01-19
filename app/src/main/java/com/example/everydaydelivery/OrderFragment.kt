package com.example.everydaydelivery

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
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
    lateinit var cardview_request: CardView
    lateinit var cardview_chat: CardView
    lateinit var cardview_myPage:CardView

    // 지도 확인용
    lateinit var btnMap: Button

    private var currentOrder: ArrayList<CurrentOrder> = arrayListOf()

    var orderList = arrayListOf<OrderListViewItem>()

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
        cardview_request = view.findViewById(R.id.cardview_request)
        cardview_chat = view.findViewById(R.id.cardview_chat)
        cardview_myPage = view.findViewById(R.id.cardview_myPage)

        // 지도 확인용
        btnMap = view.findViewById(R.id.button)

        // 지도 확인용
        btnMap.setOnClickListener {
            var intent = Intent(requireContext(), OrderMyPageActivity::class.java)
            startActivity(intent)
        }

        cardview_request.setOnClickListener {
            var intent = Intent(requireContext(), ChatActivity::class.java)
            startActivity(intent)
        }

        cardview_chat.setOnClickListener {
            var intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("switch_checked", false)
            startActivity(intent)
        }

        cardview_myPage.setOnClickListener {
            var intent = Intent(requireContext(), OrderMyPageActivity::class.java)
            startActivity(intent)
        }


//        val orderAdapter = OrderListViewAdapter(requireContext(), orderList)
//        listView.adapter = orderAdapter


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val formatted = current.format(formatter)

        val uid = firebaseAuth.currentUser?.uid.toString()
        val orderQuery = dbReference.child("orders")

        orderQuery.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                currentOrder.clear()
                var time: String = ""
                var before_time: String = ""

                var snap: MutableIterable<DataSnapshot> = snapshot.children

                for (snap2 in snap) {
                    val data = snap2.getValue<CurrentOrder>()
                    val complete_writing = data?.complete_writing
                    val request_accept = data?.request_accept
                    val delivery = data?.delivery
                    val complete_delivery = data?.complete_delivery
                    val storeAddress = data?.storeAddress
                    val data_uid = data?.uid

                    Log.d("storeAddress : ", storeAddress.toString())

                    if (data_uid == uid) {
                        if (complete_delivery != null && delivery != null) {
                            Log.d("complete_delivery : ", complete_delivery)
                            time = timeCalc(complete_delivery)
                            before_time = timeCalc(delivery)

                            if (time != "") {
                                orderList.remove(OrderListViewItem("배달중", before_time, storeAddress.toString()))
                                orderList.add(OrderListViewItem("배달완료", time, storeAddress.toString()))
                            }
                        } else if (delivery != null && request_accept != null) {
                            Log.d("delivery : ", delivery)

                            time = timeCalc(delivery)
                            before_time = timeCalc(request_accept)

                            if (time != "") {
                                orderList.remove(OrderListViewItem("요청수락", before_time, storeAddress.toString()))
                                orderList.add(OrderListViewItem("배달중", time, storeAddress.toString()))
                            }
                        } else if (request_accept != null && complete_writing != null) {
                            Log.d("request_accept : ", request_accept)

                            time = timeCalc(request_accept)
                            before_time = timeCalc(complete_writing)

                            if (time != "") {
                                orderList.remove(OrderListViewItem("작성완료", before_time, storeAddress.toString()))
                                orderList.add(OrderListViewItem("요청수락", time, storeAddress.toString()))
                            }
                        } else if (complete_writing != null) {
                            Log.d("complete_writing : ", complete_writing)

                            time = timeCalc(complete_writing)

                            if (time != "") {
                                orderList.add(OrderListViewItem("작성완료", time, storeAddress.toString()))
                            }
                        } else {}
                    }

                    Log.d("orderList.size : ", orderList.size.toString())
                    Log.d("orderList : ", orderList.toString())

                    if (orderList.size > 0){
                        listView.visibility = View.VISIBLE
                        tvNoneOrder.visibility = View.GONE

                        val orderAdapter = OrderListViewAdapter(requireContext(), orderList)
                        listView.adapter = orderAdapter
                    } else {
                        listView.visibility = View.GONE
                        tvNoneOrder.visibility = View.VISIBLE
                    }

                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                currentOrder.clear()
                var time: String = ""
                var before_time: String = ""

                var snap: MutableIterable<DataSnapshot> = snapshot.children

                for (snap2 in snap) {
                    val data = snap2.getValue<CurrentOrder>()
                    val complete_writing = data?.complete_writing
                    val request_accept = data?.request_accept
                    val delivery = data?.delivery
                    val complete_delivery = data?.complete_delivery
                    val storeAddress = data?.storeAddress
                    val data_uid = data?.uid

                    Log.d("storeAddress : ", storeAddress.toString())

                    if (data_uid == uid) {
                        if (complete_delivery != null && delivery != null) {
                            Log.d("complete_delivery : ", complete_delivery)
                            time = timeCalc(complete_delivery)
                            before_time = timeCalc(delivery)

                            if (time != "") {
                                orderList.remove(OrderListViewItem("배달중", before_time, storeAddress.toString()))
                                orderList.add(OrderListViewItem("배달완료", time, storeAddress.toString()))
                            }
                        } else if (delivery != null && request_accept != null) {
                            Log.d("delivery : ", delivery)

                            time = timeCalc(delivery)
                            before_time = timeCalc(request_accept)

                            if (time != "") {
                                orderList.remove(OrderListViewItem("요청수락", before_time, storeAddress.toString()))
                                orderList.add(OrderListViewItem("배달중", time, storeAddress.toString()))
                            }
                        } else if (request_accept != null && complete_writing != null) {
                            Log.d("request_accept : ", request_accept)

                            time = timeCalc(request_accept)
                            before_time = timeCalc(complete_writing)

                            if (time != "") {
                                orderList.remove(OrderListViewItem("작성완료", before_time, storeAddress.toString()))
                                orderList.add(OrderListViewItem("요청수락", time, storeAddress.toString()))
                            }
                        } else if (complete_writing != null) {
                            Log.d("complete_writing : ", complete_writing)

                            time = timeCalc(complete_writing)

                            if (time != "") {
                                orderList.remove(OrderListViewItem("작성완료", time, storeAddress.toString()))
                                orderList.add(OrderListViewItem("작성완료", time, storeAddress.toString()))
                            }
                        } else {}
                    }

                    Log.d("orderList.size : ", orderList.size.toString())
                    Log.d("orderList : ", orderList.toString())

                    if (orderList.size > 0){
                        listView.visibility = View.VISIBLE
                        tvNoneOrder.visibility = View.GONE

                        val orderAdapter = OrderListViewAdapter(requireContext(), orderList)
                        listView.adapter = orderAdapter
                    } else {
                        listView.visibility = View.GONE
                        tvNoneOrder.visibility = View.VISIBLE
                    }
                }
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


        return view
    }

    private fun timeCalc(dateTime: String): String {
        Log.d("dateTime : ", dateTime)

        val todayDateTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val today = dateFormat.format(Date(todayDateTime)).toString()

        val dataDate = dateTime.substring(0, 13)

        Log.d("today : ", today + ".")
        Log.d("dateDate : ", dataDate + ".")

        if (today != dataDate) {
            return ""
        }

        var hour = dateTime.substring(14, 16)
        val minute = dateTime.substring(16)
        var meridiem: String = ""

        Log.d("hour : ", hour)
        Log.d("minute : ", minute)


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

        val time:String = meridiem + hour + minute

        Log.d("meridiem : ", meridiem)
        Log.d("after hour : ", hour)
        Log.d("after minute : ", minute)
        Log.d("after time : ", time)

        return time
    }
}