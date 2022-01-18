package com.example.everydaydelivery

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
//
class DeliveryAdapter(val deliveryList:ArrayList<Datas>) : RecyclerView.Adapter<DeliveryAdapter.CustomViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.delivery_sheet, parent, false)
        return CustomViewHolder(view)   // view가 아이템 끌고 어뎁터에 붙여주고, 그 뷰를 뷰홀더에 전달
    }

    override fun onBindViewHolder(holder: DeliveryAdapter.CustomViewHolder, position: Int) {
        // 실제 만들어진 뷰 가져다가 연결
        holder.arrive_add.text = deliveryList[position].ArriveAdd


        //var viewHolder = (holder as RecyclerView.ViewHolder).itemView
        //var arriveaddress : TextView

        //arriveaddress = viewHolder.findViewById(R.id.arrive_add)
    }

    override fun getItemCount(): Int {
        // 총 요청서 개수만큼 나오기
        return deliveryList.size
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 간략한 요청서에 보일 항목들 id연결
        val arrive_add = itemView.findViewById<TextView>(R.id.arrive_add) // 도착지
    }

}