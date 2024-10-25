package com.coding404.retrofit

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//싱글톤 모형의 클래스 선언
object Constants {

    //연결할 주소의 베이스 URL
    const val BASE_URL = "https://run.mocky.io/v3/" //베이스 유알엘은 반드시 /로 끝나도록 선언

    //GSON의 _ 바를 카멜표기법으로 컨버팅해서 저장시키도록 선언
    val gson = GsonBuilder().setFieldNamingPolicy( FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

    fun getInstance() : Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) //유알엘 주소
            .addConverterFactory(GsonConverterFactory.create( gson )) //응답 데이터를 어떤 모형으로 컨버팅
            .build()

        return retrofit
    }

}