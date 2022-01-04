package com.task.noteapp.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.task.noteapp.R
import com.task.noteapp.databinding.ActivityMainBinding
import com.task.noteapp.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val dataBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(dataBinding.root)
    }
}