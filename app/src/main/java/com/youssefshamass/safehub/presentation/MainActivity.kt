package com.youssefshamass.safehub.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core_android.extensions.binding
import com.example.core_android.extensions.dataBinding
import com.youssefshamass.safehub.R
import com.youssefshamass.safehub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}