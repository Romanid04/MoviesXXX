package com.jax.movies.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.data.ActorDetailsResponse
import com.jax.movies.network.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActorPageViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ActorPageUIState>(ActorPageUIState.Loading)
    val uiState: StateFlow<ActorPageUIState> get() = _uiState

    fun getActorDetails(staffId: Int) {
        viewModelScope.launch {
            _uiState.value = ActorPageUIState.Loading
            try {
                val actorDetails = Api.retrofitService.getActorDetails(staffId)
                _uiState.value = ActorPageUIState.Success(actorDetails)
            } catch (e: Exception) {
                _uiState.value = ActorPageUIState.Error(e.localizedMessage ?: "Ошибка загрузки данных актера")
            }
        }
    }
}

sealed class ActorPageUIState {
    object Loading : ActorPageUIState()
    data class Success(val actorDetails: ActorDetailsResponse) : ActorPageUIState()
    data class Error(val message: String) : ActorPageUIState()
}
