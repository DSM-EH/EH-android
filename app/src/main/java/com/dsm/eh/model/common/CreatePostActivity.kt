package com.dsm.eh.model.common

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.dsm.eh.data.RetrofitBuilder
import com.dsm.eh.data.request.post.PostCreateRequest
import com.dsm.eh.data.util.MyApplication
import com.dsm.eh.databinding.ActivityCreatePostBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePostActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreatePostBinding
    val retrofitBuilder = RetrofitBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("id", 0)
        var title = false
        var content = false

        binding.imageButton3.setOnClickListener {
            onBackPressed()
        }

        binding.title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                title = binding.title.length() >= 1

                if (title && content) {
                    binding.finish.setTextColor(Color.parseColor("#FFFFACAC"))
                    binding.finish.isEnabled = true
                } else {
                    binding.finish.setTextColor(Color.parseColor("#FFA7A7A7"))
                    binding.finish.isEnabled = false
                }
            }
        })

        binding.content.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                content = binding.content.length() >= 1

                if (title && content) {
                    binding.finish.setTextColor(Color.parseColor("#FFFFACAC"))
                    binding.finish.isEnabled = true
                } else {
                    binding.finish.setTextColor(Color.parseColor("#FFA7A7A7"))
                    binding.finish.isEnabled = false
                }
            }
        })


        binding.finish.setOnClickListener {
            retrofitBuilder.postApi.postPost(
                PostCreateRequest(
                    MyApplication.prefs.getString("id", "").toInt(),
                    id,
                    binding.title.text.toString(),
                    binding.content.text.toString(),
                    false
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
                        onBackPressed()
                    } else {
                        Log.d("실패", response.message())
                    }
                }
            })
        }
    }
}