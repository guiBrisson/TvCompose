package me.brisson.tvcompose.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import me.brisson.tvcompose.ui.components.ErrorComponent
import me.brisson.tvcompose.ui.components.Loading
import me.brisson.tvcompose.ui.theme.TvComposeTheme

@Composable
fun DetailsRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    val viewModel: DetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect("fetch data") {
        viewModel.fetchMovieDetails()
    }

    DetailsScreen(
        modifier = modifier then Modifier.fillMaxSize(),
        uiState = uiState,
        onBack = onBack,
    )
}

@Composable
internal fun DetailsScreen(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState,
    onBack: () -> Unit,
) {
    when(uiState) {
        is DetailsUiState.Ok -> OkUiState(modifier = modifier, uiState = uiState)
        DetailsUiState.Loading -> Loading(modifier = modifier)
        DetailsUiState.NotFound -> NotFound(modifier = modifier, onBack = onBack)
        is DetailsUiState.Error -> ErrorComponent(
            modifier = modifier,
            errorMessage = uiState.message,
            onBack = onBack,
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun OkUiState(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState.Ok,
) {
    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = uiState.movie.cardImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            Color.Transparent
                        )
                    )
                )
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 48.dp, vertical = 24.dp)
                    .fillMaxWidth(0.5f)
            ) {
                Text(
                    text = uiState.movie.title,
                    style = MaterialTheme.typography.displayMedium,
                )
                Text(
                    text = uiState.movie.studio,
                    style = MaterialTheme.typography.bodySmall,
                )

                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = uiState.movie.description,
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun NotFound(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text(text = "Not found", style = MaterialTheme.typography.displayMedium)
            Button(onClick = onBack) {
                Text(text = "Back to the previous screen")
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Preview(device = "id:tv_1080p", showBackground = true)
@Composable
private fun PreviewDetailsScreen(
    @PreviewParameter(DetailsUiStatePreviewProvider::class)
    uiState: DetailsUiState,
) {
    TvComposeTheme(isInDarkTheme = true) {
        Surface(shape = RectangleShape) {
            DetailsScreen(
                modifier = Modifier.fillMaxSize(),
                uiState = uiState,
                onBack = { },
            )
        }
    }
}
