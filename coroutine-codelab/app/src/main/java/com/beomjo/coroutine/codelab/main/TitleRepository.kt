package com.beomjo.coroutine.codelab.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.*

class TitleRepository(val network: MainNetwork, val titleDao: TitleDao) {

    val title: LiveData<String?> = titleDao.titleLiveData.map { it?.title }

    suspend fun refreshTitle() {
        try {
            val result = withTimeout(5_000) {
                network.fetchNextTitle()
            }
            titleDao.insertTitle(Title(result))
        } catch (error: Throwable) {
            println(error)
            throw TitleRefreshError("Unable to refresh title", error)
        }
    }
}

class TitleRefreshError(message: String, cause: Throwable?) : Throwable(message, cause)