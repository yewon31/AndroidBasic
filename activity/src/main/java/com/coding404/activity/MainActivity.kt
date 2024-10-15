package com.coding404.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coding404.activity.databinding.ActivityMainBinding
import kotlin.math.log

const val TAG = "myLog"

class MainActivity : AppCompatActivity() {

    //private var binding : ActivityMainBinding? = null
    private lateinit var binding: ActivityMainBinding

    //전역으로 사용할 변수
    private var cnt = 1

    //엑티비티 콜백을 받기 위한 멤버변수
    var activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult() ) {

        Log.d(TAG, ": 콜백실행됨 $it")
        if(it.resultCode == Activity.RESULT_OK) {

            //성공시 실행할 코드를 동작 - data를 받는게 가능함
            var callback = it.data?.getStringExtra("callback")
            Toast.makeText(this, "콜백데이터 :$callback", Toast.LENGTH_SHORT).show()
        }




    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        //기존방식
//        var tvView : TextView = findViewById(R.id.ex_text)
//        tvView.setOnClickListener {  }

        //뷰바인딩방식
        binding.exText.setOnClickListener {
            Toast.makeText(this, "클릭", Toast.LENGTH_SHORT).show()
        }

        //실습
        binding.increaseBtn.setOnClickListener {
            cnt = binding.increaseText.text.toString().toInt().plus(1)
            binding.increaseText.text = cnt.toString()
        }

        //인텐트전환하기 -
        binding.changeBtn.setOnClickListener {
//            val intent = Intent(this, MyIntent::class.java) //컨텍스트, 실행시킬 클래스
//            intent.putExtra("data1", "홍길동")
//            intent.putExtra("data2", 20)
//            startActivity(intent)

            //엑티비티 콜백런쳐
            val intent = Intent(this, MyIntent::class.java)
            activityLauncher.launch(intent)
            //finish() //엑티비티종료

        }


        //암시적인텐트
        binding.changeBtn2.setOnClickListener {

            //예시 - 연락처 앱을 실행시킬 수 있음
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people") )
//            startActivity(intent)

            //구글 지도
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo: 38.00, -35.03") )
            startActivity(intent)
        }





        Log.d(TAG, "onCreate: 온크리에이트")
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: 엑티시비 생성후 시작")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: 엑티비티 시작 재개")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: 엑티비티 일시정지")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: 엑티비티 정지")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: 엑티비티 재시작") //Restart -> start -> resume
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: 엑티비티 종료")
    }


    //엑티비티의 상태를 저장할 때
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //onStop이후에 동작을 하고 bundle에 값을 저장할 수 있음.
        Log.d(TAG, "onSaveInstanceState: 실행됨")

        outState.putString("data1", "홍길동")
        outState.putInt("data2", cnt)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //onStart이후에 실행됨
        Log.d(TAG, "onRestoreInstanceState: 실행됨")

        var name = savedInstanceState.getString("data1")
        cnt = savedInstanceState.getInt("data2")
        binding.increaseText.text = cnt.toString() //값의 유지
    }





}