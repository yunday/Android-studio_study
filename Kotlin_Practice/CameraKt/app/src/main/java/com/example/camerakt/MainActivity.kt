package com.example.camerakt

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.Permission
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1 // 카메라 사진 촬영 요청코드
    lateinit var curPhotoPath: String // 문자열 형태의 사진 경로 값
    // lateinit var 늦은 초기화 (내 문법 노트 참고!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPermission() // 최초에 권한을 체크하는 메소드 수행

        btn_camera.setOnClickListener {
            takeCapture() // 기본 카메라 앱을 실행하여 사진 촬영
        }
    }

    // 카메라 촬영
    private fun takeCapture() {
        // 기본 카메라 앱 실행
        // 하나의 앱을 실행하는거이므로 intent 사용
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{ takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try{
                    createImageFile()
                } catch (ex: IOException){
                    null
                }
                // 보안관련 코드
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.camerakt.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE) // 메인액티비티에서 서브액티비티로 갔다가 서브액티비에서 결과값을 메인액티비티에서 받을 수 있음
                    // 사진결과물을 다시 받아와야하므로 ForResult가 붙은놈을 사용한다
                }
            }
        }
    }

    // 이미지 파일 생성
    private fun createImageFile(): File? {
        // 파일 이름 구분하기 위해서 날짜로 구분
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) // 시간 표기 포맷
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
            .apply { curPhotoPath = absolutePath }
    }

    // 테드 퍼미션 설정
    private fun setPermission() {
        val permission = object : PermissionListener{
            override fun onPermissionGranted() { // 설정해놓은 위험 권한들이 허용 되었을 경우 이 곳을 수행함.
                Toast.makeText(this@MainActivity, "권한이 허용 되었습니다.", Toast.LENGTH_SHORT).show() // 토스트메세지 띄워줌
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) { // 설정해놓은 위험권한 들 중 거부를 한 경우 이곳을 수행함
                Toast.makeText(this@MainActivity, "권한이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }

        // TedPermission -> 누군가 만든 라이브러리로 쉽게 퍼미션 줄 수 있게 만들어둠
        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다. [앱 설정] - > [권한] 항목에서 허용해주세요.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
            .check()
    }

    // startActivityForResult를 통해서 기본 카메라 앱으로 부터 받아온 사진 결과 값
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 이미지를 성공적으로 가져왔다면..
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val bitmap: Bitmap
            val file = File(curPhotoPath)
            if(Build.VERSION.SDK_INT < 28){ // 안드로이드 9.0 (Pie) 버전보다 낮을 경우
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))
                iv_profile.setImageBitmap(bitmap) // set하면서 이미지뷰에 기록이 됨
            } else{ // 안드로이드 9.0 (Pie) 버전보다 높을 경우
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    Uri.fromFile(file)
                )
                bitmap = ImageDecoder.decodeBitmap(decode)
                iv_profile.setImageBitmap(bitmap)

            }
            Log.e("완료", "ENTER")
            savePhoto(bitmap)
        }

    }

    // 갤러리에 저장
    private fun savePhoto(bitmap: Bitmap) {
        val folderPath = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/" // 사진폴더로 저장하기 위한 경로 선언
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) // 시간 표기 포맷
        val fileName = "${timestamp}.jpeg"
        val folder = File(folderPath)
        if(!folder.isDirectory) { // 현재 해당 경로에 폴더가 존재하지 않는다면 검사
            folder.mkdirs() // make directory 줄임말로 해당 경로에 폴더 자동으로 새로 만들기
        }

        // 실제적인 저장처리
        val out = FileOutputStream(folderPath + fileName)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show()
    }
}