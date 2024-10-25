package com.coding404.retrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.coding404.retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

const val TAG = "myLog"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val retrofit : Retrofit = Constants.getInstance() //레트로핏 객체 생성
//        val client = retrofit.create(APIService::class.java) //레트로핏 인터페이스 연결
//
//        //Callback에 제네릭에는 응답받을 데이터 타입
//        client.getData().enqueue( object : Callback<ResponseData> {
//            //응답이 성공시
//            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
//                if(response.isSuccessful) {
//                    //응답 성공시
//                    val body = response.body()
//                    Log.d(TAG, "onResponse: $body")
//                } else {
//                    //응답 실패시
//                    Log.d(TAG, "onResponse: ${response.errorBody()}")
//                }
//            }
//            //응답 실패시
//            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                Log.d(TAG, "onResponse: $t")
//            }
//        })



        showProgress() //프로그래스바를 호출
        lifecycleScope.launch(Dispatchers.IO) {

            val retrofit = Constants.getInstance()
            val client = retrofit.create(APIService::class.java)

            val response : Response<ResponseData> = client.getData()

            if(response.isSuccessful) {
                val body = response.body()
                Log.d(TAG, "onCreate: $body") //화면에 대해서 데이터 처리..
            } else {
                val body = response.errorBody()
                Log.d(TAG, "onCreate: $body")
            }

            //메인쓰레드 안에서 UI업데이트
            launch(Dispatchers.Main) {
                cancleProgress()
            }

        }


    }


    //프로그래스바 활성화
    private fun showProgress() {
        binding.loadingBar.visibility = View.VISIBLE
        binding.dataText.visibility = View.INVISIBLE
    }


    private fun cancleProgress() {
        binding.loadingBar.visibility = View.INVISIBLE
        binding.dataText.visibility = View.VISIBLE
    }




}