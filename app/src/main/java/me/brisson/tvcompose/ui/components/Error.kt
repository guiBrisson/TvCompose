package me.brisson.tvcompose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ErrorComponent(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onBack: (() -> Unit)? = null
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Error", style = MaterialTheme.typography.displayMedium)
            Text(text = errorMessage)

            onBack?.let {
                Button(modifier = Modifier.padding(top = 20.dp), onClick = it) {
                    Text(text = "Back to the previous screen")
                }
            }
        }
    }
}
