package com.coding404.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao //인터페이스에 정의할 메서드를 작성합니다. JPA와 같음
interface MemberRepository {

    @Insert
    suspend fun insertMember(entity : MemberEntity)

    //select구문에서 데이터베이스가 변경되는 것을 동기화하려면 suspend키워드를 빼고 Flow로 감쌉니다.
    @Query("select * from 'member' order by id desc") //테이블명에는 ''를 붙입니다.
    fun getMember() : Flow< List<MemberEntity>>


}