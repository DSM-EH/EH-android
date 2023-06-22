package com.dsm.eh.model.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.dsm.eh.R
import com.dsm.eh.databinding.ActivityMenuBinding
import com.dsm.eh.model.board.BoardFragment
import com.dsm.eh.model.home.HomeFragment
import com.dsm.eh.model.mypage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MenuActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    lateinit var binding: ActivityMenuBinding
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener(this)

        //첫 프래그먼트 화면은 home fragment로
        bottomNavigationView.selectedItemId = R.id.homeFragment
    }

    //Implement member
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.bottom_container, HomeFragment())
                    // view_main이 보여지는 화면에 fragment를 보여줘라
                    .commit()
                return true
            }

            R.id.boardFragment -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.bottom_container, BoardFragment()).commit()
                return true
            }

            R.id.myPageFragment -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.bottom_container, MyPageFragment()).commit()
                return true
            }
        }
        return false
    }

    private var backPressedTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime >= 1500) {
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish() // 액티비티 종료
        }
    }
}