package me.brisson.tvcompose.ui.screen.details

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import me.brisson.tvcompose.domain.model.Movie

sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data object NotFound: DetailsUiState
    data class Error(val message: String) : DetailsUiState
    data class Ok(val movie: Movie) : DetailsUiState
}

class DetailsUiStatePreviewProvider : PreviewParameterProvider<DetailsUiState> {
    override val values = sequenceOf(
        DetailsUiState.Loading,
        DetailsUiState.NotFound,
        DetailsUiState.Error("Unknown error"),
        DetailsUiState.Ok(
            movie = Movie(
                id = 1,
                title = "The Shawshank Redemption",
                description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                backgroundImageUrl = "https://example.com/shawshank_background.jpg",
                cardImageUrl = "https://example.com/shawshank_card.jpg",
                videoUrl = "https://example.com/shawshank_trailer.mp4",
                studio = "Columbia Pictures"
            )
        )
    )
}
