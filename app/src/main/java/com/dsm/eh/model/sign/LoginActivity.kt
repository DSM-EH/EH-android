package com.dsm.eh.model.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dsm.eh.data.RetrofitBuilder
import com.dsm.eh.data.response.user.UserProfileResponse
import com.dsm.eh.data.util.MyApplication
import com.dsm.eh.model.common.MenuActivity
import com.dsm.eh.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val retrofitBuilder = RetrofitBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.nextBtn.setOnClickListener {
            MyApplication.prefs.setString("email", "${binding.email.text}")
            initUserProfile()
            startActivity(Intent(this, MenuActivity::class.java))
            finishAffinity()
        }
    }

    fun initUserProfile() {
        retrofitBuilder.userApi.userProfile(
            MyApplication.prefs.getString("email","")
        ).enqueue(object : Callback<UserProfileResponse> {
            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                Log.d("통신 실패", "${t.message}")
            }

            override fun onResponse(
                call: Call<UserProfileResponse>,
                response: Response<UserProfileResponse>
            ) {
                if (response.isSuccessful) {
                    MyApplication.prefs.setString("id", response.body()!!.id.toString())
                    MyApplication.prefs.setString("email", response.body()!!.email)
                    MyApplication.prefs.setString("password", response.body()!!.password)
                    MyApplication.prefs.setString("nickname", response.body()!!.nickname)
                    MyApplication.prefs.setString("oneliner", response.body()!!.description)
                    MyApplication.prefs.setString("profile", response.body()!!.profileImageUrl!!)
                }
            }
        })
    }
}