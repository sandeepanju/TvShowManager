package com.example.tvshowmanager.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tvshowmanager.R
import com.example.tvshowmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listner()
    }

    private fun listner() {
        binding.btnCreateNewShow.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CreateShowActivity::class.java
                )
            )
        }
        binding.btnShowList.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ShowListActivity::class.java
                )
            )
        }
    }
}