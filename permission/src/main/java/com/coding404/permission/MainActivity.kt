package com.coding404.permission

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Random
import kotlin.math.log

const val TAG = "myLog"

class MainActivity : AppCompatActivity() {

    //단일 권한 런쳐
    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {isGranted ->

        if(isGranted) { //사용자가 허용을 누른 경우 true
            Toast.makeText(this, "권한이 허용되었습니다", Toast.LENGTH_SHORT).show()

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)


        } else { //거절을 누른경우에 false
            //Toast.makeText(this, "권한이 거절되었습니다", Toast.LENGTH_SHORT).show()

            AlertDialog.Builder(this)
                .setTitle("권한요청")
                .setMessage("앱이 정상 가동되기 위해서 카메라 권한이 필요 합니다. \n권한 탭에서 카메라 권한을 확인해 주세요.")
                .setPositiveButton("확인") { _, _ ->
                    //인텐트로 설정창을 열어줌
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.setData(Uri.fromParts("package", packageName, null))
                    startActivity(intent)
                }
                .setNegativeButton("취소") {_, _ ->
                    //앱을 종료
                    finish()
                }
                .show()

        }

    }

    //카메라 런쳐
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        //사용자가 사진을 찍고, 확인을 눌르면 동작
        if(result.resultCode == Activity.RESULT_OK) {
            try {

                //단 사진첩에 사진을 저장해주진 않습니다.
                val thumbnail = result.data!!.extras!!.get("data") as Bitmap //bitmap타입으로 형변환
                findViewById<ImageView>(R.id.permission_img).setImageBitmap(thumbnail)

            } catch(e: Exception) {
                e.printStackTrace()
            }

        }

    }

    //다중 권한 런쳐
    private val galaryPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {maps ->

//        maps.forEach { entry ->
//            val permission = entry.key
//            val isGranted = entry.value
//            Log.d(TAG, "$permission, $isGranted")
//        }

        //34버전 이상
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {

            val video = maps.get(Manifest.permission.READ_MEDIA_VIDEO)
            val img = maps.get(Manifest.permission.READ_MEDIA_IMAGES)
            val audio = maps.get(Manifest.permission.READ_MEDIA_AUDIO)

            //애뮬레이터에 사진을 직접 넣으려면,
            if(video == true && img == true && audio == true) {
                //갤러리에 접근함
                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                galleryLauncher.launch(gallery)

            } else {
                Toast.makeText(this, "사진 또는 동영상 권한이 거부되었습니다...", Toast.LENGTH_SHORT).show()
            }
        }

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            val galary = maps.get(Manifest.permission.READ_EXTERNAL_STORAGE)
            //갤러리로 접근...
        }
    }

    //갤러리에 접근하는 런쳐
    private  var galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->

        //사용자가 사진을 선택하면 동작
        if (result.resultCode == Activity.RESULT_OK) {

            try {
                val selectedImageUri = result.data?.data
                //uri값을 기반으로 갤러리에서 bitmap이미지를 요청해서 받아옴
                val selectedImage = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                findViewById<ImageView>(R.id.permission_img).setImageBitmap(selectedImage)

            } catch(e: Exception) {
                e.printStackTrace()
            }


        }

    }


    //알림 권한 런쳐
    private val notifyPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {isGranted ->

        if(isGranted) {
            Toast.makeText(this, "알림 권한 승인", Toast.LENGTH_SHORT).show()
            //알림을 보내기
            broadcastNotification()
        } else {
            Toast.makeText(this, "알림 권한 거부", Toast.LENGTH_SHORT).show()
            //....거부에 대한 적절한 처리
        }

    }

    //알림 보내기 기능
    private fun broadcastNotification() {

        val channelId = "myChannel" //앱에서 고유한 채널명
        val channelName = "my channel one"

        //알림 매니저
        val manager = getSystemService(NotificationManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //API 26
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel) //알림 매니저에 채널 등록

            //알림객체 생성
            val notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle("알림 제목") //필수
                .setContentText("알림 내용") //필수
                .setSmallIcon(R.drawable.ic_launcher_background) //필수
                .setAutoCancel(true) //클릭시 알림창에서 제거 - 등등등 옵션..
                //.setContentIntent() //펜딩인텐트 를 전달하면, 앱클릭시, 특정 화면으로 이동
                .build()

            manager.notify( Random().nextInt(), notification ) //고유알림번호(중복이면 알림x)

        } else { //26버전 하위, 알림채널이 x

            val notification = NotificationCompat.Builder(this)
                .setContentTitle("알림 제목") //필수
                .setContentText("알림 내용") //필수
                .setSmallIcon(R.drawable.ic_launcher_background) //필수
                .setAutoCancel(true) //클릭시 알림창에서 제거 - 등등등 옵션..
                .build()

            manager.notify( Random().nextInt(), notification)
        }


    }







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //단일권한
        val btn1 = findViewById<Button>(R.id.permission_one_btn)
        btn1.setOnClickListener {

            //카메라 권한이 허용되어 있는지 확인
            val status = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA )
            //사용자가 이전에 권한 요청을 거부했으나, 혹시 다시 요청을 보낼 수 있는 경우 true
            //val result = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)

            if(status == PackageManager.PERMISSION_GRANTED) {  //권한이 허용된 경우

                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(cameraIntent)

            } else { //권한이 허용되어 있지 않은 경우
                //권한 요청
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }


        }
        //다중권한
        val btn2 = findViewById<Button>(R.id.permission_multi_btn)
        btn2.setOnClickListener {

            //34이상
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                val arr = arrayOf(Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO)
                galaryPermissionLauncher.launch(arr)
            }

            //33이하
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
                var arr = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                galaryPermissionLauncher.launch(arr)
            }
        }

        //알림 받기
        val btn3 = findViewById<Button>(R.id.noti_btn)
        btn3.setOnClickListener {


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notifyPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else { //기기버전이 낮으면, 알림권한이 필요 없음
                //알림 메서드를 호출
                broadcastNotification()
            }


        }




    }
}