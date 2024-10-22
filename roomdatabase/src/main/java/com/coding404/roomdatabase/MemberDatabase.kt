package com.coding404.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MemberEntity::class /* */] , version = 1 ) //실행시킬 엔티티를 정의하고, 초기 데이터베이스 버전 선언(엔티티가 만약 변경된다면 version이 되야 됩니다.)
abstract class MemberDatabase : RoomDatabase() { //실행시에 자바파일로 구현제를 자동 생성합니다.

    //사용할 repository를 추상메드로 선언합니다.
    abstract fun memberRepository() : MemberRepository //실행시에 자바파일로 구현체를 자동 생성합니다.

    //싱글톤 패턴의 데이터베이스 생성
    companion object {

        @Volatile //kotlin.jvm타입 - 멀티스레드 환경에서, 한스레드에 의해서 값이 지정되면, 다른스레드에 즉시 보이도록 처리.
        private var instance : MemberDatabase? = null

        fun getInstance(context: Context) : MemberDatabase {
            //여러스레드가 동시에 한 시점에 접근할 때, 하나의 스레드만 접근이 가능하도록 동기화를 보장합니다.
            synchronized(this) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context,
                                                    MemberDatabase::class.java, //실행시킬 데이터베이스
                                                    "member-database") //데이터베이스명
                        //room데이터베이스 스키마에 변경이 일어나면, 변경이 일어난 내용을 작성을 해야되는데,
                        // 이 메서드를 사용하면, 데이터베이스를 삭제하고, 처음부터 다시 만들어줍니다.
                                                    .fallbackToDestructiveMigration()
                                                    .build()
                }   //end if
                return instance!!
            }
        }

    }








}