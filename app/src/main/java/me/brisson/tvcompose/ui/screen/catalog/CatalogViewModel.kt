package me.brisson.tvcompose.ui.screen.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.raise.either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.brisson.tvcompose.domain.model.Category
import me.brisson.tvcompose.domain.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch {
            val result = either {
                val movies = movieRepository.getFeaturedMovieList().bind()
                val categories = movieRepository.getCategoryList().bind().map { category ->
                    Category(
                        name = category,
                        movieList = movieRepository.getMovieListByCategory(category).bind(),
                    )
                }
                CatalogUiState.Ok(categories, movies)
            }
            _uiState.value = result.fold(
                ifLeft = { error ->  CatalogUiState.Error(error.message)},
                ifRight = { it }
            )
        }
    }
}
