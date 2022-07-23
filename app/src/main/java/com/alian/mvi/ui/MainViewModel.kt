package com.alian.mvi.ui

import androidx.lifecycle.*
import com.alian.mvi.model.Blog
import com.alian.mvi.repository.MainRepository
import com.alian.mvi.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
) : ViewModel(), LifecycleObserver {
    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetBlogEvents -> {
                    mainRepository.getBlog().onEach {
                        _dataState.value = it
                    }.launchIn(viewModelScope)
                }
                else -> {}
            }
        }
    }
}

sealed class MainStateEvent {
    object GetBlogEvents : MainStateEvent()
    object None : MainStateEvent()
}