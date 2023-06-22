package com.dsm.eh.model.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dsm.eh.databinding.ActivitySignUpEmailBinding

class SignUpEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.nextBtn.setOnClickListener {
            binding.emailInputLayout.error = null
            if (binding.email.text.isEmpty()) {
                binding.email.requestFocus()
                binding.emailInputLayout.error = "이메일을 입력해주세요."
            } else {
                intent = Intent(this, SignUpPasswordActivity::class.java)
                intent.putExtra("email", binding.email.text.toString())
                startActivity(intent)
            }
        }
    }
}