package com.dsm.eh.model.sign

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dsm.eh.model.common.MenuActivity
import com.dsm.eh.data.RetrofitBuilder
import com.dsm.eh.data.response.user.UserProfileResponse
import com.dsm.eh.data.util.MyApplication
import com.dsm.eh.databinding.ActivitySignUpOnelinerBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class SignUpOnelinerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpOnelinerBinding
    private val retrofitBuilder = RetrofitBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpOnelinerBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.nextBtn.setOnClickListener {
            binding.oneLinerInputLayout.error = null
            if (binding.oneLiner.text.isEmpty()) {
                binding.oneLiner.requestFocus()
                binding.oneLinerInputLayout.error = "한줄 소개을 입력해주세요."
            } else {
                val email = intent.getStringExtra("email")
                val password = intent.getStringExtra("password")
                val nickname = intent.getStringExtra("nickname")
                val description = binding.oneLiner.text.toString()
                val imageUriString = intent.getStringExtra("imageUri")
                val imageUri = Uri.parse(imageUriString)
                val imageFile = convertImageToFile(imageUri)
                val requestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData(
                    "Profile_image_url",
                    imageFile.name,
                    requestBody
                )

                retrofitBuilder.userApi.userSignUp(
                    email = email.toString(),
                    password = password.toString(),
                    nickname = nickname.toString(),
                    description = description,
                    images = imagePart
                ).enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("통신 실패", "${t.message}")
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            MyApplication.prefs.setString("email", "$email")
                            initUserProfile()
                            startActivity(
                                Intent(
                                    this@SignUpOnelinerActivity,
                                    MenuActivity::class.java
                                )
                            )
                            finishAffinity()
                        }
                    }
                })
            }
        }
    }

    fun convertImageToFile(uri: Uri): File {
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(cacheDir, "temp_image.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.flush()
        outputStream.close()
        inputStream?.close()
        return file
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