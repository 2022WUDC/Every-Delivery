package com.example.everydaydelivery

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class DeliveryExFragment : Fragment() {
    //, androidx.appcompat.widget.SearchView.OnQueryTextListener


    companion object{
        fun newInstance() : DeliveryExFragment{
            return DeliveryExFragment()
        }
    }

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
        val view = inflater.inflate(R.layout.fragment_delivery_ex, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_order)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecyclerViewAdapter()

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
        }
        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.deliveryPrice.text = order[position].deliveryPrice
            holder.menu.text = order[position].menu
            holder.totalPrice.text = order[position].totalPrice
            holder.store.text = order[position].storeAddress
            holder.time.text = order[position].complete_writing
            //Toast.makeText(activity, "하나 성공 order개수: ${order.size} 메뉴: ${holder.menu.text} ", Toast.LENGTH_SHORT ).show()
            //Log.d(TAG, "시간알림: ${holder.time.text}")

        }

        override fun getItemCount(): Int {
            return order.size
        }



    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.search_menu, menu)
//        val search = menu?.findItem(R.id.search_menu)
//        val searchView = search.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
//        searchView?.setOnQueryTextListener(this)
//
//        super.onCreateOptionsMenu(menu, inflater)
//
//    }
//
//
//    override fun onQueryTextSubmit(query: String?): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun onQueryTextChange(newText: String?): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    private fun searchDatabase(query: String){
//        val searchQuery = "%$query%"
//
//        mainViewModel.searchDatabase(searchQuery).observe(this,{list ->
//            list.let{
//                myAdapter.setData(it)
//            }
//        })
//    }


}