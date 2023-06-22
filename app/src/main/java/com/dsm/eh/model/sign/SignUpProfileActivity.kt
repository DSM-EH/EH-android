package com.dsm.eh.model.sign

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dsm.eh.data.util.MyApplication
import com.dsm.eh.databinding.ActivitySignUpProfileBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class SignUpProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpProfileBinding
    lateinit var imageUri: Uri
    private val imageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUri = result.data?.data ?: return@registerForActivityResult
                binding.imageUrl.setImageURI(imageUri)
            }
        }

    companion object {
        const val REQ_GALLERY = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        binding.imageUrl.setOnClickListener {
            initAddPhoto()
        }

        binding.imageUrl1.setOnClickListener {
            initCheckPermission()
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.nextBtn.setOnClickListener {
            intent = Intent(this, SignUpNicknameActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("imageUri", imageUri.toString())
            startActivity(intent)
        }
    }

    fun initCheckPermission() {
        // 현재 기기에 설정된 쓰기 권한을 가져오기 위한 변수
        val writePermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        // 현재 기기에 설정된 읽기 권한을 가져오기 위한 변수
        val readPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        // 읽기 권한과 쓰기 권한에 대해서 설정이 되어있지 않다면
        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
            // 읽기, 쓰기 권한을 요청합니다.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQ_GALLERY
            )
        }
        // 위 경우가 아니라면 권한에 대해서 설정이 되어 있으므로
        else {
            initAddPhoto()
        }
    }

    fun initAddPhoto() {
        // 갤러리를 열어서 파일을 선택할 수 있도록 합니다.
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        imageResult.launch(intent)
    }
}