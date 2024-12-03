package com.jax.movies.model

import android.util.Log
import androidx.compose.runtime.key
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val uiState: StateFlow<SearchUiState> = _uiState

    private val repository = MovieRepository()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun findFilmsByKeyword(keyword: String){
        if(keyword.isBlank()) return
        _uiState.value = SearchUiState.Loading
        viewModelScope.launch {
            try{
                val results = repository.searchByKeyword(keyword)
                _uiState.value = SearchUiState.Success(searchResults = results.films)
            }
            catch (e: Exception){
                Log.e("Search ViewModel: ", e.message.toString())
                _uiState.value = SearchUiState.Error("Error in searching")
            }
        }
    }

    fun onSearchTextChange(text: String){
        _searchText.value = text
    }

    fun onToogleSearch(){
        _isSearching.value = !_isSearching.value
        if(!_isSearching.value){
            onSearchTextChange("")
        }
    }

}