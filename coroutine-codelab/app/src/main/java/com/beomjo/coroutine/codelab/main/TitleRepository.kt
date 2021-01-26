package com.beomjo.coroutine.codelab.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.beomjo.coroutine.codelab.util.BACKGROUND
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TitleRepository(val network: MainNetwork, val titleDao: TitleDao) {

    val title: LiveData<String?> = titleDao.titleLiveData.map { it?.title }

    fun refreshTitleWithCallbacks(titleRefreshCallback: TitleRefreshCallback) {
        BACKGROUND.submit {
            try {
                val result = network.fetchNextTitle().execute()
                if (result.isSuccessful) {
                    titleDao.insertTitle(Title(result.body()!!))
                    titleRefreshCallback.onCompleted()
                } else {
                    titleRefreshCallback.onError(
                        TitleRefreshError("Unable to refresh title", null)
                    )
                }
            } catch (cause: Throwable) {
                titleRefreshCallback.onError(
                    TitleRefreshError("Unable to refresh title", cause)
                )
            }
        }
    }

    /**
     * 고급 팁
     * 이 코드는 코 루틴 취소를 지원하지 않지만 가능합니다! 코 루틴 취소는 협력 적 입니다. 즉, kotlinx-coroutines에서 함수를 호출 할 때마다 발생하는 취소를 명시 적으로 코드에서 확인해야합니다.
     * 이 withContext차단은 차단 호출 만 호출 하기 때문에 에서 돌아올 때까지 취소되지 않습니다 withContext.
     * 이 문제를 해결하려면 yield 정기적으로 전화 하여 다른 코 루틴에게 기회를 제공하고 취소 여부를 확인할 수 있습니다. 여기 yield에서 네트워크 요청과 데이터베이스 쿼리 사이에 호출을 추가 합니다.
     * 그런 다음 네트워크 요청 중에 코 루틴이 취소되면 결과가 데이터베이스에 저장되지 않습니다.
     * 저수준 코 루틴 인터페이스를 만들 때 수행해야하는 취소를 명시 적으로 확인할 수도 있습니다.
     */
    suspend fun refreshTitle() {
        withContext(Dispatchers.IO) {
            val result = try {
                network.fetchNextTitle().execute()
            } catch (cause: Throwable) {
                throw TitleRefreshError("Unable to refresh title", cause)
            }

            if (result.isSuccessful) {
                titleDao.insertTitle(Title(result.body()!!))
            } else {
                throw TitleRefreshError("Unable to refresh title", null)
            }
        }
    }
}

class TitleRefreshError(message: String, cause: Throwable?) : Throwable(message, cause)

interface TitleRefreshCallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}
