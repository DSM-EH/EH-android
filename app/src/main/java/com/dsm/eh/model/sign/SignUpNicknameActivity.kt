package com.dsm.eh.model.sign

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dsm.eh.databinding.ActivitySignUpNicknameBinding

class SignUpNicknameActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = Uri.parse(imageUriString)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.nextBtn.setOnClickListener {
            binding.nickNameInputLayout.error = null
            if (binding.nickName.text.isEmpty()) {
                binding.nickName.requestFocus()
                binding.nickNameInputLayout.error = "닉네임을 입력해주세요."
            } else {
                intent = Intent(this, SignUpOnelinerActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", password)
                intent.putExtra("nickName", binding.nickName.text.toString())
                intent.putExtra("imageUri", imageUri.toString())
                startActivity(intent)
            }
        }
    }
}