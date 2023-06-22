package com.dsm.eh.model.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.dsm.eh.data.RetrofitBuilder
import com.dsm.eh.data.request.user.UserProfileEditRequest
import com.dsm.eh.data.response.user.UserProfileResponse
import com.dsm.eh.data.util.MyApplication
import com.dsm.eh.databinding.ActivityChangeProfileBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityChangeProfileBinding
    private val retrofitBuilder = RetrofitBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        Glide.with(binding.profile)
            .load(MyApplication.prefs.getString("profile", ""))
            .into(binding.profile)
        binding.nickName.setText(MyApplication.prefs.getString("nickname", ""))
        binding.oneLiner.setText(MyApplication.prefs.getString("oneliner", ""))
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.nextBtn.setOnClickListener {
            retrofitBuilder.userApi.userProfileEdit(
                UserProfileEditRequest(
                    MyApplication.prefs.getString("email", ""),
                    MyApplication.prefs.getString("password", ""),
                    binding.nickName.text.toString(),
                    binding.oneLiner.text.toString(),
                    MyApplication.prefs.getString("profile", "")
                )
            ).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("통신 실패", "${t.message}")
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Log.d("성공", response.message())
                        initUserProfile()
                        onBackPressed()
                    } else {
                        Log.d("실패", response.message())
                    }
                }
            })
        }
    }

    fun initUserProfile() {
        retrofitBuilder.userApi.userProfile(
            MyApplication.prefs.getString("email", "")
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