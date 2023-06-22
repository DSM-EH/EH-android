package com.dsm.eh.model.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dsm.eh.databinding.ActivitySignUpPasswordBinding

class SignUpPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email = intent.getStringExtra("email")

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.nextBtn.setOnClickListener {
            binding.passwordInputLayout.error = null
            binding.passwordValidInputLayout.error = null
            if (binding.password.text.isEmpty()) {
                binding.password.requestFocus()
                binding.passwordInputLayout.error = "비밀번호를 입력해주세요."
            } else if (binding.passwordValid.text.isEmpty()) {
                binding.passwordValid.requestFocus()
                binding.passwordValidInputLayout.error = "비밀번호 재확인을 입력해주세요."
            } else if (binding.password.text.toString() != binding.passwordValid.text.toString()) {
                binding.passwordValid.requestFocus()
                binding.passwordValidInputLayout.error = "비밀번호가 일치하지 않습니다."
                Log.d("A", "${binding.password.text} ${binding.passwordValid.text}")
            } else {
                intent = Intent(this, SignUpProfileActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", binding.password.text.toString())
                startActivity(intent)
            }
        }
    }
}