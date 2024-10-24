package com.coding404.roomdatabase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding404.roomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var database : MemberDatabase
    private lateinit var repository : MemberRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //JPA와 비슷
        //엔티티 생성
        //sql문 실행 해주는 interface생성
        //싱글톤 형식의 MemberDatabase생성
        
        //데이터베이스 객체 생성
        database = MemberDatabase.getInstance(applicationContext)
        repository = database.memberRepository()

        //add버튼에 이벤트
        binding.addBtn.setOnClickListener {
            insertMember()
        }
        //멤버조회
        getMember()
        
        
    }
    
    private fun insertMember() {
        //값에 대한 유효성 검사
        var name = binding.nameValue.text.toString()
        var email = binding.emailValue.text.toString()

        when {
            name.isEmpty() -> Toast.makeText(this, "이름은 필수 입니다", Toast.LENGTH_SHORT).show()
            email.isEmpty() -> Toast.makeText(this, "이메일은 필수 입니다", Toast.LENGTH_SHORT).show()
            else -> { //빈값이 아니라면

                lifecycleScope.launch {
                    val entity = MemberEntity(name = name, email = email)
                    repository.insertMember(entity)
                    //알림창
                    Toast.makeText(this@MainActivity, "저장되었습니다", Toast.LENGTH_SHORT ).show()
                    //edit텍스트는 초기화
                    binding.nameValue.text.clear()
                    binding.emailValue.text.clear()
                    //포커싱도 제거
                    binding.nameValue.clearFocus()
                    binding.emailValue.clearFocus()
                }
            }

            
        }


    }

    private fun getMember() {

        lifecycleScope.launch {

            //1st
            //val list : List<MemberEntity> = repository.getMember()
            //setUpRecyclerView(list as ArrayList )

            //2nd
            //데이터베이스에 변화가 일어나면, collect가 데이터를 수집해서 동작하게 됩니다.
            //단점 - 전체데이터를 가지고 나와서, 화면에 반영을 해야하기 때문에, 리사이클러뷰를 다시 변경해야합니다.
            repository.getMember().collect() {

                setUpRecyclerView(it as ArrayList )
            }

            //리사이클러뷰 어댑터에 list를 전달

        }
    }

    //리사이클러뷰 초기값 세팅
    private fun setUpRecyclerView(list: ArrayList<MemberEntity>) {
        val adapter = MemberAdapter(list)
        binding.memberRecyclerview.adapter = adapter //어댑터연결
        binding.memberRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) //리사이클러뷰 모형
    }

    
}