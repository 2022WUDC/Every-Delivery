package com.example.everydaydelivery

// 파이어베이스에서 받아 올 요청서
//class Datas(val CustomAdd: String, val ArriveAdd: String)

//파이어베이스면 아마 이렇게 해야 할 듯..
//// 간략요청서에 들어갈 데이터
data class Order(
    val deliveryPrice : String? = null,
    val menu : String? = null,
    val menuPrice : String? = null,
    val orderRequest : String? = null,
    val storeAddress : String? = null,
    val complete_writing : String? = null,
    val totalPrice : String? = null,
    val uid : String? = null
)

data class CurrentOrder(
    val complete_writing : String? = null,
    val request_accept : String? = null,
    val delivery : String? = null,
    val complete_delivery : String? = null,
    val storeAddress : String? = null
)