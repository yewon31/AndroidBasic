package com.coding404.fragment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coding404.fragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmentFirst = FirstFragment() //A프래그먼트
    private val fragmentSecond = SecondFragment() //B프래그먼트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //보여질 프래그먼트 지정
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_fragment, FirstFragment() ) //프래그먼트가 보여질 위치, 보여질 대상
//            .commit()
        changeFragment()

        //A버튼 이벤트
        binding.btn1.setOnClickListener {
            changeFragment("first")
        }
        //B버튼 이벤트
        binding.btn2.setOnClickListener {
            changeFragment("second")
        }
        

    }

    private fun changeFragment(data : String = "first") {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left) //애니메이션

        when(data) {
            "first" -> {
                transaction.replace(R.id.main_fragment, fragmentFirst)
            }
            "second" -> {
                transaction.replace(R.id.main_fragment, fragmentSecond)
            }
            //...
        }
        transaction.commit()
    }



}