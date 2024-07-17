package me.brisson.tvcompose.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import me.brisson.tvcompose.domain.model.Movie
import javax.inject.Inject
import kotlin.random.Random

class MovieApi @Inject constructor()  {
    suspend fun loadCategories(): List<String> = withDelay {
        MovieList.categories
    }

    suspend fun loadFeaturedMovieList(): List<Movie> = withDelay {
        MovieList.featured
    }

    suspend fun getMovieListByCategory(category: String): List<Movie> = withDelay {
        MovieList.getByCategory(category)
    }

    suspend fun findMovieById(id: Long): Movie? = withDelay {
        MovieList.findById(id)
    }

    private suspend fun <T> withDelay(
        delayInMilSec: Long = Random.nextLong(300, 1500),
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        task: suspend () -> T,
    ): T {
        return withContext(context = coroutineDispatcher) {
            delay(delayInMilSec)
            task()
        }
    }
}