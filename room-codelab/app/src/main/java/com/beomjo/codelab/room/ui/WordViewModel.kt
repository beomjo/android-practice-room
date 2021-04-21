package com.beomjo.codelab.room.ui

import androidx.lifecycle.*
import com.beomjo.codelab.room.persistence.entity.Word
import com.beomjo.codelab.room.repository.WordRepository
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class WordViewModel(private val repository: WordRepository) : ViewModel() {
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalStateException("Unknown ViewModel Class")
    }
}