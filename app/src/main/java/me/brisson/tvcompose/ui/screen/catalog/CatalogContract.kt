package me.brisson.tvcompose.ui.screen.catalog

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import me.brisson.tvcompose.domain.model.Category
import me.brisson.tvcompose.domain.model.Movie

sealed interface CatalogUiState {
    data object Loading : CatalogUiState
    data class Error(val message: String) : CatalogUiState
    data class Ok(val categories: List<Category>, val movies: List<Movie>) : CatalogUiState
}

class CatalogUiStatePreviewProvider : PreviewParameterProvider<CatalogUiState> {
    private val movies = listOf(
        Movie(
            id = 1,
            title = "The Shawshank Redemption",
            description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
            backgroundImageUrl = "https://example.com/shawshank_background.jpg",
            cardImageUrl = "https://example.com/shawshank_card.jpg",
            videoUrl = "https://example.com/shawshank_trailer.mp4",
            studio = "Columbia Pictures"
        ),
        Movie(
            id = 2,
            title = "The Dark Knight",
            description = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
            backgroundImageUrl = "https://example.com/darkknight_background.jpg",
            cardImageUrl = "https://example.com/darkknight_card.jpg",
            videoUrl = "https://example.com/darkknight_trailer.mp4",
            studio = "Warner Bros."
        ),
        Movie(
            id = 3,
            title = "Pulp Fiction",
            description = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
            backgroundImageUrl = "https://example.com/pulpfiction_background.jpg",
            cardImageUrl = "https://example.com/pulpfiction_card.jpg",
            videoUrl = "https://example.com/pulpfiction_trailer.mp4",
            studio = "Miramax Films"
        )
    )

    override val values = sequenceOf(
        CatalogUiState.Loading,
        CatalogUiState.Error("Error"),
        CatalogUiState.Ok(
            categories = listOf(
                Category(
                    name = "Action",
                    movieList = movies,
                ),
                Category(
                    name = "Romance",
                    movieList = movies,
                ),
                Category(
                    name = "Comedy",
                    movieList = movies,
                ),
            ),
            movies = movies,
        )
    )
}