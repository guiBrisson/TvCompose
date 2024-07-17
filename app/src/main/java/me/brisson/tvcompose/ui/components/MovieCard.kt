package me.brisson.tvcompose.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import coil.compose.AsyncImage
import me.brisson.tvcompose.R
import me.brisson.tvcompose.domain.model.Movie

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (Movie) -> Unit,
) {
    Card(
        onClick = { onClick(movie) },
        modifier = modifier
            .widthIn(max = 320.dp)
            .aspectRatio(16f / 9f),
    ) {
        AsyncImage(
            model = movie.cardImageUrl,
            contentDescription = movie.description,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder)
        )
    }
}
