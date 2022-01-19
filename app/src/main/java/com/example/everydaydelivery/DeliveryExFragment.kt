package com.example.everydaydelivery

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import androidx.recyclerview.widget.RecyclerView.ViewHolder as ViewHolder




class DeliveryExFragment : Fragment() {

//    lateinit var firebaseAuth: FirebaseAuth
//    lateinit var storedVerificationId: String
//    private lateinit var database: FirebaseDatabase
//    lateinit var ref: DatabaseReference

    companion object{
        fun newInstance() : DeliveryExFragment{
            return DeliveryExFragment()
        }
    }

    private lateinit var database: DatabaseReference
    private var order: ArrayList<Order> = arrayListOf()

    ///
//    lateinit var dtextview1 : TextView
//    lateinit var dtextview2 : TextView
//    lateinit var dtextview3 : TextView
//
//    lateinit var recyclerview_order : RecyclerView
//    lateinit var orderlist : ArrayList<Order>
//    lateinit var mylist : ArrayList<String>
    ///
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_delivery_ex, container, false)
//
//        orderlist = arrayListOf<Order>()
//        mylist = arrayListOf<String>()
//
//        // 파이어베이스 인증 객체
//        firebaseAuth = FirebaseAuth.getInstance()
//
//        //database = Firebase.database.reference 를 써야 하나?
//        database = FirebaseDatabase.getInstance()
//        val dbReference = database.reference
//        val userRef = dbReference.child("orders")
//        ref = userRef
//
//        dtextview1 = view.findViewById(R.id.arrive_add)
//        dtextview2 = view.findViewById(R.id.tv_deliveryprice)
//        dtextview3 = view.findViewById(R.id.tv_name)
//
//        // 홈의 리사이클러뷰와 연결
//        recyclerview_order = view.findViewById(R.id.recyclerview_order)
//        recyclerview_order.adapter = RecyclerViewAdapter()
//        recyclerview_order.layoutManager = LinearLayoutManager(activity)

        database = Firebase.database.reference
        val view = inflater.inflate(R.layout.fragment_delivery_ex, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_order)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecyclerViewAdapter()

        return view
        // 여기까지는 맞게 한 듯 하다
    }

    // 리사이클러뷰 사용
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>(){

        init {
            val allOrder = Firebase.auth.uid
            FirebaseDatabase.getInstance().reference.child("orders").addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    order.clear()
                    for (data in snapshot.children) {
                        val item = data.getValue<Order>()

                        order.add(item!!)
                    }
                    notifyDataSetChanged()
                }
            })
        }



        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val store: TextView = itemView.findViewById(R.id.arrive_add)
            val price: TextView = itemView.findViewById(R.id.tv_deliveryprice)
            val name: TextView = itemView.findViewById(R.id.tv_name)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            return CustomViewHolder(
                LayoutInflater.from(context).inflate(R.layout.delivery_sheet, parent, false)
            )
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.store.text = order[position].arriveadd
            holder.price.text = order[position].deliveryPrice.toString()
            holder.name.text = order[position].name
        }

        override fun getItemCount(): Int {
            return order.size
        }
    }

//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            var view = LayoutInflater.from(parent.context).inflate(R.layout.delivery_sheet,parent,false)
//            return ViewHolder(view)
//        }
//
//        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//        // view와 실제 데이터 연결
//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//            var viewHolder = (holder as ViewHolder).itemView
//            var store : TextView
//            var price : TextView
//            var name :  TextView
//
//            store = viewHolder.findViewById(R.id.arrive_add)
//            price = viewHolder.findViewById(R.id.tv_deliveryprice)
//            name = viewHolder.findViewById(R.id.tv_name)
//
//            // 리사이클러뷰 아이템 정보
//            store.text = orderlist!![position].arriveadd
//            price.text = orderlist!![position].deliveryPrice.toString()
//            name.text = orderlist!![position].name
//
//        }
//
//        override fun getItemCount(): Int {
//            return orderlist.size
//        }
//
//    }
//
//    // 파이어베이스에서 데이터 불러오기
//    fun loadData(){
//        // 주문서 불러오기
//        if (order)
//    }


}