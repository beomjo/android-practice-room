package com.beomjo.codelab.room.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.beomjo.codelab.room.persistence.dao.WordDao
import com.beomjo.codelab.room.persistence.entity.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDataBase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {

        @Volatile
        private var INSTANCE: WordRoomDataBase? = null

        fun getDatabase(context: Context): WordRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDataBase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}