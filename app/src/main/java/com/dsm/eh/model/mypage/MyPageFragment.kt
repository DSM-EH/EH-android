package com.dsm.eh.model.mypage

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import com.dsm.eh.data.RetrofitBuilder
import com.dsm.eh.data.response.user.UserProfileResponse
import com.dsm.eh.databinding.FragmentMyPageBinding
import com.dsm.eh.model.common.MenuActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.dsm.eh.data.util.MyApplication
import com.dsm.eh.model.sign.MainActivity

class MyPageFragment : Fragment() {
    lateinit var binding: FragmentMyPageBinding
    private val retrofitBuilder = RetrofitBuilder


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        binding.refresh.setOnRefreshListener {
            initLoadProfile()
            binding.refresh.isRefreshing = false
        }

        binding.profileEdit.setOnClickListener {
            startActivity(Intent(activity, ChangeProfileActivity::class.java))
        }

        binding.passwordEdit.setOnClickListener {

        }

        binding.logout.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }

        binding.exit.setOnClickListener {

        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initLoadProfile()
    }

    fun initLoadProfile() {
        Glide.with(binding.profileImage)
            .load(MyApplication.prefs.getString("profile", ""))
            .into(binding.profileImage)
        binding.name.text = MyApplication.prefs.getString("nickname", "이름")
        binding.email.text = MyApplication.prefs.getString("email", "이메일")
        binding.oneliner.text = MyApplication.prefs.getString("oneliner", "한줄 소개")
    }
}