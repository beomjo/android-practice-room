package com.beomjo.codelab.room.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.beomjo.codelab.room.persistence.dao.WordDao
import com.beomjo.codelab.room.persistence.entity.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDataBase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {

                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()
            var word = Word(word = "Hello")
            wordDao.insert(word)
            word = Word(word = "World")
            wordDao.insert(word)
            word = Word("TODO!")
            wordDao.insert(word)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: WordRoomDataBase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope,
        ): WordRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDataBase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

