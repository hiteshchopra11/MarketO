package com.hiteshchopra.marketo.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiteshchopra.marketo.domain.SafeResult
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity
import com.hiteshchopra.marketo.domain.usecase.GetEventDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val eventDetailsUseCase: GetEventDetailsUseCase
) : ViewModel() {

    /* StateFlow for publishing/observing the UI changes to Fragment */
    init {
        getEvents()
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState

    private fun getEvents() {
        viewModelScope.launch {
            val result = eventDetailsUseCase.perform()
            when (result) {
                is SafeResult.Failure -> {
                    _viewState.value = ViewState.Failure(result.exception)
                }
                SafeResult.NetworkError -> {
                    ViewState.NetworkError
                }
                is SafeResult.Success -> {
                    Log.d("TAG123", result.data.toString())
                    ViewState.SuccessWithData(result.data as? EventDetailsEntity)
                }
            }
        }
    }
}


sealed class ViewState {
    object Loading : ViewState()
    class SuccessWithData<T>(val data: T) : ViewState()
    class Failure(val exception: Exception? = null) : ViewState()
    object NetworkError : ViewState()
}