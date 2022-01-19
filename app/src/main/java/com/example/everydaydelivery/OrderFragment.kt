package com.example.everydaydelivery

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.min


class OrderFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId: String
    private lateinit var database: FirebaseDatabase
    lateinit var ref: DatabaseReference

    lateinit var listView: ListView
    lateinit var tvNoneOrder: TextView

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

//        val orderAdapter = OrderListViewAdapter(requireContext(), orderList)
//        listView.adapter = orderAdapter


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val formatted = current.format(formatter)

        Log.d(tag, formatted)

        val uid = firebaseAuth.currentUser?.uid.toString()
        val orderQuery = dbReference.child("orders").orderByChild(uid)

        orderQuery.addChildEventListener(object: ChildEventListener {
            var orderList = arrayListOf<OrderListViewItem>()

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("Today Date : ", LocalDate.now().toString())
                var date = LocalDate.now().toString()
                orderList.clear()

                val storeAddress: String = snapshot.child("storeAddress").value.toString()
                var time: String = ""
                val complete_writing = snapshot.child("complete writing").value

                var df = SimpleDateFormat("yyyy-MM-dd")
                Log.d("firebase data : ", df.parse(complete_writing.toString()).time.toString())
                Log.d("now data timestamp : ", df.parse(date).time.toString())


                if (complete_writing != null){

                    var write_time:String = complete_writing.toString().substring(11)
                    var meridiem:String = ""
                    var hour:String = ""
                    var minute:String = write_time.substring(2, 4)

                    if (write_time.substring(0, 1).toInt() == 12){
                        meridiem = "오후 "
                        hour = write_time.substring(0, 1)
                    }
                    else if (write_time.substring(0, 1).toInt() == 24) {
                        meridiem = "오전 "
                        hour = (write_time.substring(0, 1).toInt() - 12).toString()
                    }
                    else if (write_time.substring(0, 1).toInt() > 12){
                        meridiem = "오후 "
                        hour = (write_time.substring(0, 1).toInt() - 12).toString()
                    }
                    else {
                        meridiem = "오전 "
                        hour = write_time.substring(0, 1)
                    }

                    time = meridiem + hour + minute

                    orderList.add(OrderListViewItem("작성완료", time, storeAddress))
                }


                val request_accept = snapshot.child("request accept").value
                if (request_accept != null){
                    var rAccept_time:String = request_accept.toString().substring(11)
                    var meridiem:String = ""
                    var hour:String = ""
                    var minute:String = rAccept_time.substring(2, 4)

                    if (rAccept_time.substring(0, 1).toInt() == 12){
                        meridiem = "오후 "
                        hour = rAccept_time.substring(0, 1)
                    }
                    else if (rAccept_time.substring(0, 1).toInt() == 24) {
                        meridiem = "오전 "
                        hour = (rAccept_time.substring(0, 1).toInt() - 12).toString()
                    }
                    else if (rAccept_time.substring(0, 1).toInt() > 12){
                        meridiem = "오후 "
                        hour = (rAccept_time.substring(0, 1).toInt() - 12).toString()
                    }
                    else {
                        meridiem = "오전 "
                        hour = rAccept_time.substring(0, 1)
                    }

                    time = meridiem + hour + minute

                    orderList.add(OrderListViewItem( "요청수락", time, storeAddress))
                }


                val delivery = snapshot.child("delivery").value
                if (delivery != null){
                    var delivery_time:String = delivery.toString().substring(11)
                    var meridiem:String = ""
                    var hour:String = ""
                    var minute:String = delivery_time.substring(2, 4)

                    if (delivery_time.substring(0, 1).toInt() == 12){
                        meridiem = "오후 "
                        hour = delivery_time.substring(0, 1)
                    }
                    else if (delivery_time.substring(0, 1).toInt() == 24) {
                        meridiem = "오전 "
                        hour = (delivery_time.substring(0, 1).toInt() - 12).toString()
                    }
                    else if (delivery_time.substring(0, 1).toInt() > 12){
                        meridiem = "오후 "
                        hour = (delivery_time.substring(0, 1).toInt() - 12).toString()
                    }
                    else {
                        meridiem = "오전 "
                        hour = delivery_time.substring(0, 1)
                    }

                    time = meridiem + hour + minute

                    orderList.add(OrderListViewItem( "요청수락", time, storeAddress))
                }


                val complete_delivery = snapshot.child("complete delivery").value
                if (complete_delivery != null){
                    var dComplete_time:String = complete_delivery.toString().substring(11)
                    var meridiem:String = ""
                    var hour:String = ""
                    var minute:String = dComplete_time.substring(2, 4)

                    if (dComplete_time.substring(0, 1).toInt() == 12){
                        meridiem = "오후 "
                        hour = dComplete_time.substring(0, 1)
                    }
                    else if (dComplete_time.substring(0, 1).toInt() == 24) {
                        meridiem = "오전 "
                        hour = (dComplete_time.substring(0, 1).toInt() - 12).toString()
                    }
                    else if (dComplete_time.substring(0, 1).toInt() > 12){
                        meridiem = "오후 "
                        hour = (dComplete_time.substring(0, 1).toInt() - 12).toString()
                    }
                    else {
                        meridiem = "오전 "
                        hour = dComplete_time.substring(0, 1)
                    }

                    time = meridiem + hour + minute

                    orderList.add(OrderListViewItem( "요청수락", time, storeAddress))
                }

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

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                orderList.clear()

//                val storeAddress:String = snapshot.child("storeAddress").value.toString()
//                var time:String = ""
//
//                val complete_writing = snapshot.child("complete writing").value
//                if (complete_writing != null){
//                    var write_time:String = complete_writing.toString().substring(11)
//                    var meridiem:String = ""
//                    var hour:String = ""
//                    var minute:String = write_time.substring(2, 4)
//
//                    if (write_time.substring(0, 1).toInt() == 12){
//                        meridiem = "오후 "
//                        hour = write_time.substring(0, 1)
//                    }
//                    else if (write_time.substring(0, 1).toInt() == 24) {
//                        meridiem = "오전 "
//                        hour = (write_time.substring(0, 1).toInt() - 12).toString()
//                    }
//                    else if (write_time.substring(0, 1).toInt() > 12){
//                        meridiem = "오후 "
//                        hour = (write_time.substring(0, 1).toInt() - 12).toString()
//                    }
//                    else {
//                        meridiem = "오전 "
//                        hour = write_time.substring(0, 1)
//                    }
//
//                    time = meridiem + hour + minute
//
//                    orderList.add(OrderListViewItem("작성완료", time, storeAddress))
//                }
//
//
//                val request_accept = snapshot.child("request accept").value
//                if (request_accept != null){
//                    var rAccept_time:String = request_accept.toString().substring(11)
//                    var meridiem:String = ""
//                    var hour:String = ""
//                    var minute:String = rAccept_time.substring(2, 4)
//
//                    if (rAccept_time.substring(0, 1).toInt() == 12){
//                        meridiem = "오후 "
//                        hour = rAccept_time.substring(0, 1)
//                    }
//                    else if (rAccept_time.substring(0, 1).toInt() == 24) {
//                        meridiem = "오전 "
//                        hour = (rAccept_time.substring(0, 1).toInt() - 12).toString()
//                    }
//                    else if (rAccept_time.substring(0, 1).toInt() > 12){
//                        meridiem = "오후 "
//                        hour = (rAccept_time.substring(0, 1).toInt() - 12).toString()
//                    }
//                    else {
//                        meridiem = "오전 "
//                        hour = rAccept_time.substring(0, 1)
//                    }
//
//                    time = meridiem + hour + minute
//
//                    orderList.add(OrderListViewItem( "요청수락", time, storeAddress))
//                }
//
//
//                val delivery = snapshot.child("delivery").value
//                if (delivery != null){
//                    var delivery_time:String = delivery.toString().substring(11)
//                    var meridiem:String = ""
//                    var hour:String = ""
//                    var minute:String = delivery_time.substring(2, 4)
//
//                    if (delivery_time.substring(0, 1).toInt() == 12){
//                        meridiem = "오후 "
//                        hour = delivery_time.substring(0, 1)
//                    }
//                    else if (delivery_time.substring(0, 1).toInt() == 24) {
//                        meridiem = "오전 "
//                        hour = (delivery_time.substring(0, 1).toInt() - 12).toString()
//                    }
//                    else if (delivery_time.substring(0, 1).toInt() > 12){
//                        meridiem = "오후 "
//                        hour = (delivery_time.substring(0, 1).toInt() - 12).toString()
//                    }
//                    else {
//                        meridiem = "오전 "
//                        hour = delivery_time.substring(0, 1)
//                    }
//
//                    time = meridiem + hour + minute
//
//                    orderList.add(OrderListViewItem( "요청수락", time, storeAddress))
//                }
//
//
//                val complete_delivery = snapshot.child("complete delivery").value
//                if (complete_delivery != null){
//                    var dComplete_time:String = complete_delivery.toString().substring(11)
//                    var meridiem:String = ""
//                    var hour:String = ""
//                    var minute:String = dComplete_time.substring(2, 4)
//
//                    if (dComplete_time.substring(0, 1).toInt() == 12){
//                        meridiem = "오후 "
//                        hour = dComplete_time.substring(0, 1)
//                    }
//                    else if (dComplete_time.substring(0, 1).toInt() == 24) {
//                        meridiem = "오전 "
//                        hour = (dComplete_time.substring(0, 1).toInt() - 12).toString()
//                    }
//                    else if (dComplete_time.substring(0, 1).toInt() > 12){
//                        meridiem = "오후 "
//                        hour = (dComplete_time.substring(0, 1).toInt() - 12).toString()
//                    }
//                    else {
//                        meridiem = "오전 "
//                        hour = dComplete_time.substring(0, 1)
//                    }
//
//                    time = meridiem + hour + minute
//
//                    orderList.add(OrderListViewItem( "요청수락", time, storeAddress))
//                }

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

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
            }

        })


        return view
    }
}