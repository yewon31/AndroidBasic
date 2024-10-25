package com.coding404.retrofit



data class ResponseData(
    val id : Int,
    val name : String,
    val info : Info,
    val data : ArrayList<Details>
)

data class Info(
    val age : Int,
    val phone : String
)

data class Details(
    val no : Int,
    val title : String
)




