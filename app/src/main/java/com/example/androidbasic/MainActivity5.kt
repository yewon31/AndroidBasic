package com.example.androidbasic

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar


const val TAG = "myLog" //상수

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main5)

        //xml에 위젯을 얻음
        var genderBtn : Button = findViewById(R.id.gender_btn)
        var genderText : TextView = findViewById(R.id.gender_text)

        var nameEdit : EditText = findViewById(R.id.name_edit)
        var nameText : TextView = findViewById(R.id.name_text)

        var birthBtn : Button = findViewById(R.id.birth_btn)
        var birthText : TextView = findViewById(R.id.birth_text)


        genderBtn.setOnClickListener {
            //Toast.makeText(this, "성별버튼 클릭", Toast.LENGTH_SHORT).show()

//            AlertDialog.Builder(this)
//                .setIcon(R.drawable.ic_launcher_background)
//                .setTitle("제목입니다")
//                .setMessage("종료하시겠습니까?")
//                .setPositiveButton("예") {dialog, which ->
//                    Toast.makeText(this, "예 버튼 클릭", Toast.LENGTH_SHORT).show()
//                }
//                .setNegativeButton("아니요") { dialog, which ->
//                    Toast.makeText(this, "아니오 버튼 클릭", Toast.LENGTH_SHORT).show()
//                    dialog.dismiss() //다이어로그 종료
//                }
//                .show()

            AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher_background)
                .setTitle("성별을 선택하세요")
                .setItems( arrayOf("여자", "남자") ) { dialog, which ->
                    //which는 인덱스를 반환해줍니다.
                    when(which) {
                        0 -> {
                            genderText.text = "여자"
                        }
                        1 -> {
                            genderText.text = "남자"
                        }
                    }
                    genderText.visibility = View.VISIBLE
                    dialog.dismiss()
                }
                .setNegativeButton("끄기") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()



        }

        //editText가 변경할때 마다 동작하는 이벤트
        //에딧 텍스트에서 사용자가 클릭하는 버튼을 감지하려면 setOnKeyListener이벤트를 쓰면됩니다.
        /*
        nameEdit.addTextChangedListener {
            //Toast.makeText(this, "클릭", Toast.LENGTH_SHORT).show()

            var text = nameEdit.text.toString() //사용자가 입력한값
            nameText.text = text

            //textView의 visibility속성 변경
            nameText.visibility = View.VISIBLE
            //글씨 색상 변경
            //nameText.setTextColor( Color.BLUE ) //글씨색상
            //nameText.setBackgroundColor( Color.GREEN ) //백그라운드색상

            val color1 = ContextCompat.getColor(this, R.color.myColor1 )
            val color2 = ContextCompat.getColor(this, R.color.myColor4 )
            nameText.setTextColor(color2)
            nameText.setBackgroundColor(color1)

        }
        */

        //사용자가 키보드를 클릭하면 동작
        nameEdit.setOnKeyListener { v, keyCode, event ->

            Log.d(TAG, "onCreate: $event")

            if(event.action == KeyEvent.ACTION_DOWN ) {
                if(event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    nameText.text = nameEdit.text.toString()
                    nameText.visibility = View.VISIBLE
                }
            }

            //true //이벤트 전파 중단(버튼UI 안닫힘)
            false//이벤트 전파 (버튼UI 닫힘)
        }

        //생일선택 버튼
        birthBtn.setOnClickListener {

            //오늘날짜 구하기
            val cal : Calendar = Calendar.getInstance()
            val yy = cal.get( Calendar.YEAR )
            val mm = cal.get( Calendar.MONTH)
            val dd = cal.get( Calendar.DAY_OF_MONTH)

            //로그창
            //Log.d(TAG, "onCreate: $year, $month, $day")
            val dialog = DatePickerDialog(this,
                { view, year, month, dayOfMonth ->
                    //ok버튼을 누를때 동작함

                    //사용자가 선택한 날짜 문자형식으로 저장
                    val selected = "$year-${month + 1}-$dayOfMonth"
                    Log.d(TAG, "onCreate: $selected")

                    //현재시간(밀리초) - 사용자가 선택한 값(밀리초) => 년도로 치환 => 나이를 구함
                    val nowMillis = System.currentTimeMillis() //1970~지금 까지의 밀리초를

                    val sdf = SimpleDateFormat("yyyy-MM-dd") //날짜형식
                    val date = sdf.parse(selected)
                    val millis = date.time //밀리초를 구함

                    val result = (nowMillis - millis) / 1000 / 60 / 60 / 24 / 365 //년도로치환
                    Log.d(TAG, "onCreate: $result")

                    //나이 숨김 영역에 result + "세" 형식으로 값을 넣고 보여주기.
                    birthText.text = "$result 세"
                    birthText.visibility = View.VISIBLE

                }
                ,yy
                ,mm
                ,dd
            )

            dialog.datePicker.maxDate = System.currentTimeMillis() //현재 날짜 다음 값을 선택하지 못하게
            dialog.show() //다이얼로그 show




        }



    }

    //xml에서 이벤트 연결하기
    fun handleClick(v : View) {
        Toast.makeText(this, "제목을 클릭했습니다", Toast.LENGTH_SHORT).show()
    }


}