package me.brisson.tvcompose.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.brisson.tvcompose.domain.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val movieId : Long = checkNotNull(savedStateHandle["id"])
    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun fetchMovieDetails() {
        viewModelScope.launch {
            _uiState.value = movieRepository.findMovieById(movieId).fold(
                ifLeft = { error -> DetailsUiState.Error(error.message) },
                ifRight = { movie ->
                    if (movie == null) {
                        DetailsUiState.NotFound
                        return@launch
                    }
                    DetailsUiState.Ok(movie)
                }
            )
        }
    }
}
