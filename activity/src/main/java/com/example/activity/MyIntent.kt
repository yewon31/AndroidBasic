package com.example.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.activity.databinding.ActivityMyIntentBinding

class MyIntent : AppCompatActivity() {

    private lateinit var binding : ActivityMyIntentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_intent)
        binding = ActivityMyIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //엑스트라 데이터 받기
//        var data1 = intent.getStringExtra("data1")
//        var data2 = intent.getIntExtra("data2", 0)
//        Toast.makeText(this, "넘어온 데이터 $data1, $data2 입니다", Toast.LENGTH_SHORT).show()

        //엑티비티 콜백
        binding.returnBtn.setOnClickListener {

            intent.putExtra("callback", "리턴할 데이터")
            setResult(RESULT_OK, intent) //성공실패에 대한 값, 인텐트
            finish() //현재엑티비티의 종료
        }


    }
}