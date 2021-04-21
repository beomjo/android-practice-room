package com.beomjo.codelab.room.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import com.beomjo.codelab.room.R
import com.beomjo.codelab.room.WordApplication
import com.beomjo.codelab.room.ui.adapter.WordListAdapter
import com.beomjo.codelab.room.databinding.ActivityMainBinding
import com.beomjo.codelab.room.persistence.entity.Word

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActivityMainBinding

    private val wordViewModel: WordViewModel by viewModels { WordViewModelFactory((application as WordApplication).repository) }

    private val wordAdapter = WordListAdapter()

    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
    }

    private fun setView() {
        binding.apply {
            recyclerview.apply {
                adapter = wordAdapter
            }
            fab.apply {
                setOnClickListener {
                    val intent = Intent(this@MainActivity, NewWordActivity::class.java)
                    startActivityForResult(intent, newWordActivityRequestCode)
                }
            }
        }

        wordViewModel.allWords.observe(this) { words ->
            words?.let { wordAdapter.addList(it) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}