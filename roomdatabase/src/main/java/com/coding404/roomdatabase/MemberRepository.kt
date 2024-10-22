package com.coding404.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao //인터페이스에 정의할 메서드를 작성합니다. JPA와 같음
interface MemberRepository {

    @Insert
    suspend fun insertMember(entity : MemberEntity)

    @Query("select * from 'member'") //테이블명에는 ''를 붙입니다.
    suspend fun getMember() : List<MemberEntity>


}