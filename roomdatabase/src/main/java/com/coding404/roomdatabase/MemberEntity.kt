package com.coding404.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member")
data class MemberEntity(
    @PrimaryKey(autoGenerate = true) //pk가 되고, 자동증가 값을 가집니다
    val id : Int = 0,
    //아무것도 적지 않으면 컬럼명 name
    val name : String = "",
    @ColumnInfo(name ="email_id", defaultValue = "example@example.com" ) //email_id 라는 컬럼명이 됩니다.
    val email : String = ""
)