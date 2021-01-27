package com.beomjo.coroutine.codelab.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomjo.coroutine.codelab.util.BACKGROUND
import com.beomjo.coroutine.codelab.util.singleArgViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TitleRepository) : ViewModel() {

    companion object {
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }

    private val _snackBar = MutableLiveData<String?>()
    val snackbar: LiveData<String?>
        get() = _snackBar

    val title = repository.title

    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean>
        get() = _spinner

    private var tapCount = 0

    private val _taps = MutableLiveData<String>("$tapCount taps")
    val taps: LiveData<String>
        get() = _taps

    fun onMainViewClicked() {
        refreshTitle()
        updateTaps()
    }

    private fun updateTaps() {
        viewModelScope.launch {
            tapCount++
            delay(1_000)
            _taps.postValue("$tapCount taps")
        }
    }

    fun onSnackbarShown() {
        _snackBar.value = null
    }

    private fun refreshTitle() {
        launchDataLoad {
            repository.refreshTitle()
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: TitleRefreshError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }
}
