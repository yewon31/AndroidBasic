package com.coding404.recyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding404.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //리사이클러 뷰 영역에 adapter를 연결.
        val adapter = TodoAdapter( TodoList.getTodoList() )
        binding.reTodolist.adapter = adapter
        binding.reTodolist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false )
    }
}