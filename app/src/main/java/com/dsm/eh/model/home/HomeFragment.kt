package com.dsm.eh.model.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsm.eh.data.RetrofitBuilder
import com.dsm.eh.data.response.Group
import com.dsm.eh.data.util.GroupRecyclerViewAdapter
import com.dsm.eh.data.util.MyApplication
import com.dsm.eh.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val adapter = GroupRecyclerViewAdapter()
    val retrofitBuilder = RetrofitBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initLoadData()
        initDetailGroup()
        initRefresh()

        return binding.root
    }

    fun initRefresh() {
        binding.refresh.setOnRefreshListener {
            initLoadData()
            binding.refresh.isRefreshing = false
        }
    }

    fun initDetailGroup() {
        adapter.itemClickListener = object : GroupRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = adapter.groupList[position]
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("id", item.id)
                intent.putExtra("title", item.title)
                intent.putExtra("oneliner", item.description)
                intent.putExtra("image", item.background_image)
                startActivity(intent)
            }
        }
    }

    private fun setAdapter(groupList: List<Group>) {
        adapter.groupList = groupList.toMutableList()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayout.VERTICAL
            )
        )
    }

    fun initLoadData() {
        retrofitBuilder.groupApi.groupMyGroup(MyApplication.prefs.getString("email",""))
            .enqueue(object : Callback<ArrayList<Group>> {
                override fun onFailure(call: Call<ArrayList<Group>>, t: Throwable) {
                    Log.d("통신 실패", "${t.message}")
                }

                override fun onResponse(
                    call: Call<ArrayList<Group>>,
                    response: Response<ArrayList<Group>>
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