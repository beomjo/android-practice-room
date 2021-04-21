package com.beomjo.codelab.room.repository

import com.beomjo.codelab.room.persistence.dao.WordDao
import com.beomjo.codelab.room.persistence.entity.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWord()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}