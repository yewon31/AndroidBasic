package com.coding404.roomdatabase

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
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
import com.coding404.roomdatabase.databinding.CustomDialogBinding
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

            //1st - 만약 insert가 일어나면, 동기화가 일어나지 않기 때문에, 직접 변경된 데이터를 화면에 반영하도록 처리
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
        val adapter = MemberAdapter(list
                                    ,{id -> updateMember(id)}
                                    ,{id -> deleteMember(id)} )
        binding.memberRecyclerview.adapter = adapter //어댑터연결
        binding.memberRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) //리사이클러뷰 모형
    }

    //update기능 - 리사이클러뷰에 함수를 전달하고 실행
    private fun updateMember(id : Int) {
        //Toast.makeText(this, "업데이트 메서드 실행됨 $id", Toast.LENGTH_SHORT).show()
        //아이디가 있어야 update를 할 수 있자나~
        //커스텀 다이얼로그
        val customDialog = Dialog(this) //다이어로그객체
        val dialogBinding = CustomDialogBinding.inflate(layoutInflater) //xml
        customDialog.setContentView(dialogBinding.root) //다이어로그객체에 레이아웃 연결
        customDialog.setCanceledOnTouchOutside(false) //알림창 바깥을 누르더라도 꺼지지 않음

        //id에 해당하는 데이터를 가지고 와서, customdialog에 출력
        lifecycleScope.launch {
            val entity = repository.getMemberOne(id)
            if(entity != null) {
                dialogBinding.name.setText( entity.name ) //editText는 setText로 변경
                dialogBinding.email.setText( entity.email )
            }
        }
        //수정버튼을 클릭을하면, 데이터를 업데이트
        dialogBinding.yesBtn.setOnClickListener {

            val name = dialogBinding.name.text.toString()
            val email = dialogBinding.email.text.toString()

            if(name.isNotEmpty() && email.isNotEmpty() ) {

                lifecycleScope.launch {
                    repository.updateMember( MemberEntity(id, name, email) )
                    Toast.makeText(this@MainActivity, "정상 처리되었습니다", Toast.LENGTH_SHORT).show()
                    customDialog.dismiss() //창 종료
                }
            } else {
                Toast.makeText(this@MainActivity, "값을 입력하세요", Toast.LENGTH_SHORT).show()
            }

        }

        //취소버튼
        dialogBinding.noBtn.setOnClickListener { 
            customDialog.dismiss() //다이어로그 종료
        }
        customDialog.show()
    }

    //삭제기능 - 리사이클러뷰의 생성자 매개변수로 넘기고, 리사이클러뷰에서는 아이디를 이 함수로 전달
    private fun deleteMember(id : Int) {
        
        //기본 알림창
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("삭제 하시겠습니까?")
        dialog.setPositiveButton("예") {dialog, _ ->
            //삭제 진행
            lifecycleScope.launch {
                repository.deleteMember( MemberEntity(id = id)  )
                Toast.makeText(this@MainActivity, "$id 가 삭제되었습니다", Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss() //창종료
        }
        dialog.setNegativeButton("아니요") {dialog, _ ->
            dialog.dismiss() //창종료
        }

        dialog.create().show() //보여지게함

    }

    
    
    
    
    

}