package com.example.everydaydelivery

import android.content.ComponentCallbacks
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
//
//class DeliveryAdapter(val deliveryList:ArrayList<Datas>) : RecyclerView.Adapter<DeliveryAdapter.CustomViewHolder>()
class DeliveryAdapter(private val context:Context):RecyclerView.Adapter<DeliveryAdapter.CustomViewHolder>()
{
//    private var orderList = mutableListOf<String>()
//    private ArrayList<Datas> arrayList;
//
//
//    fun setOrderData(data:MutableList<Order>){
//        orderList = data
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryAdapter.CustomViewHolder {
//        //val view = LayoutInflater.from(parent.context).inflate(R.layout.delivery_sheet, parent, false)
//        val view = LayoutInflater.from(context).inflate(R.layout.delivery_sheet, parent, false)
//        return CustomViewHolder(view)   // view가 아이템 끌고 어뎁터에 붙여주고, 그 뷰를 뷰홀더에 전달
//    }
//
//    override fun onBindViewHolder(holder: DeliveryAdapter.CustomViewHolder, position: Int) {
//        // 실제 만들어진 뷰 가져다가 연결
//        //holder.arrive_add.text = deliveryList[position].ArriveAdd
//        val order : Order = orderList[position]
//        holder.time.text = Order.completewriting
//        holder.menu.text = Order.menu
//
//
//        //var viewHolder = (holder as RecyclerView.ViewHolder).itemView
//        //var arriveaddress : TextView
//
//        //arriveaddress = viewHolder.findViewById(R.id.arrive_add)
//    }
//
//    override fun getItemCount(): Int {
//        // 총 요청서 개수만큼 나오기
//        return deliveryList.size
//    }
//
//
//    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        // 간략한 요청서에 보일 항목들 id연결
//        val arrive_add = itemView.findViewById<TextView>(R.id.arrive_add) // 도착지
//    }

    //참고해서 다시 하는 부분
    lateinit var dtextview1 : TextView
    lateinit var dtextview2 : TextView
    lateinit var dtextview3 : TextView

    // 파이어베이스
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId: String
    private lateinit var database : FirebaseDatabase
    lateinit var ref : DatabaseReference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryAdapter.CustomViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.delivery_sheet, parent, false)
        val view = LayoutInflater.from(context).inflate(R.layout.delivery_sheet, parent, false)
        return CustomViewHolder(view)   // view가 아이템 끌고 어뎁터에 붙여주고, 그 뷰를 뷰홀더에 전달
    }

    override fun onBindViewHolder(holder: DeliveryAdapter.CustomViewHolder, position: Int) {
        // 실제 만들어진 뷰 가져다가 연결
        //holder.arrive_add.text = deliveryList[position].ArriveAdd
        //val order : Order = orderList[position]
        //holder.time.text = Order.completewriting
        //holder.menu.text = Order.menu


        //var viewHolder = (holder as RecyclerView.ViewHolder).itemView
        //var arriveaddress : TextView

        //arriveaddress = viewHolder.findViewById(R.id.arrive_add)
    }

    override fun getItemCount(): Int {
        // 총 요청서 개수만큼 나오기
        //return deliveryList.size
        return 0    // 나중에 지울 것
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 간략한 요청서에 보일 항목들 id연결
        val arrive_add = itemView.findViewById<TextView>(R.id.arrive_add) // 도착지
    }

}