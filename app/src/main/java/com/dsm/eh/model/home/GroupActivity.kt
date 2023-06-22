package com.dsm.eh.model.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dsm.eh.data.RetrofitBuilder
import com.dsm.eh.data.response.Post
import com.dsm.eh.data.util.PostRecyclerViewAdapter
import com.dsm.eh.databinding.ActivityGroupBinding
import com.dsm.eh.model.common.CreatePostActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupBinding
    private val adapter = PostRecyclerViewAdapter()
    val retrofitBuilder = RetrofitBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("id", 0)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        binding.title.text = intent.getStringExtra("title")
        binding.oneliner.text = intent.getStringExtra("oneliner")
        if (intent.getStringExtra("image") != null) {
            Glide.with(binding.backgroundImage).load(intent.getStringExtra("image"))
                .into(binding.backgroundImage)
        }

        setContentView(binding.root)
        initRefresh()

        binding.createContestButton.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        binding.imageButton5.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        initLoadData()
    }

    fun initRefresh() {
        binding.refresh.setOnRefreshListener {
            initLoadData()
            binding.refresh.isRefreshing = false
        }
    }

    private fun setAdapter(postList: List<Post>) {
        adapter.postList = postList.toMutableList()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayout.VERTICAL)
        )
    }

    fun initLoadData() {
        retrofitBuilder.postApi.postGet(intent.getIntExtra("id", 0))
            .enqueue(object : Callback<ArrayList<Post>> {
                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                    Log.d("통신 실패", "${t.message}")
                }

                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            setAdapter(response.body()!!)
                        }
                    } else {
                        Log.d("실패", response.message())
                    }
                }
            })
    }
}