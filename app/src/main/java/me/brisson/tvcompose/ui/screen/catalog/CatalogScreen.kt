package me.brisson.tvcompose.ui.screen.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.material3.Button
import androidx.tv.material3.Carousel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import me.brisson.tvcompose.R
import me.brisson.tvcompose.domain.model.Movie
import me.brisson.tvcompose.ui.components.ErrorComponent
import me.brisson.tvcompose.ui.components.Loading
import me.brisson.tvcompose.ui.components.MovieCard
import me.brisson.tvcompose.ui.theme.TvComposeTheme

@Composable
fun CatalogRoute(
    modifier: Modifier = Modifier,
    onMovie: (id: Long) -> Unit,
) {
    val viewModel: CatalogViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect("fetch data") {
        viewModel.fetchData()
    }

    CatalogScreen(
        modifier = modifier then Modifier.fillMaxSize(),
        uiState = uiState,
        onMovie = onMovie,
    )
}

@Composable
internal fun CatalogScreen(
    modifier: Modifier = Modifier,
    uiState: CatalogUiState,
    onMovie: (id: Long) -> Unit,
) {
    when(uiState) {
        is CatalogUiState.Ok -> OkUiState(
            modifier = modifier,
            uiState = uiState,
            onMovie = { onMovie(it.id) }
        )
        is CatalogUiState.Error -> {
            ErrorComponent(
                modifier = modifier,
                errorMessage = uiState.message,
            )
        }
        CatalogUiState.Loading -> Loading(modifier = modifier)
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun OkUiState(
    modifier: Modifier = Modifier,
    uiState: CatalogUiState.Ok,
    onMovie: (Movie) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(vertical = 36.dp)
    ) {
        val featuredMovieList = uiState.movies
        val categoryList = uiState.categories

        item {
            Carousel(
                modifier = Modifier.fillMaxWidth().height(376.dp),
                itemCount = featuredMovieList.size,
            ) { indexOfCarouselItem ->
                val featuredMovie = featuredMovieList[indexOfCarouselItem]
                val backgroundColor = MaterialTheme.colorScheme.background

                Box {
                    AsyncImage(
                        model = featuredMovie.backgroundImageUrl,
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                    Box(
                        contentAlignment = Alignment.BottomStart,
                        modifier = Modifier
                            .fillMaxSize()
                            .drawBehind {
                                val brush = Brush.horizontalGradient(
                                    listOf(backgroundColor, Color.Transparent)
                                )
                                drawRect(brush)
                            }
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = featuredMovie.title,
                                style = MaterialTheme.typography.displaySmall
                            )
                            Spacer(modifier = Modifier.height(28.dp))
                            Button(onClick = { onMovie(featuredMovie) }) {
                                Text(text = stringResource(id = R.string.show_details))
                            }
                        }
                    }
                }
            }
        }
        items(categoryList) { category ->
            Text(
                modifier = Modifier.padding(bottom = 12.dp, start = 52.dp),
                text = category.name,
                fontWeight = FontWeight.Medium,
            )

            LazyRow(
                modifier = Modifier.height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 52.dp),
            ) {
                items(category.movieList) { movie ->
                    MovieCard(movie = movie, onClick = onMovie)
                }
            }
        }
    }
}


@OptIn(ExperimentalTvMaterial3Api::class)
@Preview(device = "id:tv_1080p", showBackground = true)
@Composable
private fun PreviewCatalogScreen(
    @PreviewParameter(CatalogUiStatePreviewProvider::class)
    uiState: CatalogUiState,
) {
    TvComposeTheme(isInDarkTheme = true) {
        Surface(shape = RectangleShape) {
            CatalogScreen(
                modifier = Modifier.fillMaxSize(),
                uiState = uiState,
                onMovie = { },
            )
        }
    }
}
