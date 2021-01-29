package com.beomjo.coroutines.advanced

import androidx.lifecycle.*
import com.beomjo.advancedcoroutines.GrowZone
import com.beomjo.advancedcoroutines.NoGrowZone
import com.beomjo.advancedcoroutines.Plant
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlantListViewModel internal constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _snackbar = MutableLiveData<String?>()
    val snackbar: LiveData<String?>
        get() = _snackbar

    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val growZone = MutableLiveData<GrowZone>(NoGrowZone)

    val plants: LiveData<List<Plant>> = growZone.switchMap { growZone ->
        if (growZone == NoGrowZone) {
            plantRepository.plants
        } else {
            plantRepository.getPlantsWithGrowZone(growZone)
        }
    }

    private val growZoneChannel = ConflatedBroadcastChannel<GrowZone>()

    val plantsUsingFlow: LiveData<List<Plant>> = growZoneChannel.asFlow()
        .flatMapLatest { growZone ->
            if (growZone == NoGrowZone) {
                plantRepository.plantsFlow
            } else {
                plantRepository.getPlantsWithGrowZoneFlow(growZone)
            }
        }
        .asLiveData()


    init {
        clearGrowZoneNumber()

        launchDataLoad {
            plantRepository.tryUpdateRecentPlantsCache()
        }

        growZoneChannel.asFlow().mapLatest { growZone ->
            _spinner.value = true
            if (growZone == NoGrowZone) {
                plantRepository.tryUpdateRecentPlantsCache()
            } else {
                plantRepository.tryUpdateRecentPlantsForGrowZoneCache(growZone)
            }
        }
            .onCompletion { _spinner.value = false }
            .catch { throwable -> _snackbar.value = throwable.message }
            .launchIn(viewModelScope)

    }

    fun setGrowZoneNumber(num: Int) {
        growZone.value = GrowZone(num)
        growZoneChannel.offer(GrowZone(num))
    }

    fun clearGrowZoneNumber() {
        growZone.value = NoGrowZone
        growZoneChannel.offer(NoGrowZone)
    }

    fun isFiltered() = growZone.value != NoGrowZone

    fun onSnackbarShown() {
        _snackbar.value = null
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: Throwable) {
                _snackbar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }
}
