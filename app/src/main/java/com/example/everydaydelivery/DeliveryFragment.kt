package com.example.everydaydelivery

import android.content.Intent
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class DeliveryFragment : Fragment() {
    companion object{
        fun newInstance() : DeliveryExFragment{
            return DeliveryExFragment()
        }
    }

    lateinit var uid : String
    lateinit var cardview_request:CardView
    lateinit var cardView_chat: CardView
    lateinit var cardview_myPage: CardView

    private lateinit var database: DatabaseReference
    private var order: ArrayList<Order> = arrayListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 리사이클러뷰 연결해주기
        database = Firebase.database.reference
        val view = inflater.inflate(R.layout.fragment_delivery, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_delivery)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecyclerViewAdapter()

        cardview_request = view.findViewById(R.id.cardview_request)
        cardView_chat = view.findViewById(R.id.cardview_chat)
        cardview_myPage = view.findViewById(R.id.cardview_myPage)

        cardview_request.setOnClickListener {
            var intent = Intent(requireContext(), DeliveryActivity::class.java)
            startActivity(intent)
        }

        cardView_chat.setOnClickListener {
            var intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("switch_checked", "true")
            startActivity(intent)
        }

        cardview_myPage.setOnClickListener {
            var intent = Intent(requireContext(), DeliveryMyPageActivity::class.java)
            startActivity(intent)
        }

        return view

    }

    // 리사이클러뷰 사용
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>(){

        private val order = ArrayList<Order>()
        private var tempArrayList = ArrayList<Order>()

        init {
            val allOrder = Firebase.auth.uid
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
                    notifyDataSetChanged()
                }
            })
            // 내가 추가한거
            tempArrayList.addAll(order)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            return CustomViewHolder(
                LayoutInflater.from(context).inflate(R.layout.delivery_sheet, parent, false)
            )
        }

        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var deliveryPrice: TextView = itemView.findViewById(R.id.tv_deliveryprice)
            var menu: TextView = itemView.findViewById(R.id.tv_menu)
            var totalPrice: TextView = itemView.findViewById(R.id.tv_totalprice)
            var store: TextView = itemView.findViewById(R.id.arrive_add)
            var time : TextView = itemView.findViewById(R.id.tv_time)
            var uid : TextView = itemView.findViewById(R.id.text_uid)
            //var button : Button = itemView.findViewById(R.id.accept_button)

        }
        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.deliveryPrice.text = order[position].deliveryPrice
            holder.menu.text = order[position].menu
            holder.totalPrice.text = order[position].totalPrice
            holder.store.text = order[position].storeAddress
            holder.time.text = order[position].complete_writing
            holder.uid.text = order[position].uid

            holder.itemView.setOnClickListener {
                val intent = Intent(context, MessageActivity::class.java)
                intent.putExtra("destinationUid", holder.uid.text)
                intent.putExtra("switch_checked", "true")
                context?.startActivity(intent)
            }

        }

        override fun getItemCount(): Int {
            return order.size
        }

    }


}