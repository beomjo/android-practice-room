package com.beomjo.codelab.room

import android.app.Application
import com.beomjo.codelab.room.persistence.WordRoomDataBase
import com.beomjo.codelab.room.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { WordRoomDataBase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}