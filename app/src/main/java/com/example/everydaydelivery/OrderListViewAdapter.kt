package com.example.everydaydelivery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.view.MotionEvent
import android.view.View.OnTouchListener


class OrderListViewAdapter (val context: Context, private val items: ArrayList<OrderListViewItem>): BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): OrderListViewItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.main_my_order_item, null)

        val tvState:TextView = view.findViewById(R.id.textView_state)
        val tvTimestamp:TextView = view.findViewById(R.id.textView_timestamp)
        val tvTitle:TextView = view.findViewById(R.id.textView_storeName)

        val item: OrderListViewItem = items[position]
        tvState.text = item.state
        tvTimestamp.text = item.timestamp
        tvTitle.text = item.title

        view.setOnTouchListener(OnTouchListener { v, event -> true })

        return view
    }
}
