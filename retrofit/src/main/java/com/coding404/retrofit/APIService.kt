package com.coding404.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    //https://square.github.io/retrofit/
    @GET("8da84b18-4d06-4da0-b43c-4df41188c927") //베이스URL + 요청주소
    //fun getData() : Call<ResponseData> //Call<저장할객체>
    suspend fun getData() : Response<ResponseData> //코루틴을 사용하는 방법


}