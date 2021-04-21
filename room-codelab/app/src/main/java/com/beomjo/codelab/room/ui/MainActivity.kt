package com.beomjo.codelab.room.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beomjo.codelab.room.adapter.WordListAdapter
import com.beomjo.codelab.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.recyclerview.apply {
            adapter = WordListAdapter()
        }
    }
}